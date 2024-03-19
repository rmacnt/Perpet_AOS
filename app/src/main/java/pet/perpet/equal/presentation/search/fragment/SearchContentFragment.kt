package pet.perpet.equal.presentation.search.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SearchContentFragmentBinding
import pet.perpet.equal.presentation.search.viewmodel.SearchContentViewModel
import pet.perpet.framework.fragment.v2.AbstractSwipePagerRecyclerViewFragment
import pet.perpet.framework.widget.recyclerview.SwipeRefreshRecyclerView


class SearchContentFragment : AbstractSwipePagerRecyclerViewFragment<SearchContentViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SearchContentFragmentBinding

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
        return inflater.inflate(R.layout.search_content_fragment, container, false)
    }

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = SearchContentFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.setSwipeRefreshLayoutEnable(true)
        binding.recyclerview.setRefreshing(true)
        binding.recyclerview.run {
            addItemDecoration(object : RecyclerView.ItemDecoration() {

                private fun isLastLine(
                    position: Int,
                    pRecyclerView: RecyclerView
                ): Boolean {
                    val itemCount = pRecyclerView.adapter?.itemCount.nonnull()
                    val lastItemPosition = itemCount -1
                    if (position == lastItemPosition - 1) {
                        // 마지막 전 아이템이 짝수라면 마지막 아이템이 마지막 줄 이므로
                        // 마지막 전 아이템은 isLastLine: false
                        return !isEvenItem(position)
                    } else if (position == lastItemPosition) {
                        return true
                    }
                    return false
                }

                private fun isEvenItem(position: Int) =
                    position % 2 == 1


                val spacing = resources.getDimensionPixelSize(R.dimen.app_list_spacing_16dip)
                val spacing24 = resources.getDimensionPixelSize(R.dimen.app_list_spacing_24dip)
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    val position = parent.getChildAdapterPosition(view)
                    val first = position % 2 == 0

                    outRect.top = spacing
                    outRect.left = if(first) spacing24 else spacing / 2
                    outRect.right = if(first) spacing / 2 else spacing24
                    outRect.bottom = if(isLastLine(position, parent)) spacing else 0
                }
            })
        }
    }

}