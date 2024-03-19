package pet.perpet.domain

import IntakeData
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import pet.perpet.data.api.entity.account.TokenEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.domain.model.account.Term
import pet.perpet.domain.model.account.User
import pet.perpet.domain.model.account.UserLogin
import pet.perpet.domain.model.pet.Pet
import pet.perpet.domain.usecase.account.AccountInfoUseCase
import pet.perpet.domain.usecase.account.AccountLoginUseCase

object UserStore {

    //======================================================================
    // Variables
    //======================================================================

    val user: UserLogin?
        get() {
            return userDataSource.get()
        }

    val userInfo: User?
        get() {
            return userInfoDataSource.get()
        }

    val mainPet: Pet?
        get() {
            return petDataSource.get()
        }

    val temporaryPet: Pet?
        get() {
            return petTemporaryDataSource.get()
        }

    val recent: String?
        get() {
            return userRecentDataSource.get()
        }

    val recentSnsId: String?
        get() {
            return userRecentSnsIDDataSource.get()
        }


    val isUseUser: Boolean
        get() = user != null

    private val userDataSource: CashDataSource<UserLogin> by lazy {
        object : CashDataSource<UserLogin>() {
            override val key: String
                get() = "userDataSource"
        }
    }
    private val userInfoDataSource: CashDataSource<User> by lazy {
        object : CashDataSource<User>() {
            override val key: String
                get() = "userInfoDataSource"
        }
    }

    private val petDataSource: CashDataSource<Pet> by lazy {
        object : CashDataSource<Pet>() {
            override val key: String
                get() = "petDataSource"
        }
    }

    private val userRecentDataSource: CashDataSource<String> by lazy {
        object : CashDataSource<String>() {
            override val key: String
                get() = "userRecentDataSource"
        }
    }

    private val userRecentSnsIDDataSource: CashDataSource<String> by lazy {
        object : CashDataSource<String>() {
            override val key: String
                get() = "userRecentSnsIDDataSource"
        }
    }


    private val petTemporaryDataSource: CashDataSource<Pet> by lazy {
        object : CashDataSource<Pet>() {
            override val key: String
                get() = "petTemporaryDataSource"
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun sync(userLogin: UserLogin): UserLogin? {
        userDataSource.save(userLogin)
        return userDataSource.get()
    }

    fun mainPetSync(pet: Pet) {
        petDataSource.clear()
        petDataSource.save(pet)
    }
    fun userInfoSync(user: User) {
        userInfoDataSource.clear()
        userInfoDataSource.save(user)
    }



    fun remoteSync(): Pair<Int, UserLogin?>? {

        val useCase = AccountLoginUseCase().parameter2 {
            this.id = recentSnsId.nonnull()
            this.type = recent.nonnull()
            this.device_group = "A"
            this.service_agree = TokenStore.term?.service_agree.nonnull()
            this.privacy_agree = TokenStore.term?.privacy_agree.nonnull()
            this.device_num = TokenStore.deviceToken.nonnull()

//            Log.d("CHECK", "id:${id} ,type:${type}, devive_group:${device_group} ,service_agree: ${service_agree} m privacy_agree: ${privacy_agree}")
        }.remote()


        var statusCode = -1
        useCase.responseStatus { code, success ->
            statusCode = code
        }.fail {
        }
        val user = useCase.sync()
//        Log.d("CHECK", "uesr:${user}")
        userDataSource.clear()
        userDataSource.save(user)
        return Pair(statusCode, user)
    }

    fun getAccountInfo(
        callback: ((result: User?) -> Unit),
        error: ((exception: Throwable) -> Unit)? = null
    ) {
        AccountInfoUseCase()
            .success {
                saveUserInternal(it)
                callback(it)
            }
            .fail {
                clearUserInternal()
                error?.invoke(it)
            }
            .execute()
    }

    fun getAccountInfo(
        callback: ((result: User?) -> Unit)
    ) {
        AccountInfoUseCase()
            .success {
                saveUserInternal(it)
                callback(it)
            }
            .fail {
                clearUserInternal()
                callback(null)
            }
            .execute()
    }
    fun setRecentLogin(login: String) {
        userRecentDataSource.save(login)
    }

    fun setRecentSnsLoginIdLogin(id: String) {
        userRecentSnsIDDataSource.clear()
        userRecentSnsIDDataSource.save(id)
    }

    fun setTemporaryPet(pet: Pet) {
        petTemporaryDataSource.save(pet)
    }

    fun setTemporaryClear() {
        petTemporaryDataSource.clear()
    }


    fun clear() {
        AccountInfoUseCase().clearCash()
        clearUserInternal()
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun saveUserInternal(user: User?) {
        user?.run {
//            SentryCompat.setUser(this)
        }
        userDataSource.save(user)
    }

    private fun clearUserInternal() {
        userDataSource.clear()
        userRecentSnsIDDataSource.clear()
    }
}