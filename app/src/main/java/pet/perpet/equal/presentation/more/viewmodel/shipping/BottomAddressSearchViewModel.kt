package pet.perpet.equal.presentation.more.viewmodel.shipping

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.Juso
import pet.perpet.domain.usecase.address.AddressListUseCase
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.sign.differ.AddressItemListDiffer
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.DialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class BottomAddressSearchViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val address: MutableLiveData<String> = MutableLiveData()

    val itemListDiffer by lazy {
        AddressItemListDiffer()
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

    //======================================================================
    // Public Methods
    //======================================================================

    fun onAddressTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        address.value = text.toString()

        if(address.value.nonnull().isNotEmpty()) {
            getAddressList(text.toString())

        }


        executeViewBinding()
    }


    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            Bundle().apply {
                this.putBoolean("success", false)
            }.run {
                fragment?.dispatchDismissToResult(this)
            }
        }
    }


    fun dismissToResult(juso: Juso) {
        if (fragment is BottomSheetDialogFragment<*>) {
            fragment?.dispatchDismissToResult(juso)
        }
    }

    fun getAddressList(addressData: String) {
        viewModelScope.launch {
            if(addressData.length > 1) {
                AddressListUseCase().parameter2 {
                    this.address = addressData
                }.success {data ->
                    data?.juso?.let { jusoData ->
                        val items = mutableListOf<Any>()
                        this.let {
                            items.addAll(jusoData)
                        }
                        itemListDiffer.clearList()
                        itemListDiffer.allList(items)
                    }
                }.fail {
                }.execute()
            }else {
                itemListDiffer.clearList()
            }
        }
    }
}