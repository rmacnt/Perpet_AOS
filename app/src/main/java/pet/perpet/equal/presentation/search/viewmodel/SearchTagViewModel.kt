package pet.perpet.equal.presentation.search.viewmodel

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.search.SearchTagListUseCase
import pet.perpet.equal.presentation.search.fragment.SearchFaqFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchTagFragmentArgs
import pet.perpet.equal.presentation.search.viewholder.SearchTagViewHolder
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.fragment.v2.PagerRecyclerViewPresenter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class SearchTagViewModel(fragment: Fragment) : PagerRecyclerViewPresenter(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    private val args by lazy {
        SearchTagFragmentArgs.fromBundle(arguments)
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateProvider(): PagerProvider {
        return object : PagerProvider() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolderSet(): ViewHolderSet? {
        return object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(SearchTagViewHolder::class.java)

            override fun asViewType(position: Int): Int {
                return SearchTagViewHolder::class.java.hashCode()
            }
        }
    }


    override fun onCreateDataSource(): PagerDataSource {
        return object : PagerDataSource() {
            override fun loadInitial(
                params: LoadInitialParams<Int>,
                callback: LoadInitialCallback<Int, Any>,
            ) {
                SearchTagListUseCase()
                    .parameter2 {
                        page = 0
                        type = "faq"
                        search = args.keyword.nonnull()
                    }
                    .sync()?.content?.run {
                        if (this.size > 0) {
                            this
                        } else {
                            null
                        }
                        callback.onResult(this as List<Any>, null, 1)
                    }
            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {
                SearchTagListUseCase().parameter2 {
                    page = params.key
                    type = "faq"
                    search = args.keyword.nonnull()

                }.sync()?.content?.run {
                    if (this.size > 0) {
                        this
                    } else {
                        null
                    }
                }?.run {
                    callback.onResult(this as List<Any>, params.key + 1)
                }
            }

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {
            }
        }
    }
}