package pet.perpet.domain.usecase.main

import android.util.Log
import pet.perpet.data.api.request.main.SubscribeRequest
import pet.perpet.domain.UserStore.user
import pet.perpet.domain.usecase.UseCase2
import pet.perpet.domain.usecase.address.AddressEditUseCase
import pet.perpet.domain.usecase.address.AddressInsertUseCase
import pet.perpet.domain.usecase.card.CardInsertUseCase
import pet.perpet.domain.usecase.search.SearchArticleListUseCase
import pet.perpet.domain.usecase.search.SearchSuppleListUseCase
import pet.perpet.domain.usecase.subsscribe.SubscribeInsertUseCase

class MainUseCase : UseCase2() {

    fun getMainCard(petId: String): MainCardUseCase {
        return getRepositoryBehavior(MainCardUseCase::class.java) {
            it.parameter2 {
                this.userId = user?.id.toString()
                this.petId = petId
            }
        }
    }

    fun getSearchArticle(page: Int): SearchArticleListUseCase {
        return getRepositoryBehavior(SearchArticleListUseCase::class.java) {
            it.parameter2 {
                this.page = page
            }
        }
    }

    fun getSearchProduct(page: Int): SearchSuppleListUseCase {
        return getRepositoryBehavior(SearchSuppleListUseCase::class.java) {
            it.parameter2 {
                this.page = page
            }
        }
    }

    fun getAddressList(address: String): SearchSuppleListUseCase {
        return getRepositoryBehavior(SearchSuppleListUseCase::class.java) {
            it.parameter2 {
                this.page = page
            }
        }
    }

    fun setAddressInsert(recipient: String , phone: String , zipcode: String, address: String, address_detail: String , memo : String): AddressInsertUseCase {
        return getRepositoryBehavior(AddressInsertUseCase::class.java) {
            it.parameter2 {
                this.recipient = recipient
                this.phone = phone
                this.zipcode = zipcode
                this.address = address
                this.address_detail = address_detail
                this.memo = memo

            }
        }
    }

    fun editAddress(id : Int , recipient: String , phone: String , zipcode: String, address: String, address_detail: String, memo:String ): AddressEditUseCase {
        return getRepositoryBehavior(AddressEditUseCase::class.java) {
            it.parameter2 {
                this.id = id
                this.recipient = recipient
                this.phone = phone
                this.zipcode = zipcode
                this.address = address
                this.address_detail = address_detail
                this.memo = memo
            }
        }
    }

    fun setCardInsert(cardNumber: String , cardYear: String , cardPass: String, birth: String ): CardInsertUseCase {
        return getRepositoryBehavior(CardInsertUseCase::class.java) {
            it.parameter2 {
                this.cardNumber = cardNumber
                this.cardYear = cardYear
                this.cardPass = cardPass
                this.birth = birth
            }
        }
    }

    fun editCard(id : Int , recipient: String , phone: String , zipcode: String, address: String, address_detail: String ): AddressEditUseCase {
        return getRepositoryBehavior(AddressEditUseCase::class.java) {
            it.parameter2 {
                this.id = id
                this.recipient = recipient
                this.phone = phone
                this.zipcode = zipcode
                this.address = address
                this.address_detail = address_detail
            }
        }
    }


    fun setSubscribe(subscribeRequest: SubscribeRequest): SubscribeInsertUseCase {
        return getRepositoryBehavior(SubscribeInsertUseCase::class.java) {
            it.parameter2 {
                this.subscribeRequest = subscribeRequest
            }
        }
    }

}