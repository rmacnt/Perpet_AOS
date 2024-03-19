package pet.perpet.equal.presentation.subscribe.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.presentation.intakecheck.differ.IntakeItemListDiffer
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class BottomSubscribeCancelViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val itemListDiffer by lazy {
        IntakeItemListDiffer(true)
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


    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
    }

    fun getPetList() {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                it.content?.let { data->
                    val items = mutableListOf<Any>()

                    data.forEach {
                        if(it.latest_order_id == null) {
                            items.add(it)
                        }
                    }
                    itemListDiffer.clearList()
                    itemListDiffer.allList(items)
                }
                executeViewBinding()

            }
        }.fail {
        }.execute()
    }


}