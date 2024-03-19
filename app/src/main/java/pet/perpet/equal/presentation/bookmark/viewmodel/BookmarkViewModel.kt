package pet.perpet.equal.presentation.bookmark.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.domain.model.bookmark.Bookmark
import pet.perpet.domain.usecase.bookmark.BookmarkListUseCase
import pet.perpet.equal.presentation.bookmark.fragment.BookmarkFragmentArgs
import pet.perpet.equal.presentation.bookmark.viewholder.ItemBookmarkViewHolder
import pet.perpet.equal.presentation.safetyCallback
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class BookmarkViewModel (fragment: Fragment) : RecyclerViewPresenter(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val items: ArrayList<Bookmark> = arrayListOf()

    private val args by lazy {
        BookmarkFragmentArgs.fromBundle(arguments)
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateProvider(): BaseRecyclerViewAdapter.Provider {
        return object : BaseRecyclerViewAdapter.Provider {
            override fun getItemHeaderCount(): Int {
                return 0
            }

            override fun getSupportItemCount(): Int {
                return items.size
            }

            override fun getSupportItemViewType(position: Int): Int {
                return viewHolderSet.asViewType(position)
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return items[position]
            }
        }
    }

    override fun onCreateViewHolderSet(): ViewHolderSet? {
        return object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(ItemBookmarkViewHolder::class.java)

            override fun asViewType(position: Int): Int {
                return ItemBookmarkViewHolder::class.java.hashCode()
            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun getSpanSize(position: Int): Int {
        return 1
    }


    fun load(callback: () -> Unit) {
        fun safetyCall() {
            safetyCallback {
                callback()
            }
        }
        items.clear()
        BookmarkListUseCase()
            .parameter2 {
                petId = args.petId.nonnull()
            }
            .success {
                it?.content?.let { it1 ->
                    items.addAll(it1)
                    executeViewBinding()
                }
                safetyCall()
            }.fail {
                safetyCall()
            }.execute()
    }
}