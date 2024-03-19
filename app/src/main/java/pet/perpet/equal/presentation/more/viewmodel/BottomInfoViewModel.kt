package pet.perpet.equal.presentation.more.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.domain.usecase.address.AddressMoreListUseCase
import pet.perpet.domain.usecase.card.CardListUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.more.differ.OrderInfoListDiffer
import pet.perpet.equal.presentation.more.fragment.BottomInfoFragmentArgs
import pet.perpet.equal.presentation.more.fragment.viewholder.OrderInfoViewHolder
import pet.perpet.equal.presentation.more.model.OrderInfo
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class BottomInfoViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val args by lazy {
        BottomInfoFragmentArgs.fromBundle(arguments)
    }

    val subTitle: String?
    get() = if(args.type == "card") getString(R.string.bottom_info_comment2)
            else getString(R.string.bottom_info_comment4)

    val itemListDiffer by lazy {
        OrderInfoListDiffer()
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

        if(args.type == "card") {
            getCardList()
        } else {
            getAddressList()
        }

        itemListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle
            ) {

                if (holder is OrderInfoViewHolder) {
                    (fragment as BottomSheetDialogFragment<*>).dismiss()
                }
            }
        })
    }

    //======================================================================
    // Public Methods
    //======================================================================


    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            Bundle().apply {
                this.putBoolean("success", false)
            }.run {
                (fragment as BottomSheetDialogFragment<*>).dismiss(this)
            }
        }
    }

    fun getAddressList() {
        viewModelScope.launch {
            AddressMoreListUseCase()
                .parameter2 {}
                .success {data->
                    data?.content?.let { data ->
                        val items = mutableListOf<Any>()
                        this.let {
                            data.forEach {
                                it.ordersInfo?.forEach { order->
                                    val orderInfo = OrderInfo(String.format(getString(R.string.bottom_info_comment5) , order.pet_name) ,
                                        "address" ,  order , order.pet_name)
                                    items.add(orderInfo)
                                }
                            }
                        }
                        itemListDiffer.clearList()
                        itemListDiffer.allList(items)
                    }
                    executeViewBinding()
                }.fail {
                }.execute()

        }
    }

    fun getCardList() {
        viewModelScope.launch {
            CardListUseCase()
                .parameter2 {}
                .success {data->
                    data?.content?.let { data ->
                        val items = mutableListOf<Any>()
                        this.let {
                            data.forEach {
                                it.ordersInfo?.forEach { order->
                                    items.add(order)
                                    val orderInfo = OrderInfo(String.format(getString(R.string.bottom_info_comment5) , order.pet_name) ,
                                        "address" ,  order , order.pet_name)
                                    items.add(orderInfo)
                                }
                            }
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