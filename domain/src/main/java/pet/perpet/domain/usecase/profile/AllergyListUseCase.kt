package pet.perpet.domain.usecase.profile

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.profile.AllergyEntity
import pet.perpet.data.repository.profile.AllergyRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AllergyListUseCase :
    UseCase<AllergyRepository, AllergyRepository.Parameter, PageContents<Allergy>, PageContentsEntity<AllergyEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<AllergyEntity>?): PageContents<Allergy>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<Allergy>>() {}.type
        )
    }

}