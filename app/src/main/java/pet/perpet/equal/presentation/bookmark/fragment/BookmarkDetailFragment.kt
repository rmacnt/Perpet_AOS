package pet.perpet.equal.presentation.bookmark.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.data.nonnull
import pet.perpet.domain.model.bookmark.BookmarkTag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BookmarkDetailFragmentBinding
import pet.perpet.equal.presentation.bookmark.viewholder.ItemBookmarkTagViewHolder
import pet.perpet.equal.presentation.bookmark.viewmodel.BookmarkDetailViewModel
import pet.perpet.equal.presentation.goSearch
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.widget.OnVisibleOffsetChangedListener
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class BookmarkDetailFragment : Fragment<BookmarkDetailViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BookmarkDetailFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bookmark_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = BookmarkDetailFragmentBinding.bind(view)
        binding.model = viewmodel

        activity?.window?.decorView?.setBackgroundColor(Color.WHITE)
        binding.slLayout.setGradualBackground(activity?.window?.decorView?.background)
        binding.slLayout.setLayoutScrollListener {
            activity?.onBackPressed()
        }
        binding.appbar.addOnOffsetChangedListener(object : OnVisibleOffsetChangedListener() {
            override fun handleViewVisibility(percentage: Float) {
                try {
                    // 라이프사이클이 onDestroyView 상태일 때 이벤트를 알림 받을 수 있게 된 경우 앱이 종료된다.
                    val title = 0.6f
                    val notify = percentage >= title
                    if (notify != viewmodel.isShowToolbar) {
                        viewmodel.isShowToolbar = notify
                        binding.model = viewmodel
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        viewmodel.args.bookmark?.tags?.let {
            viewmodel.itemBookmarkListDiffer.allList(it)
        }

        val hander = Handler()
        hander.postDelayed({
            binding.recyclerview.visibility = View.VISIBLE
        }, 2000)

        viewmodel.itemBookmarkListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            @SuppressLint("SimpleDateFormat")
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is ItemBookmarkTagViewHolder) {
                    val data = holder.item
                    if (data is BookmarkTag) {
                        activity?.goSearch(data.name.nonnull())
                    }
                }
            }
        })
        super.onViewCreated(view, savedInstanceState)

    }

}