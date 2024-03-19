package pet.perpet.equal.presentation.start.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.launch
import pet.perpet.data.api.entity.account.TokenEntity
import pet.perpet.data.nonnull
import pet.perpet.domain.TokenStore
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.account.Term
import pet.perpet.domain.usecase.account.AccountCheckUseCase
import pet.perpet.domain.usecase.account.AccountLoginUseCase
import pet.perpet.domain.usecase.account.AccountUserInfoUseCase
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.goPagerWeight
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import pet.perpet.equal.presentation.start.fragment.IntoPagerFragment
import pet.perpet.equal.support.alarm.AlarmReceiver
import pet.perpet.equal.support.deeplink.DeepLink
import pet.perpet.equal.support.deeplink.goDeepLinkProcess
import pet.perpet.equal.support.deeplink.model.BaseArgument
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.navigation.showSignPolicy
import pet.perpet.framework.activity.AppCompatActivity
import java.util.Calendar

class StartViewModel : ViewModel() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var dependency: (() -> AppCompatActivity)

    private val activity: AppCompatActivity
        get() = dependency.invoke()

    private lateinit var getResult: ActivityResultLauncher<Intent>

    private var kakaoProgress: Boolean = false

    private var term: String = "N"
    private var info: String = "N"
    private var marketing: String = "N"

    val recent: Int
        get() = when (UserStore.recent) {
            "KAKAO" -> 1
            "NAVER" -> 2
            "GOOGLE" -> 3
            else -> -1
        }

    private var deepLink: BaseArgument? = null

    private var googleSignInClient: GoogleSignInClient? = null

    private val fragmentItems = mutableListOf(
        FragmentItem(
            FragmentItem.id_intro_1,
            IntoPagerFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_2,
            IntoPagerFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_3,
            IntoPagerFragment::class.java
        ),
    )

    private val sessionCallback: ((OAuthToken?, Throwable?) -> Unit) = { token, error ->
        if (error != null) {
            var keyHash = Utility.getKeyHash(activity)
            AppLogger.i(TAG, "로그인 실패${error.message} +  키해시${keyHash}")
        } else if (token != null) {
            if (kakaoProgress) {
                AppLogger.i(TAG, "로그인 절차 진행중")
            } else {
                AppLogger.i(TAG, "로그인 성공")
                getKakaoProfile()
            }
        }
    }

    val fragmentAdapter by lazy {
        object : FragmentStatePagerAdapter(
            activity.supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int {
                return fragmentItems.size
            }

            override fun getItem(position: Int): Fragment {
                return fragmentItems[position].createFragment()
            }
        }
    }

    fun onNewIntent(intent: Intent) {
        deepLink = DeepLink.getParameter(intent)
        AppLogger.d(TAG, "onNewIntent > intent : $deepLink")
    }

    fun onCreate(activity: AppCompatActivity) {

        deepLink = DeepLink.getParameter(activity.intent)
        AppLogger.d(TAG, "onCreate > intent : $deepLink")

        //naver
        NaverIdLoginSDK.initialize(
            activity,
            activity.getString(R.string.social_login_info_naver_client_id),
            activity.getString(R.string.social_login_info_naver_client_secret),
            activity.getString(R.string.app_name)
        )
        getGoogleClient()

        getResult =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    try {
                        val account = task.getResult(ApiException::class.java)

                        viewModelScope.launch {
                            AccountCheckUseCase().parameter2 {
                                this.id = account.id.toString()
                                this.type = "GOOGLE"
                            }.success {
                                if ((it as Boolean).not()) {
                                    activity.showSignPolicy {
                                        UserStore.setRecentLogin("GOOGLE")
                                        UserStore.setRecentSnsLoginIdLogin(account.idToken.toString())
                                        term = it.term.nonnull()
                                        marketing = it.marketing.nonnull()
                                        info = it.info.nonnull()

                                        goSnsLogin(
                                            account.id.toString(),
                                            "GOOGLE",
                                            account.displayName.nonnull(),
                                            account.email.nonnull(),
                                            TokenStore.deviceToken
                                        )
                                    }
                                } else {
                                    UserStore.setRecentLogin("GOOGLE")
                                    UserStore.setRecentSnsLoginIdLogin(account.id.toString())
                                    goSnsLogin(
                                        account.id.toString(),
                                        "GOOGLE",
                                        account.displayName.nonnull(),
                                        account.email.nonnull(),
                                        TokenStore.deviceToken
                                    )
                                }
                            }.fail {
                            }.execute()
                        }
                    } catch (e: Exception) {
                    }
                }
            }
    }

    fun getGoogleClient() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }


    fun onKakaoClick(view: View) {

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
            UserApiClient.instance.loginWithKakaoTalk(activity, callback = sessionCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(activity, callback = sessionCallback)
        }

//        goSnsLogin(3014020399.toString(), "KAKAO", "", " rmacnt@naver.com", TokenStore.deviceToken)
    }

    fun onGoogleClick(view: View) {
        googleSignInClient?.signOut()
        try {
            getResult.launch(googleSignInClient?.signInIntent)
        } catch (e: Exception) {
        }
    }

    fun onNaverClick(view: View) {
        startNaverLogin()
    }

    private fun getKakaoProfile() {

        fun logout() {
            UserApiClient.instance.logout { error ->
                if (error != null) AppLogger.i(TAG, "로그아웃 실패. SDK에서 토큰 삭제$error")
                else AppLogger.i(TAG, "로그아웃 성공")
                kakaoProgress = false
            }
        }
        kakaoProgress = true
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                resetKakaoProgress()
            } else if (user != null) {

                val id = user.id
                val account = user.kakaoAccount

                val email = account?.email.orEmpty()
                val nickname = account?.profile?.nickname
                val profile = account?.profile?.profileImageUrl

                AppLogger.w(
                    TAG,
                    "getKakaoProfile > id : $id, email : $email, nickname : $nickname, profile : $profile"
                )
                AppLogger.w(
                    TAG,
                    "TokenStore:> : ${TokenStore.deviceToken}"
                )

                logout()
                viewModelScope.launch {
                    AccountCheckUseCase().parameter2 {
                        this.id = id.toString()
                        this.type = "KAKAO"
                    }.success {
                        if ((it as Boolean).not()) {
                            activity.showSignPolicy {
                                UserStore.setRecentLogin("KAKAO")
                                UserStore.setRecentSnsLoginIdLogin(id.toString())
                                term = it.term.nonnull()
                                marketing = it.marketing.nonnull()
                                info = it.info.nonnull()

                                goSnsLogin(
                                    id.toString(),
                                    "KAKAO",
                                    nickname,
                                    email,
                                    TokenStore.deviceToken
                                )
                            }
                        } else {
                            UserStore.setRecentLogin("KAKAO")
                            UserStore.setRecentSnsLoginIdLogin(id.toString())
                            goSnsLogin(
                                id.toString(),
                                "KAKAO",
                                nickname,
                                email,
                                TokenStore.deviceToken
                            )
                        }
                    }.fail {
                    }.execute()
                }


            }
        }
    }


    private fun resetKakaoProgress() {
        kakaoProgress = false
    }


    private fun startNaverLogin() {
        var naverToken: String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {

            fun logout() {
                NaverIdLoginSDK.logout()
            }

            override fun onSuccess(response: NidProfileResponse) {
                logout()
                val userId = response.profile?.id
                val userName = response.profile?.name
                val userEmail = response.profile?.email

                viewModelScope.launch {
                    AccountCheckUseCase().parameter2 {
                        this.id = userId.nonnull()
                        this.type = "NAVER"
                    }.success {
                        it?.let {
                            if ((it as Boolean).not()) {
                                activity.showSignPolicy {
                                    UserStore.setRecentLogin("NAVER")
                                    UserStore.setRecentSnsLoginIdLogin(userId.nonnull())
                                    term = it.term.nonnull()
                                    marketing = it.marketing.nonnull()
                                    info = it.info.nonnull()

                                    goSnsLogin(
                                        userId,
                                        "NAVER",
                                        userName,
                                        userEmail,
                                        TokenStore.deviceToken
                                    )
                                }
                            } else {
                                UserStore.setRecentLogin("NAVER")
                                UserStore.setRecentSnsLoginIdLogin(userId.nonnull())
                                goSnsLogin(
                                    userId,
                                    "NAVER",
                                    userName,
                                    userEmail,
                                    TokenStore.deviceToken
                                )
                            }
                        }

                    }.fail {
                    }.execute()
                }
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                naverToken = NaverIdLoginSDK.getAccessToken()
                NidOAuthLogin().callProfileApi(profileCallback)
            }

            override fun onFailure(httpStatus: Int, message: String) {
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        try {
            NaverIdLoginSDK.authenticate(activity, oauthLoginCallback)
        } catch (e: Exception) {
        }

    }

    @SuppressLint("HardwareIds")
    private fun goSnsLogin(
        id: String?,
        type: String,
        name: String?,
        email: String?,
        deviceNum: String?,
    ) {
        val androidId: String =
            Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
        AccountLoginUseCase().parameter2 {
            this.id = id.nonnull()
            this.type = type.nonnull()
            this.name = name.nonnull()
            this.email = email.nonnull()
            this.device_unique = androidId
            this.device_group = "A"
            this.device_num = deviceNum.nonnull()
            this.service_agree = term
            this.privacy_agree = info
            this.marketing_agree = marketing
        }.success {
            it?.let {
                UserStore.sync(it)
                TokenStore.saveToken(
                    TokenEntity(
                        it.id,
                        it.accessToken,
                        it.refreshToken,
                        it.tokenType,
                        null
                    )
                )
                TokenStore.saveTerm(Term(term, info, marketing))
                getUserInfo()
                if (it.signUp == false) {
                    getMyPetList()
                } else {
                    activity.goPagerWeight(true)
                    activity.finish()
                }
            }
        }.fail {
            Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
        }.execute()
    }


    private fun getMyPetList() {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                if ((it.content?.size ?: -1) > 0) {
                    completeLogin()
                } else {
                    activity.goPagerWeight()
                    activity.finish()
                }
            }
        }.fail {
            activity.goPagerWeight()
            activity.finish()
        }.execute()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            val id = UserStore.user?.id
            id?.let {
                AccountUserInfoUseCase().parameter2 {
                    this.id = it
                }.success {
                    it?.let {
                        UserStore.userInfoSync(it)
                    }
                }.fail {
                }.execute()
            }

        }
    }

    private fun completeLogin() {
        activity.goHome()
        setAlarm()
        processGoDeepLink()
        activity.finish()
    }


    private fun processGoDeepLink() {
        AppLogger.d(TAG, "processGoDeepLink > intent : $deepLink")
        deepLink?.run {
            activity.goDeepLinkProcess(this)
        }
    }

    private fun setAlarm () {
        val manager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val hasPermission: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            manager.canScheduleExactAlarms()
        } else {
            true
        }

        if(hasPermission) {

            IntakeStore.intakeAlarm?.let {

                var intakeTime  = 0
                it.forEach {
                    if(it.userId == UserStore.userInfo?.id) {
                        intakeTime =  if (it.type == 1) it.time.nonnull() + 12 else it.time.nonnull()
                        return@forEach
                    }
                }

                if(intakeTime != 0) {
                    val intent = Intent(activity, AlarmReceiver::class.java)
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                    val pendingIntent = PendingIntent.getBroadcast(activity, 7727, intent, IntakeStore.ALARM_FLAG)

                    val calendar = Calendar.getInstance().apply {

                        timeInMillis = System.currentTimeMillis()

                        set(Calendar.HOUR_OF_DAY ,intakeTime)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)

                        if (before(Calendar.getInstance())) {
                            add(Calendar.DATE, 1)
                        }
                    }

                    manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                }
            }

        }
    }

    companion object {
        const val TAG = "AUTH"
    }
}