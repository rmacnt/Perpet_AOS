package pet.perpet.domain.usecase.profile

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.profile.DiseaseEntity
import pet.perpet.data.repository.profile.DiseaseCommentRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.model.profile.Disease
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class DiseaseCommentListUseCase :
    UseCase<DiseaseCommentRepository, DiseaseCommentRepository.Parameter, PageContents<Disease>, PageContentsEntity<DiseaseEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<DiseaseEntity>?): PageContents<Disease>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<Disease>>() {}.type
        )
    }

}