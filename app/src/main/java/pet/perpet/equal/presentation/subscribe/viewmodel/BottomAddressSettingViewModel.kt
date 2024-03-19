package pet.perpet.equal.presentation.subscribe.viewmodel

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.usecase.address.AddressMoreListUseCase
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.goShippingInsert
import pet.perpet.equal.presentation.goShippingManagement
import pet.perpet.equal.presentation.sign.differ.SubscribeSearchItemListDiffer
import pet.perpet.equal.presentation.subscribe.viewholder.SubscribeItemViewHolder
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.DialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class BottomAddressSettingViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val itemListDiffer by lazy {
        SubscribeSearchItemListDiffer()
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
                bundle: Bundle
            ) {

                if (holder is SubscribeItemViewHolder) {
                    val item = holder.item
                    if (item is ListAddress) {
                        val checked = itemListDiffer.snapList().filter {
                            it.isChecked
                        }.toList()
                        checked.forEach {
                            if (it.isChecked) {
                                if (it.address.contains("서울").nonnull() || it.address.contains("경기")
                                        .nonnull() || it.address.contains("인천").nonnull()
                                ) {
                                    fragment?.dispatchDismissToResult(it)
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "서비스 불가지역은 배송정보로 등록할 수 없습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
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
                        if (it.address.contains("서울").nonnull() || it.address.contains("경기")
                                .nonnull() || it.address.contains("인천").nonnull()
                        ) {
                            fragment?.dispatchDismissToResult(it)
                        } else {
                            Toast.makeText(
                                activity,
                                "서비스 불가지역은 배송정보로 등록할 수 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@forEach
                    }
                }
            }
        } else {
            context?.let {
                it.goShippingInsert()
            }
        }

    }

    fun onSettingClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
        context?.let {
            it.goShippingManagement()
        }
    }


    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            Bundle().apply {
                this.putBoolean("success", false)
            }.run {
                (fragment as BottomSheetDialogFragment<*>).dismiss()
            }
        }
    }

    fun dismissToResult() {
        if (fragment is DialogFragment<*>) {
            (fragment as DialogFragment<*>).dismiss()
        }
    }


    fun getAddressList() {
        viewModelScope.launch {
            AddressMoreListUseCase()
                .parameter2 {}
                .success { data ->
                    data?.content?.let { data ->
                        val items = mutableListOf<Any>()
                        this.let {
                            items.addAll(data)
                        }
                        itemListDiffer.allList(items)
                    }
                    executeViewBinding()
                }.fail {
                }.execute()

        }
    }
}