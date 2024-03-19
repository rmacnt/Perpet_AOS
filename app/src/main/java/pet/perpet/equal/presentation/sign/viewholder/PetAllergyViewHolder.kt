package pet.perpet.equal.presentation.sign.viewholder

import android.os.Bundle
import android.view.ViewGroup
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetAllegiBinding
import pet.perpet.equal.presentation.sign.model.PetAllergy
import pet.perpet.equal.presentation.sign.viewmodel.PetAllergyViewModel
import pet.perpet.equal.support.navigation.showAllergySearch
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class PetAllergyViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetAllergy>(
        viewGroup,
        R.layout.item_sign_pet_allegi
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetAllegiBinding =
        ItemSignPetAllegiBinding.bind(itemView)

    val viewmodel: PetAllergyViewModel = PetAllergyViewModel()

    init {
        viewmodel.notify {
            item.error = false
            item.petAllegi = it.toInt()
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }

        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }

        viewmodel.notifyDialog {
            activity?.showAllergySearch(viewmodel.dataSetting) {
                item.error = false
                val items = mutableListOf<Any>()
                it.let {
                    items.addAll(it)
                }
                viewmodel.dataSetting.clear()
                viewmodel.dataSetting.addAll(it)
                viewmodel.itemResultListDiffer.allList(viewmodel.dataSetting)
                viewmodel.model?.allergyId = ""
                var allergyIdResult = ""
                viewmodel.dataSetting.forEach {
                    allergyIdResult = if(allergyIdResult.isNotEmpty()) {
                        allergyIdResult + ","+it.id.toString()
                    }else {
                        it.id.toString()
                    }
                    viewmodel.model?.allergyId = allergyIdResult
                }
                binding.model = viewmodel
                sendEvent(hashCode())
            }
        }

        viewmodel.itemResultListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is SearchFlexItemViewHolder) {
                    item.allergyId = ""
                    item.error = false
                    binding.model = viewmodel

                    val item = holder.item
                    if (item is Allergy) {
                        val it: MutableIterator<*> = viewmodel.dataSetting.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n == item) {
                                it.remove()
                            }
                        }

                        viewmodel.itemResultListDiffer.clearList()
                        viewmodel.itemResultListDiffer.allList(viewmodel.dataSetting)

                        var allergyIdResult = ""
                        viewmodel.dataSetting.forEach {
                            allergyIdResult = if(allergyIdResult.isNotEmpty()) {
                                allergyIdResult + ","+it.id.toString()
                            }else {
                                it.id.toString()
                            }
                            viewmodel.model?.allergyId = allergyIdResult
                        }

                        viewmodel.itemResultListDiffer.adapter.notifyDataSetChanged()
                    }

                }
            }
        })
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetAllergy?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }


}