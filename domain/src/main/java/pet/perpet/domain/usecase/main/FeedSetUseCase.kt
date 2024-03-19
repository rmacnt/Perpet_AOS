package pet.perpet.domain.usecase.main

import pet.perpet.data.api.entity.main.FeedEntity
import pet.perpet.data.repository.main.FeedSetRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.main.Feed
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class FeedSetUseCase   :
    UseCase<FeedSetRepository, FeedSetRepository.Parameter, Feed, FeedEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: FeedEntity?): Feed? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Feed::class.java)
        }
    }
}