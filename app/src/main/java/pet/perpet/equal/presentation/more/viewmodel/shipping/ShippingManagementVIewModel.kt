package pet.perpet.equal.presentation.more.viewmodel.shipping

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.usecase.address.AddressDeleteUseCase
import pet.perpet.domain.usecase.address.AddressMoreListUseCase
import pet.perpet.equal.presentation.goShippingInsert
import pet.perpet.equal.presentation.more.fragment.viewholder.ItemMoreAddressNotViewHolder
import pet.perpet.equal.presentation.more.fragment.viewholder.ItemMoreAddressViewHolder
import pet.perpet.equal.presentation.safetyCallback
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class ShippingManagementVIewModel(fragment: Fragment) : RecyclerViewPresenter(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================
    val result: MutableLiveData<Boolean> = MutableLiveData(false)


    val items: ArrayList<ListAddress> = arrayListOf()

    //======================================================================
    // Override Methods
    //======================================================================


    override fun onCreateProvider(): BaseRecyclerViewAdapter.Provider {
        return object : BaseRecyclerViewAdapter.Provider {
            override fun getItemHeaderCount(): Int {
                return 0
            }

            override fun getSupportItemCount(): Int {
                return items.size
            }

            override fun getSupportItemViewType(position: Int): Int {
                return viewHolderSet.asViewType(position)
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return items[position]
            }
        }
    }

    override fun onCreateViewHolderSet(): ViewHolderSet? {
        return object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(
                    ItemMoreAddressViewHolder::class.java,
                    ItemMoreAddressNotViewHolder::class.java
                )

            override fun asViewType(position: Int): Int {
                val item = items[position]
                if (item is ListAddress) {
                    item.ordersInfo?.let {
                        if (it.size > 0) {
                            return ItemMoreAddressViewHolder::class.java.hashCode()
                        } else {
                            return ItemMoreAddressNotViewHolder::class.java.hashCode()
                        }
                    } ?: run {
                        return ItemMoreAddressNotViewHolder::class.java.hashCode()
                    }
                } else {
                    return ItemMoreAddressNotViewHolder::class.java.hashCode()
                }

            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================


    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }


    fun onAddressClick(view: View) {
        context?.let {
            it.goShippingInsert()
        }
    }


    fun onDeleteAddress(itemId: Int, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            AddressDeleteUseCase().parameter2 { id = itemId }.success {
                Toast.makeText(activity , "정상적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                callback()
            }.execute()
        }
    }

    fun load(callback: () -> Unit) {
        fun safetyCall() {
            safetyCallback {
                callback()
            }
        }
        items.clear()
        AddressMoreListUseCase()
            .parameter2 {}
            .success {
                it?.content?.let { it1 ->
                    items.addAll(it1)
                    executeViewBinding()
                }
                safetyCall()
            }.fail {
                safetyCall()
            }.execute()
    }

}