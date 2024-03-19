package pet.perpet.equal.presentation.more.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import pet.perpet.domain.model.order.Inventory
import pet.perpet.domain.usecase.order.OrderHistoryListUseCase
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.more.fragment.viewholder.ItemOrderViewHolder
import pet.perpet.equal.presentation.safetyCallback
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class OrderHistoryViewModel(fragment: Fragment) : RecyclerViewPresenter(fragment) {

    val result: MutableLiveData<Boolean> = MutableLiveData(false)

    val items: ArrayList<Inventory> = arrayListOf()

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
                get() = arrayOf(ItemOrderViewHolder::class.java,)

            override fun asViewType(position: Int): Int {
                return ItemOrderViewHolder::class.java.hashCode()
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

    fun load(callback: () -> Unit) {
        fun safetyCall() {
            safetyCallback {
                callback()
            }
        }
        items.clear()
        OrderHistoryListUseCase()
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