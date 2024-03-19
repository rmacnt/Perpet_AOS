package pet.perpet.domain.usecase.medical

import com.google.gson.reflect.TypeToken
import pet.perpet.data.GsonLoader
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.medical.MedicalEntity
import pet.perpet.data.repository.medical.MedicalListRepository
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class MedicalListUseCase :
    UseCase<MedicalListRepository, MedicalListRepository.Parameter, PageContents<Medical>, PageContentsEntity<MedicalEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<MedicalEntity>?): PageContents<Medical>? {
        return raw?.let {
            GsonLoader.gson.fromJson(
                it.toJson(),
                object : TypeToken<PageContents<Medical>>() {}.type
            )
        }
    }

}