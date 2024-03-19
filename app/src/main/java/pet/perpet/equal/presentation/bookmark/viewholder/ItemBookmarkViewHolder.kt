package pet.perpet.equal.presentation.bookmark.viewholder

import android.app.Activity
import android.app.ActivityOptions
import android.util.Pair
import android.view.ViewGroup
import pet.perpet.domain.model.bookmark.Bookmark
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemBookmarkBinding
import pet.perpet.equal.presentation.bookmark.viewmodel.ItemBookmarkViewModel
import pet.perpet.equal.presentation.goBookmarkDetail
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemBookmarkViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Bookmark>(viewGroup, R.layout.item_bookmark) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemBookmarkBinding = ItemBookmarkBinding.bind(itemView)

    val viewmodel: ItemBookmarkViewModel = ItemBookmarkViewModel()

    init {
        viewmodel.notify {

            val options = ActivityOptions.makeSceneTransitionAnimation(
                context as Activity,
                Pair.create(binding.tvTitle, "title_transform"),
                Pair.create(binding.tvSubTitle, "sub_title_transform"),
                Pair.create(binding.ivBookmark, "image_transform")
            )
            activity.goBookmarkDetail(it , "https://equal.pet/content/ViewApp/${it.id}" )
        }

    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Bookmark?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}