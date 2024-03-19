package pet.perpet.domain.usecase.pet

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.pet.PetEntity
import pet.perpet.data.repository.pet.PetListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.model.pet.Pet
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class PetListUseCase :
    UseCase<PetListRepository, PetListRepository.Parameter, PageContents<Pet>, PageContentsEntity<PetEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<PetEntity>?): PageContents<Pet>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<Pet>>() {}.type
        )
    }

}