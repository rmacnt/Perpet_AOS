package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.R
import pet.perpet.equal.databinding.OrderHistoryFragmentBinding
import pet.perpet.equal.presentation.activityChannelProvider
import pet.perpet.equal.presentation.more.fragment.viewholder.OrderEmptyViewHolder
import pet.perpet.equal.presentation.more.viewmodel.OrderHistoryViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.fragment.v2.AbstractRecyclerViewFragment
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class OrderHistoryFragment : AbstractRecyclerViewFragment<OrderHistoryViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: OrderHistoryFragmentBinding

    override val recyclerView: RecyclerView
        get() = binding.recyclerview

    override fun onCreateEmptyViewHolderProcessor(): BaseRecyclerViewAdapter.EmptyViewHolderProcessor? {
        return object : BaseRecyclerViewAdapter.EmptyViewHolderProcessor {
            override fun onCreateEmptyViewHolder(parent: ViewGroup?): RecyclerViewHolder<*> {
                return OrderEmptyViewHolder(parent)
            }

            override fun getItem(): Any? {
                return null
            }

        }
    }

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
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.order_history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = OrderHistoryFragmentBinding.bind(view)
        binding.model = viewmodel

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