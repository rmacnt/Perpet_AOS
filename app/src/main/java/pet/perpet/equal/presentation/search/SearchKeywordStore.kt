package pet.perpet.equal.presentation.search

import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.domain.model.search.SearchSimple
import pet.perpet.equal.presentation.search.model.SearchKeyword

object SearchKeywordStore {

    val searchRecent: ArrayList<SearchSimple>?
        get() = searchDataSource.get()

    val searchKeyword: SearchKeyword?
        get() = searchKeywordSource.get()

    private val searchDataSource: CashDataSource<ArrayList<SearchSimple>> by lazy {
        object : CashDataSource<ArrayList<SearchSimple>>() {
            override val key: String
                get() = "searchDataSource"
        }
    }
    private val searchKeywordSource: CashDataSource<SearchKeyword> by lazy {
        object : CashDataSource<SearchKeyword>() {
            override val key: String
                get() = "searchKeywordSource"
        }
    }

    fun sync(list: SearchSimple) {
        val items = searchDataSource.get()
        val item = searchDataSource.get()
        if(items?.size.nonnull() > 9) {
            items?.remove(items.first())
        }

        items?.forEach {
            if(list != it) {
                item?.add(list)
            }
            item?.add(list)
        }
        searchDataSource.clear()
        searchDataSource.save(item)

    }

    fun saveKeyword(badge: SearchKeyword?) {
        searchKeywordSource.clear()
        searchKeywordSource.save(badge)
    }
}