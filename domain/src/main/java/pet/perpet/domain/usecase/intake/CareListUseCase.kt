package pet.perpet.domain.usecase.intake

import IntakeData
import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.intake.IntakeDataEntity
import pet.perpet.data.repository.intake.CareListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class CareListUseCase :
    UseCase<CareListRepository, CareListRepository.Parameter, ArrayList<IntakeData>, ArrayList<IntakeDataEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: ArrayList<IntakeDataEntity>?): ArrayList<IntakeData>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<ArrayList<IntakeData>>() {}.type
        )
    }
}