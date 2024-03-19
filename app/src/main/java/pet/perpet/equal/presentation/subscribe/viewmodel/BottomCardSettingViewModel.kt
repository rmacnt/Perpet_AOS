package pet.perpet.equal.presentation.subscribe.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.card.CardList
import pet.perpet.domain.usecase.card.CardListUseCase
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.goPayment
import pet.perpet.equal.presentation.goPaymentInsert
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.equal.presentation.sign.differ.SearchCardItemListDiffer
import pet.perpet.equal.presentation.subscribe.viewholder.SearchCardItemViewHolder
import pet.perpet.equal.presentation.subscribe.viewholder.SubscribeItemViewHolder
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class BottomCardSettingViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val itemListDiffer by lazy {
        SearchCardItemListDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is SearchCardItemViewHolder) {
                    val item = holder.item
                    if (item is CardList) {
                        val checked = itemListDiffer.snapList().filter {
                            it.isChecked
                        }.toList()
                        checked.forEach {
                            if (it.isChecked) {
                                fragment?.dispatchDismissToResult(it)
                                return@forEach
                            }
                        }
                    }
                }
            }
        })
    }


    //======================================================================
    // Public Methods
    //======================================================================


    fun onClick(view: View) {

        if (itemListDiffer.itemSize > 0) {
            if (fragment is BottomSheetDialogFragment<*>) {
                val checked = itemListDiffer.snapList().filter {
                    it.isChecked
                }.toList()
                checked.forEach {
                    if (it.isChecked) {
                        fragment.dispatchDismissToResult(it)
                        return@forEach
                    }
                }
            }
        } else {
            activity?.let {
                it.goPaymentInsert()
            }
        }

    }

    fun onSettingClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
        context?.let {
            it.goPayment()
        }
    }


    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            Bundle().apply {
                this.putBoolean("success", false)
            }.run {
                (fragment as BottomSheetDialogFragment<*>).dismiss(this)
            }
        }
    }


    fun getCardList() {
        viewModelScope.launch {
            CardListUseCase()
                .parameter2 {}
                .success { data ->
                    data?.content?.let { data ->
                        val items = mutableListOf<Any>()
                        this.let {
                            items.addAll(data)
                        }
                        itemListDiffer.clearList()
                        itemListDiffer.allList(items)
                    }
                    itemListDiffer.adapter.notifyDataSetChanged()
                    executeViewBinding()
                }.fail {
                }.execute()

        }
    }
}