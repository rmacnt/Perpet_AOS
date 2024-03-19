package pet.perpet.equal.presentation.more.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.data.nonnull
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.R
import pet.perpet.equal.databinding.PaymentManagementFragmentBinding
import pet.perpet.equal.presentation.activityChannelProvider
import pet.perpet.equal.presentation.more.fragment.viewholder.ItemPaymentNotCardViewHolder
import pet.perpet.equal.presentation.more.viewmodel.payment.PaymentManagementViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.fragment.v2.AbstractRecyclerViewFragment
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PaymentManagementFragment : AbstractRecyclerViewFragment<PaymentManagementViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: PaymentManagementFragmentBinding

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
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.payment_management_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = PaymentManagementFragmentBinding.bind(view)
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

    override fun onRecyclerViewHolderEvent(
        holder: BaseRecyclerViewHolder<*>,
        id: Int,
        bundle: Bundle,
    ) {
        super.onRecyclerViewHolderEvent(holder, id, bundle)
        binding.model = viewmodel
        when (holder) {
            is ItemPaymentNotCardViewHolder -> {
                val item = holder.item

                viewmodel.onDeleteCard(item.id.nonnull()) {
                    viewmodel.load {
                        try {
                            notifySupportDataSetChanged()
                        } catch (e: Exception) {
                            AppLogger.printStackTrace(e)
                        }
                    }
                }
            }
        }
    }

}