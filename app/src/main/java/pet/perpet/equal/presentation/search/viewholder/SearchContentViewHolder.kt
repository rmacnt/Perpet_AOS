package pet.perpet.equal.presentation.search.viewholder

import android.app.Activity
import android.app.ActivityOptions
import android.util.Pair
import android.view.ViewGroup
import pet.perpet.domain.model.search.SearchArticle
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchContentBinding
import pet.perpet.equal.presentation.goSearchDetail
import pet.perpet.equal.presentation.search.viewmodel.ItemSearchContentViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SearchContentViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<SearchArticle>(viewGroup, R.layout.item_search_content) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSearchContentBinding = ItemSearchContentBinding.bind(itemView)

    val viewmodel: ItemSearchContentViewModel = ItemSearchContentViewModel()

    init {
        viewmodel.notify {

            val options = ActivityOptions.makeSceneTransitionAnimation(
                context as Activity,
                Pair.create(binding.tvTitle, "title_transform"),
                Pair.create(binding.tvSubTitle, "sub_title_transform"),
                Pair.create(binding.ivContent, "image_transform")
            )
            activity.goSearchDetail(it , "https://equal.pet/content/ViewApp/${it.id}")
        }

    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: SearchArticle?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}