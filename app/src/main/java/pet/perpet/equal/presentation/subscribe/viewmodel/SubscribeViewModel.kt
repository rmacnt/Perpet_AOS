package pet.perpet.equal.presentation.subscribe.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.CountDownTimer
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.api.request.account.AuthenticationPhoneRequest
import pet.perpet.data.api.request.main.AddProductsRequest
import pet.perpet.data.api.request.main.SubscribeRequest
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.card.CardList
import pet.perpet.domain.usecase.account.AccountUserInfoUseCase
import pet.perpet.domain.usecase.account.AuthenticationSendSmsUseCase
import pet.perpet.domain.usecase.account.AuthenticationUseCase
import pet.perpet.domain.usecase.address.AddressMoreListUseCase
import pet.perpet.domain.usecase.card.CardListUseCase
import pet.perpet.domain.usecase.main.MainUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.finishAll
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import pet.perpet.equal.presentation.intakecheck.model.IntakeAlarm
import pet.perpet.equal.presentation.sign.differ.PackageItemListDiffer
import pet.perpet.equal.presentation.subscribe.fragment.BottomAddressSettingFragment
import pet.perpet.equal.presentation.subscribe.fragment.SubscribeFragmentArgs
import pet.perpet.equal.presentation.supportFragmentManager
import pet.perpet.equal.support.alarm.AlarmReceiver
import pet.perpet.equal.support.navigation.showAddressSetting
import pet.perpet.equal.support.navigation.showPaymentSetting
import pet.perpet.equal.support.navigation.showPolicyInfo
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Collections
import java.util.regex.Pattern

class SubscribeViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val useCase = MainUseCase()

    private val args by lazy {
        SubscribeFragmentArgs.fromBundle(arguments)
    }

    val title: String?
        get() = getString(R.string.subscribe_comment2)
            ?.let { String.format(it, args.name) }

    val namePackage: String?
        get() = getString(R.string.subscribe_comment37)
            ?.let { String.format(it, args.name) }

    val priceMonthText: String?
        get() = priceMonth.value

    val name: String?
        get() = args.name

    var card: CardList? = null

    var address: ListAddress? = null

    var allClick: Boolean = false

    var subscribeTotalPrice: Int = 0

    val suppleTitle: String?
        get() = String.format(getString(R.string.subscribe_comment13), args.medical?.packages?.size)

    val phoneTimeValue: String?
        get() = phoneTimer.value


    var term1Use: Boolean = false
    var term2Use: Boolean = false
    var term3Use: Boolean = false

    val priceMonth: MutableLiveData<String> = MutableLiveData("")
    val totalPrice: MutableLiveData<String> = MutableLiveData("")
    val productBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    val termAsk: MutableLiveData<Boolean> = MutableLiveData(true)

    val productAddress: MutableLiveData<String> = MutableLiveData("")
    val productAddressName: MutableLiveData<String> = MutableLiveData("")
    val allTotalPrice: MutableLiveData<String> = MutableLiveData("")
    val phoneSendCall: MutableLiveData<Int> = MutableLiveData(-1)
    val productCard: MutableLiveData<String> =
        MutableLiveData(getString(R.string.subscribe_comment19))

    val productCardBoolean: Boolean?
        get() = productCard.value?.contains("/")

    val allTotalPriceResult: MutableLiveData<String> = MutableLiveData("")
    val allTotalTodayPriceResult: MutableLiveData<Spanned> = MutableLiveData()

    val totalPriceText: String?
        get() = totalPrice.value

    val phone: MutableLiveData<String> = MutableLiveData()
    val phoneOk: MutableLiveData<String> = MutableLiveData()
    val phoneTimer: MutableLiveData<String> =
        MutableLiveData(getString(R.string.subscribe_payment_comment16))

    var countDownTimer: CountDownTimer? = null

    val phoneSend: Int?
        get() = phoneSendCall.value

    val phoneError: MutableLiveData<Boolean> = MutableLiveData(false)
    val phoneErrorMessage: MutableLiveData<String> = MutableLiveData()
    val phoneNumError: MutableLiveData<Boolean> = MutableLiveData(false)
    val phoneNumErrorMessage: MutableLiveData<String> = MutableLiveData()

    val viewComment: Spanned?
        get() = getString(R.string.bottom_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val onPhoneTextColor: Int?
        get() = getColor(
            when (phoneSend) {
                -1 -> R.color.white
                1 -> R.color.text_hint_color
                else -> R.color.black
            }
        )

    val onPhoneDrawable: Drawable?
        get() = getDrawable(
            when (phoneSend) {
                -1 -> {
                    if(isPhoneNumberCheck(phone.value.nonnull())) {
                        R.drawable.black_radius_8
                    } else {
                        R.drawable.ddd_radius_8
                    }
                }
                1 -> R.drawable.border_1_black_radius_8
                else -> R.drawable.border_1_ddd_eee_radius_8
            }
        )

    val onPhoneText: String?
        get() = getString(
            when (phoneSend) {
                -1 -> R.string.subscribe_payment_comment5
                else -> R.string.subscribe_payment_comment6
            }
        )

    val onPhoneColor: Int?
        get() = getColor(
            when (phoneSend) {
                0 -> R.color.phone_success
                else -> R.color.line_color_error
            }
        )

    val onPhoneError: String?
        get() = phoneErrorMessage.value

    val onPhoneNumError: String?
        get() = phoneNumErrorMessage.value

    val onTermTextColor: Int?
        get() = getColor(
            if (allClick.not()) {
                R.color.text_hint_color
            } else {
                R.color.black
            }
        )

    val onTerm1TextColor: Int?
        get() = getColor(
            if (term1Use.not()) {
                R.color.text_hint_color
            } else {
                R.color.black
            }
        )
    val onTerm2TextColor: Int?
        get() = getColor(
            if (term2Use.not()) {
                R.color.text_hint_color
            } else {
                R.color.black
            }
        )

    val onTerm3TextColor: Int?
        get() = getColor(
            if (term3Use.not()) {
                R.color.text_hint_color
            } else {
                R.color.black
            }
        )

    val itemListDiffer by lazy {
        PackageItemListDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    val subscribeBg: Drawable?
        get() = if(allClick && address != null && card != null && phoneSendCall.value == 0) {
            getDrawable(R.drawable.black_radius_8)
        }else {
            getDrawable(R.drawable.ddd_radius_8)
        }

    private var startNetwork = false

    init {
        args.products?.let {
            var totalCount = 0
            productBoolean.value = true

            var packagePrice = 0
            args.medical?.packages?.forEach {
                it.rxInfo?.daily_price?.let { price ->
                    packagePrice += price
                }


                allTotalPrice.value =
                    String.format(
                        getString(R.string.price),
                        DecimalFormat("#,###").format((packagePrice * 30))
                    )
            }

            allTotalPriceResult.value = String.format(
                getString(R.string.price),
                DecimalFormat("#,###").format(((packagePrice * 30) + totalCount))
            )
            subscribeTotalPrice = (packagePrice * 30) + totalCount
            allTotalTodayPriceResult.value = String.format(
                getString(R.string.subscribe_payment_comment15),
                DecimalFormat("#,###").format((totalCount / 30) + packagePrice)
            ).run { HtmlFactory.fromHtml(this) }
            executeViewBinding()
        } ?: run {


            args.medical?.packages?.let { data ->
                val items = mutableListOf<Any>()
                items.addAll(data)
                Collections.sort(items, Collections.reverseOrder())
                itemListDiffer.allList(items)
                itemListDiffer.adapter.notifyDataSetChanged()
            }

            var packagePrice = 0
            args.medical?.packages?.forEach {
                it.rxInfo?.daily_price?.let { price ->
                    packagePrice += price
                }

                allTotalPrice.value =
                    String.format(
                        getString(R.string.price),
                        DecimalFormat("#,###").format((packagePrice * 30))
                    )
            }

            allTotalPrice.value =
                String.format(
                    getString(R.string.price),
                    DecimalFormat("#,###").format((packagePrice * 30))
                )

            allTotalPriceResult.value = String.format(
                getString(R.string.price),
                DecimalFormat("#,###").format((packagePrice * 30))
            )
            subscribeTotalPrice = packagePrice * 30
            allTotalTodayPriceResult.value = String.format(
                getString(R.string.subscribe_payment_comment15),
                DecimalFormat("#,###").format(packagePrice)
            ).run { HtmlFactory.fromHtml(this) }
            productBoolean.value = false

            getMonthPrice()
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun getMonthPrice() {
        var totalPrice = 0
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.daily_price ?: 0
        }
        priceMonth.value = DecimalFormat("#,###").format((totalPrice * 30)
        )
    }

    fun onPhoneTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {

        phone.value = text.toString().replace("/[^0-9]/g", "")
            .replace("/^(\\d{0,3})(\\d{0,4})(\\d{0,4})$/g", "$1-$2-$3")
            .replace("/(\\-{1,2})$/g", "")

        if(phoneSendCall.value != 0) {
            phoneNumErrorMessage.value = ""
            phoneNumError.value = false
            phoneError.value = false
            phoneErrorMessage.value = ""
        }
        executeViewBinding()
    }

    fun onPhoneSmsChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {

        phoneOk.value = text.toString()
        executeViewBinding()
    }

    fun onSubmitClick(view: View) {

        if (phoneSendCall.value != 0) {
            Toast.makeText(activity, "휴대폰 인증을 진행해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (term2Use.not()) {
            Toast.makeText(activity, getString(R.string.subscribe_comment33)+ "해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (term1Use.not()) {
            Toast.makeText(activity, getString(R.string.subscribe_comment34)+ "해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (term3Use.not()) {
            Toast.makeText(activity, getString(R.string.subscribe_comment35)+ "을 동의해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }


        if (address == null) {
            Toast.makeText(activity, "배송 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (card == null) {
            Toast.makeText(activity, "결제 수단을 등록해 주세요.", Toast.LENGTH_SHORT).show()
        }

        if (allClick.not()) {
            Toast.makeText(activity, "주문 필수 약관에 동의해 주세요.", Toast.LENGTH_SHORT).show()
        }

        if(startNetwork.not()) {
            startNetwork = true
            viewModelScope.launch {
                var item: ArrayList<AddProductsRequest> = arrayListOf()
                args.products?.let { data ->
                    data.forEach {
                        val totalPrice = it.count_per_unit?.let { price ->
                            it.price_per_count.nonnull().toInt() * price
                        } ?: run {
                            0
                        }

                        item.add(
                            AddProductsRequest(
                                product_id = it.id,
                                quantity = it.count_per_unit,
                                price = it.price_per_count.nonnull(),
                                totalPrice = totalPrice.toDouble()
                            )
                        )
                    }


                }

                useCase.setSubscribe(
                    SubscribeRequest(
                        pet_id = args.petId.toString(),
                        medical_id = args.medical?.id.toString(),
                        card_id = card?.id.toString(),
                        address_id = address?.id.toString(),
                        pay_method = "card",
                        amount = subscribeTotalPrice.toString(),
                        addProducts = null

                    )
                ).success {
                    it?.let {
                        if(it is DefaultResponse) {
                            if(it.success ) {

                                IntakeStore.sync(IntakeAlarm(0,  11 , UserStore.userInfo?.id))
                                setAlarm()

                                Toast.makeText(activity, "구독 결제가 완료되었습니다. 정성껏 준비하여 전달해 드릴게요.", Toast.LENGTH_SHORT).show()
                                activity?.finishAll()
                                activity?.goHome()

                            } else {
                                startNetwork = false
                                Toast.makeText(activity, it.msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } ?: run {
                        startNetwork = false
                        Toast.makeText(activity, "구독신청이 실패되었습니다.", Toast.LENGTH_SHORT).show()
                    }

                }.fail {
                    startNetwork = false
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }.execute()
            }
        }

    }

    fun onPaymentInsertClick(view: View) {
        context?.let {
            it.supportFragmentManager()?.let {
                activity?.showPaymentSetting {

                    if (card == null) {
                        Toast.makeText(activity , getString(R.string.subscribe_comment67) , Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity , getString(R.string.subscribe_comment69) , Toast.LENGTH_SHORT).show()
                    }

                    productCard.value = "${it.card_name} / ${it.card_number}"
                    card = it
                    executeViewBinding()
                }
            }
        }
    }

    fun onAddressInsertClick(view: View) {
        context?.let {
            activity?.showAddressSetting {
                if (address == null) {
                    Toast.makeText(activity , getString(R.string.subscribe_comment68) , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity , getString(R.string.subscribe_comment70) , Toast.LENGTH_SHORT).show()
                }

                address = it
                productAddress.value = it.address
                productAddressName.value = "${it.recipient} / ${it.phone}"

                fragment?.parentFragmentManager?.let {
                    it.beginTransaction()
                        .attach(BottomAddressSettingFragment())
                        .commitAllowingStateLoss()
                }

                executeViewBinding()
            }
        }

    }

    fun getCardList() {
        CardListUseCase()
            .parameter2 {}
            .success {
                it?.content?.let {
                    it.forEach {cardInfo->
                        if(cardInfo.ordersInfo != null) {
                            productCard.value = "${cardInfo.card_name} / ${cardInfo.card_number}"
                            card = cardInfo
                            executeViewBinding()
                            return@forEach
                        }
                    }

                }
            }.fail {
            }.execute()
    }

    fun getAddressList() {
        AddressMoreListUseCase()
            .parameter2 {}
            .success {
                it?.content?.let {
                    it.forEach {listAddress->
                        if(listAddress.ordersInfo != null) {
                            address = listAddress
                            productAddress.value = listAddress.address
                            productAddressName.value = "${listAddress.recipient} / ${listAddress.phone}"

                            executeViewBinding()
                            return@forEach
                        }
                    }

                }
            }.fail {
            }.execute()
    }

    fun onPhoneSubmitClick(view: View) {
        phone.value?.let {
            if (isPhoneNumberCheck(it)) {
                phoneError.postValue(false)
                phoneSendCall.value = 1
                viewModelScope.launch {
                    AuthenticationSendSmsUseCase().parameter2 {
                        authenticationPhoneRequest = AuthenticationPhoneRequest(it)
                    }.success {
                        viewModelScope.launch {
                            countDownTimer = object : CountDownTimer(1000 * 60 * 5, 1000) {
                                override fun onTick(p0: Long) {
                                    phoneTimer.postValue(
                                        String.format(
                                            getString(R.string.subscribe_payment_comment7),
                                            (p0 / 1000).toString()
                                        )
                                    )
                                    executeViewBinding()
                                }

                                override fun onFinish() {
                                    if (phoneSend != 0) {
                                        phoneError.value = true
                                        phoneErrorMessage.value =
                                            getString(R.string.subscribe_payment_comment18)
                                        executeViewBinding()
                                    }
                                }
                            }.start()
                        }
                    }.fail {
                    }.execute()
                }
            } else {
                phoneNumError.value = true
                phoneNumErrorMessage.value = getString(R.string.subscribe_payment_comment19)
            }
            executeViewBinding()
        }


    }

    fun onPhoneNumberClick(view: View) {
        val phoneNumber = phone.value
        phoneOk.value?.let {
            if (it.length >= 6) {
                phoneError.postValue(false)
                viewModelScope.launch {
                    AuthenticationUseCase().parameter2 {
                        this.phone = phoneNumber
                        this.auth_num = it
                    }.success {
                        if ((it as Boolean).not()) {
                            phoneErrorMessage.postValue(getString(R.string.subscribe_payment_comment8))
                            phoneError.postValue(true)
                        } else {
                            phoneSendCall.value = 0
                            phoneError.value = true
                            phoneErrorMessage.value =
                                getString(R.string.subscribe_payment_comment10)
                        }
                        countDownTimer?.cancel()
                        executeViewBinding()
                    }.fail {
                    }.execute()
                }
            } else {
                phoneErrorMessage.postValue(getString(R.string.subscribe_payment_comment8))
                phoneError.postValue(true)
            }
            executeViewBinding()
        }


    }

    fun onHomeClick(view: View?) {
        activity?.let { activity ->
            AppAlertDialog(
                activity,
                getString(R.string.dialog_title),
                getString(R.string.subscribe_comment71),
                getString(R.string.yes_1),
                getString(R.string.no)

            ).show(
                onClickNegative = {
                    it.dismiss()
                },
                onClickPositive = {
                    activity.finish()
                    it.dismiss()
                }
            )
        }



    }


    fun isPhoneNumberCheck(cellphoneNumber: String): Boolean {
        var returnValue = false;
        val regex =
            "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";
        val p = Pattern.compile(regex);

        val m = p.matcher(cellphoneNumber);

        if (m.matches()) {
            returnValue = true;
        }

        return returnValue;
    }

    fun onTermClick(view: View) {
        activity?.showPolicyInfo(
            "개인정보 수집 및 이용에 대한 동의",
            getString(R.string.subscribe_url_1)
        )
    }

    fun onTerm2Click(view: View) {
        activity?.showPolicyInfo(
            "개인정보 제3자 제공에 대한 동의",
            getString(R.string.subscribe_url_2)
        )
    }

    fun getTotalPrice() {
        var price = 0
        args.medical?.packages?.forEach {
            it.rxInfo?.daily_price?.let { item ->
                price += item
            }
        }

        totalPrice.value =
            String.format(getString(R.string.price), DecimalFormat("#,###").format((price * 30)))
    }

    fun onAllClick(view: View) {
        if (allClick.not()) {
            term1Use = true
            term2Use = true
            term3Use = true
            allClick = true

        } else {
            term1Use = false
            term2Use = false
            term3Use = false
            allClick = false
        }

        executeViewBinding()
    }

    fun onTerm1ItemClick(view: View) {
        term1Use = term1Use.not()
        allClick = term1Use && term2Use && term3Use
        executeViewBinding()
    }

    fun onTerm2ItemClick(view: View) {
        term2Use = term2Use.not()
        allClick = term1Use && term2Use && term3Use
        executeViewBinding()
    }

    fun onTerm3ItemClick(view: View) {
        term3Use = term3Use.not()
        allClick = term1Use && term2Use && term3Use
        executeViewBinding()
    }

    fun onTermDetailClick(view: View) {
        termAsk.value = termAsk.value?.not()
        executeViewBinding()
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
                        phoneSendCall.value = if (it.authentication_yn == "Y") 0 else -1

                        if(it.authentication_yn == "Y") {
                            phone.value = it.phone.nonnull()

                            phoneError.value = true
                            phoneErrorMessage.value =
                                getString(R.string.subscribe_payment_comment10)

                        }
//                        .value = it.address
//                        productAddressName.value = "${it.recipient} / ${it.phone}"
                        executeViewBinding()
                    }
                }.fail {
                }.execute()
            }

        }
    }


    private fun setAlarm () {
        val manager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val hasPermission: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            manager.canScheduleExactAlarms()
        } else {
            true
        }

        if(hasPermission) {
            val intentCancel = Intent(context, AlarmReceiver::class.java)
            val pendingIntentCancel = PendingIntent.getBroadcast(context, 7727, intentCancel, IntakeStore.ALARM_FLAG)
            if(pendingIntentCancel != null) {
                manager.cancel(pendingIntentCancel)
            }

            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 7727, intent, IntakeStore.ALARM_FLAG)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()

                set(Calendar.HOUR_OF_DAY ,11)
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