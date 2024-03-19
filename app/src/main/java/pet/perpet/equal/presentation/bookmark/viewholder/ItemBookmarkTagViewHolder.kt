package pet.perpet.equal.presentation.bookmark.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.bookmark.BookmarkTag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemBookmarkTagBinding
import pet.perpet.equal.presentation.bookmark.viewmodel.ItemBookmarkTagViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemBookmarkTagViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<BookmarkTag>(viewGroup, R.layout.item_bookmark_tag) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemBookmarkTagBinding = ItemBookmarkTagBinding.bind(itemView)

    val viewmodel: ItemBookmarkTagViewModel = ItemBookmarkTagViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: BookmarkTag?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}