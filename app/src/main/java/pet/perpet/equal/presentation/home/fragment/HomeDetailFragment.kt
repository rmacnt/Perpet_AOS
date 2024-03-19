package pet.perpet.equal.presentation.home.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.data.nonnull
import pet.perpet.domain.model.main.HomeTag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.HomeDetailFragmentBinding
import pet.perpet.equal.presentation.goSearch
import pet.perpet.equal.presentation.home.viewholder.ItemHomeTagViewHolder
import pet.perpet.equal.presentation.home.viewmodel.HomeDetailViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.widget.OnVisibleOffsetChangedListener
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener


class HomeDetailFragment : Fragment<HomeDetailViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: HomeDetailFragmentBinding

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
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.home_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = HomeDetailFragmentBinding.bind(view)
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

        viewmodel.args.mainCard?.tags?.let {
            viewmodel.itemPetListDiffer.allList(it)
        }

        val hander = Handler()
        hander.postDelayed({
            binding.recyclerview.visibility = View.VISIBLE
        }, 2000)

//        requireActivity().window?.run {
//            this.setSystemUI(
//                statusBarColor = Color.TRANSPARENT,
//                navigationBarColor = R.color.app_color_white.getColor(requireContext()),
//                overlay = true,
//                light = true
//            )
//        }

        viewmodel.itemPetListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            @SuppressLint("SimpleDateFormat")
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is ItemHomeTagViewHolder) {
                    val data = holder.item
                    if (data is HomeTag) {
                        activity?.goSearch(data.name.nonnull())
                    }
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)

    }


}