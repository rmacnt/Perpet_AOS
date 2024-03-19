package pet.perpet.data.repository.account

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.api.request.account.AccountMarketingRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AccountMarketingSettingRepository : Repository<AccountMarketingSettingRepository.Parameter, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {

        return object :
            RemoteDataSource<Parameter, Any, DefaultResponse>() {
            override fun fetchApi(param: Parameter?): ApiCall<DefaultResponse> {
                return Client.account.setMarketingSetting(
                    AccountMarketingRequest(
                        marketing_agree = param?.marketingAgree.nonnull()
                    )
                )
            }

            override fun toObject(raw: DefaultResponse?): Boolean? {
                return raw?.success
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var marketingAgree: String = ""
    }
}