package pet.perpet.domain.usecase.tracker

import pet.perpet.data.api.entity.tracker.TrackerEntity
import pet.perpet.data.repository.tracker.TrackerGetRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.tracker.Tracker
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class TrackerGetUseCase  :
    UseCase<TrackerGetRepository, TrackerGetRepository.Parameter, Tracker, TrackerEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: TrackerEntity?): Tracker? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Tracker::class.java)
        }
    }
}