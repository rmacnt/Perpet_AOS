package pet.perpet.data.api

import pet.perpet.data.DataConfig
import pet.perpet.data.RetrofitFactory
import pet.perpet.data.api.service.AccountService
import pet.perpet.data.api.service.IntakeService
import pet.perpet.data.api.service.MainService
import pet.perpet.data.api.service.MedicalService
import pet.perpet.data.api.service.NotificationService
import retrofit2.Retrofit

object Client {

    val baseUrl: String = "https://api.equal.pet/"

    private val api: Retrofit by lazy {
        RetrofitFactory.create(baseUrl)
    }

    val account: AccountService
        get() {
            return api
                .newBuilder()
                .baseUrl(DataConfig.getProvider().domain)
                .build()
                .create(AccountService::class.java)
        }

    val medical: MedicalService
        get() {
            return api
                .newBuilder()
                .baseUrl(DataConfig.getProvider().domain)
                .build()
                .create(MedicalService::class.java)
        }

    val notification: NotificationService
        get() {
            return api
                .newBuilder()
                .baseUrl(DataConfig.getProvider().domain)
                .build()
                .create(NotificationService::class.java)
        }

    val main: MainService
        get() {
            return api
                .newBuilder()
                .baseUrl(DataConfig.getProvider().domain)
                .build()
                .create(MainService::class.java)
        }

    val intake: IntakeService
        get() {
            return api
                .newBuilder()
                .baseUrl(DataConfig.getProvider().domain)
                .build()
                .create(IntakeService::class.java)
        }

}