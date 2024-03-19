package pet.perpet.equal.presentation.search.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.search.SearchSimpleUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.search.SearchKeywordStore
import pet.perpet.equal.presentation.search.differ.SearchListDiffer
import pet.perpet.equal.presentation.search.differ.SearchRecentListDiffer
import pet.perpet.equal.presentation.search.fragment.SearchContentFragment
import pet.perpet.equal.presentation.search.fragment.SearchContentFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchFaqFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchSupplementFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchTagFragmentArgs
import pet.perpet.equal.presentation.search.update
import pet.perpet.equal.support.navigation.toFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class SearchViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val args by lazy {
        SearchFragmentArgs.fromBundle(arguments)
    }

    var keyword: String? = ""

    val keywordSetting: String?
        get() = if (keyword?.length.nonnull() > 0)
            String.format(getString(R.string.search_comment8), keyword)
        else
            getString(R.string.search_comment6)

    val empty: String?
        get() = emptyValue.value

    val emptyValue: MutableLiveData<String> = MutableLiveData(getString(R.string.search_comment7))

    val searchListDiffer by lazy {
        SearchListDiffer()
    }

    val searchRecentListDiffer by lazy {
        SearchRecentListDiffer()
    }

    val searchMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return searchListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }

        }

    }


    val searchRecentMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return searchRecentListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    //======================================================================
    // Public Variables
    //======================================================================

    val fragmentAdapter: androidx.fragment.app.FragmentStatePagerAdapter by lazy {
        val f = object :
            androidx.fragment.app.FragmentStatePagerAdapter(fragment.childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        SearchContentFragmentArgs.Builder()
                            .setKeyword(keyword)
                            .build()
                            .toFragment()
                    }
                    1 -> {
                        SearchSupplementFragmentArgs.Builder()
                            .setKeyword(keyword)
                            .build()
                            .toFragment()
                    }
                    2 -> {
                        SearchFaqFragmentArgs.Builder()
                            .setKeyword(keyword)
                            .build()
                            .toFragment()

                    }
                    3 -> {
                        SearchTagFragmentArgs.Builder()
                            .setKeyword(keyword)
                            .build()
                            .toFragment()
                    }
                    else -> SearchContentFragment()
                }
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when (position) {
                    0 -> getString(R.string.search_comment1)
                    1 -> getString(R.string.search_comment2)
                    2 -> getString(R.string.search_comment3)
                    3 -> getString(R.string.search_comment4)
                    else -> getString(R.string.search_comment1)
                }
            }

            override fun getCount(): Int {
                return 2
            }

            override fun getItemPosition(`object`: Any): Int {
                return POSITION_NONE
            }
        }
        f
    }

    fun onFinishClick(view: View) {
        activity?.let {
            if(keyword.nonnull().isEmpty()) {
                it.finish()
            }else {
                SearchKeywordStore.update("")
            }
        }
    }

    fun ouTouchOutClick(view: View) {
        executeViewBindingNew()
    }

    fun onDeleteClick(view: View) {
        keyword = ""
        executeViewBinding()
    }


    fun getSearch() {
        if(keyword.nonnull().isNotEmpty()) {
            viewModelScope.launch {
                SearchSimpleUseCase().parameter2 {
                    this.search = keyword.nonnull()
                }.success {
                    it?.let { data ->
                        val items = mutableListOf<Any>()
                        this.let {
                            items.addAll(data)
                        }
                        searchListDiffer.clearList()
                        searchListDiffer.allList(items)
                        executeViewBinding()
                    }
                }.fail {
                }.execute()
            }
        } else {
            searchListDiffer.clearList()
            executeViewBinding()

        }


    }

    fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        keyword = text.toString()
        getSearch()
    }
}