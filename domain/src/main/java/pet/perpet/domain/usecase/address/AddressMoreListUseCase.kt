package pet.perpet.domain.usecase.address

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.address.ListAddressEntity
import pet.perpet.data.repository.address.AddressMoreListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AddressMoreListUseCase :
    UseCase<AddressMoreListRepository, AddressMoreListRepository.Parameter, PageContents<ListAddress>, PageContentsEntity<ListAddressEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<ListAddressEntity>?): PageContents<ListAddress>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<ListAddress>>() {}.type
        )
    }

}