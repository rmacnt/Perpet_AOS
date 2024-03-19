package pet.perpet.domain.usecase.profile

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.profile.BreedEntity
import pet.perpet.data.repository.profile.BreedRepository
import pet.perpet.domain.GsonLoader.gson
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.model.profile.Breed
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class BreedListUseCase :
    UseCase<BreedRepository, BreedRepository.Parameter, PageContents<Breed>, PageContentsEntity<BreedEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<BreedEntity>?): PageContents<Breed>? {
        return gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<Breed>>() {}.type
        )
    }

}