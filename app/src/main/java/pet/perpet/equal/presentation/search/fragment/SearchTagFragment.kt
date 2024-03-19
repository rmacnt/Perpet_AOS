package pet.perpet.equal.presentation.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import pet.perpet.data.nonnull
import pet.perpet.domain.model.search.SearchTag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SearchTagFragmentBinding
import pet.perpet.equal.presentation.search.Channel
import pet.perpet.equal.presentation.search.viewholder.SearchTagViewHolder
import pet.perpet.equal.presentation.search.viewmodel.SearchTagViewModel
import pet.perpet.framework.fragment.v2.AbstractSwipePagerRecyclerViewFragment
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.SwipeRefreshRecyclerView

class SearchTagFragment  : AbstractSwipePagerRecyclerViewFragment<SearchTagViewModel>() {


    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SearchTagFragmentBinding

    override val recyclerView: SwipeRefreshRecyclerView
        get() = binding.recyclerview

    override val dependencyEmptyView: View
        get() = binding.tvEmpty

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_tag_fragment, container, false)
    }

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        return FlexboxLayoutManager(pet.perpet.equal.presentation.getContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.CENTER
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = SearchTagFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.setSwipeRefreshLayoutEnable(true)
        binding.recyclerview.setRefreshing(true)
    }


    override fun onRecyclerViewHolderEvent(
        holder: BaseRecyclerViewHolder<*>,
        id: Int,
        bundle: Bundle
    ) {
        super.onRecyclerViewHolderEvent(holder, id, bundle)
        if (holder is SearchTagViewHolder) {
            val item = holder.item
            if (item is SearchTag) {
                Channel.keyword(this.parentFragment)?.postValue((item.name.nonnull()))
            }
        }
    }

}