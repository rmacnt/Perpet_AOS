package pet.perpet.equal.presentation.search

import androidx.fragment.app.Fragment
import pet.perpet.equal.presentation.base.bus.RxBus2
import pet.perpet.equal.presentation.search.model.SearchKeyword
import pet.perpet.framework.fragment.safetyCallback

fun subscribe(fragment: Fragment, callback: (searchKeyword: SearchKeyword) -> Unit) {
    RxBus2.subscribe(fragment, SearchKeyword::class.java) {
        fragment.safetyCallback {
            SearchKeywordStore.searchKeyword?.let { it1 -> callback(it1) }
        }
    }
}

fun SearchKeywordStore.update(keyword: String) {
    saveKeyword(SearchKeyword(keyword))
    RxBus2.post(SearchKeyword(keyword))
}