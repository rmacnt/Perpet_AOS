package pet.perpet.equal.presentation.more.viewmodel.payment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pet.perpet.domain.model.card.CardList
import pet.perpet.domain.usecase.card.CardDeleteUseCase
import pet.perpet.domain.usecase.card.CardListUseCase
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.goPaymentInsert
import pet.perpet.equal.presentation.more.fragment.viewholder.ItemPaymentCardViewHolder
import pet.perpet.equal.presentation.more.fragment.viewholder.ItemPaymentNotCardViewHolder
import pet.perpet.equal.presentation.safetyCallback
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class PaymentManagementViewModel(fragment: Fragment) : RecyclerViewPresenter(fragment) {

    val result: MutableLiveData<Boolean> = MutableLiveData(false)

    val items: ArrayList<CardList> = arrayListOf()

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
                    ItemPaymentCardViewHolder::class.java,
                    ItemPaymentNotCardViewHolder::class.java
                )

            override fun asViewType(position: Int): Int {
                val item = items[position]
                if(item is CardList) {
                    item.ordersInfo?.let {
                        if(it.size > 0) {
                            return ItemPaymentCardViewHolder::class.java.hashCode()
                        }else {
                            return ItemPaymentNotCardViewHolder::class.java.hashCode()
                        }
                    } ?: run {
                        return ItemPaymentNotCardViewHolder::class.java.hashCode()
                    }
                } else {
                    return ItemPaymentNotCardViewHolder::class.java.hashCode()
                }

            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onHomeClick(view: View) {
        context?.let {
            it.goHome()
        }

    }

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onPaymentAddClick(view: View) {
        activity?.let {
            it.goPaymentInsert()
//            it.goPaymentSelect()
        }
    }

    fun onDeleteCard(itemId: Int , callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            CardDeleteUseCase().parameter2 { id = itemId }.success {
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
        CardListUseCase()
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