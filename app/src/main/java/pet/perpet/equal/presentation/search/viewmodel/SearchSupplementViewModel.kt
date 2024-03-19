package pet.perpet.equal.presentation.search.viewmodel

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.search.SearchSuppleListUseCase
import pet.perpet.equal.presentation.examination.fragment.ExaminationHealthResultDetailFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchSupplementFragmentArgs
import pet.perpet.equal.presentation.search.viewholder.SearchProductViewHolder
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.fragment.v2.PagerRecyclerViewPresenter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class SearchSupplementViewModel (fragment: Fragment) : PagerRecyclerViewPresenter(fragment) {

    private val args by lazy {
        SearchSupplementFragmentArgs.fromBundle(arguments)
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
                get() = arrayOf(SearchProductViewHolder::class.java)

            override fun asViewType(position: Int): Int {
                return SearchProductViewHolder::class.java.hashCode()
            }
        }
    }

    override fun onCreateDataSource(): PagerDataSource {
        return object : PagerDataSource() {
            override fun loadInitial(
                params: LoadInitialParams<Int>,
                callback: LoadInitialCallback<Int, Any>,
            ) {
                SearchSuppleListUseCase()
                    .parameter2 {
                        page = 0
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
                SearchSuppleListUseCase().parameter2 {

                    page = params.key
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