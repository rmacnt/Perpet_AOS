package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.profile.Disease
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemPetDiseaseCommentBinding
import pet.perpet.equal.presentation.sign.viewmodel.ItemDiseaseCommentViewModel
import pet.perpet.equal.support.navigation.showDiseaseSearch
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemDiseaseCommentViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Disease>(viewGroup, R.layout.item_pet_disease_comment) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemPetDiseaseCommentBinding = ItemPetDiseaseCommentBinding.bind(itemView)

    val viewmodel: ItemDiseaseCommentViewModel = ItemDiseaseCommentViewModel()

    init {

        viewmodel.notifyDialog {
            activity?.showDiseaseSearch(item.dataSetting , item.id.toString()) {
                val items = mutableListOf<Any>()
                it.let {
                    items.addAll(it)
                }
                item.dataSetting = ArrayList()
                item.dataSetting.addAll(it)

                if(item.dataSetting.size > 0) {
                    var diseaseIdResult = ""
                    item.dataSetting.forEach {
                        diseaseIdResult = if(diseaseIdResult.isNotEmpty()) {
                            diseaseIdResult + ","+it.name.toString()
                        }else {
                            it.name.toString()
                        }
                        viewmodel.model?.comment = diseaseIdResult

                    }
                } else {
                    viewmodel.model?.comment = ""
                }
                sendEvent(hashCode())
                binding.model = viewmodel
            }
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Disease?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}