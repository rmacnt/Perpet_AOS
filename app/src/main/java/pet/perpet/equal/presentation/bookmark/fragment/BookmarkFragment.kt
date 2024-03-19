package pet.perpet.equal.presentation.bookmark.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.data.nonnull
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BookmarkFragmentBinding
import pet.perpet.equal.presentation.activityChannelProvider
import pet.perpet.equal.presentation.bookmark.viewmodel.BookmarkViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.fragment.v2.AbstractRecyclerViewFragment

class BookmarkFragment  : AbstractRecyclerViewFragment<BookmarkViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BookmarkFragmentBinding

    override val recyclerView: RecyclerView
        get() = binding.recyclerview

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityChannelProvider
            ?.get(CardList::class.java)
            ?.observe(this) {
                binding.model = viewmodel
            }

        viewmodel.observeBindingNotify {
            binding.model= viewmodel
        }
    }

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2).apply {
            orientation = GridLayoutManager.VERTICAL
            reverseLayout = false
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return viewmodel.getSpanSize(position)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bookmark_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = BookmarkFragmentBinding.bind(view)
        binding.model = viewmodel
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
                    val last = position == viewmodel.adapter.itemCount - 1
                    val first = position % 2 == 0

                    outRect.top = spacing
                    outRect.left = if(first) spacing24 else spacing / 2
                    outRect.right = if(first) spacing / 2 else spacing24
                    outRect.bottom = if(isLastLine(position, parent)) spacing else 0
                }
            })
        }

        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewmodel.load {
            try {
                notifySupportDataSetChanged()
            } catch (e: Exception) {
                AppLogger.printStackTrace(e)
            }
        }
    }

}