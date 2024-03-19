package pet.perpet.equal.presentation.intakecheck.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.presentation.goPagerWeight
import pet.perpet.equal.presentation.goSign
import pet.perpet.equal.presentation.intakecheck.differ.IntakeItemListDiffer
import pet.perpet.equal.presentation.intakecheck.viewholder.ItemIntakeViewHolder
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class BottomHealthViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val itemListDiffer by lazy {
        IntakeItemListDiffer(false)
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

                if (holder is ItemIntakeViewHolder) {
                    (fragment as BottomSheetDialogFragment<*>).dismiss()
                }
            }
        })
    }


    //======================================================================
    // Public Methods
    //======================================================================

    fun onPetInsertClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
        context?.let {
            it.goPagerWeight()
        }
    }


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
                        if(it.latest_medical_id == null) {
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