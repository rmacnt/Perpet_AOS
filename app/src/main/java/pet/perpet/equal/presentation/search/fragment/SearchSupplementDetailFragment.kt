package pet.perpet.equal.presentation.search.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import pet.perpet.data.nonnull
import pet.perpet.domain.model.search.Tag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SearchSupplementDetailFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.search.Channel
import pet.perpet.equal.presentation.search.SearchKeywordStore
import pet.perpet.equal.presentation.search.update
import pet.perpet.equal.presentation.search.viewholder.ItemTagViewHolder
import pet.perpet.equal.presentation.search.viewmodel.SearchSupplementDetailViewModel
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.widget.recyclerview.AdapterUtil
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class SearchSupplementDetailFragment : Fragment<SearchSupplementDetailViewModel>() {
    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SearchSupplementDetailFragmentBinding

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
        return inflater.inflate(R.layout.search_supplement_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SearchSupplementDetailFragmentBinding.bind(view)
        binding.model = viewmodel

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        binding.recyclerview.adapter = viewmodel.adapter

        val recyclerViewTag = binding.recyclerviewTag
        recyclerViewTag.layoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.CENTER
        }
        recyclerViewTag.adapter = viewmodel.tagAdapter

        viewmodel.tagAdapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            @SuppressLint("SimpleDateFormat")
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is ItemTagViewHolder) {
                    val data = holder.item
                    if (data is Tag) {
                        SearchKeywordStore.update(data.name.nonnull())
                        activity?.finish()
                    }
                }
            }
        })


        AdapterUtil.notifySupportDataSetChanged(viewmodel.adapter, true)
        AdapterUtil.notifySupportDataSetChanged(viewmodel.tagAdapter, true)
    }

}