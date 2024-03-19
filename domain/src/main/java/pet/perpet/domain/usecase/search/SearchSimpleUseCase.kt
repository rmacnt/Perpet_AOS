package pet.perpet.domain.usecase.search

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.search.SearchSimpleEntity
import pet.perpet.data.repository.search.SearchSimpleRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.search.SearchSimple
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class SearchSimpleUseCase :
    UseCase<SearchSimpleRepository, SearchSimpleRepository.Parameter, ArrayList<SearchSimple>, ArrayList<SearchSimpleEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: ArrayList<SearchSimpleEntity>?): ArrayList<SearchSimple>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<ArrayList<SearchSimple>>() {}.type
        )
    }
}