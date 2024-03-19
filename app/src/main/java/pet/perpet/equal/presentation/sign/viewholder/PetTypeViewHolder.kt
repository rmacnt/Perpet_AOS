package pet.perpet.equal.presentation.sign.viewholder

import android.os.Bundle
import android.view.ViewGroup
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.model.profile.Breed
import pet.perpet.domain.usecase.profile.SignUseCase
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetNameTypeBinding
import pet.perpet.equal.presentation.asAppCompatActivity
import pet.perpet.equal.presentation.createMainScope
import pet.perpet.equal.presentation.sign.model.PetType
import pet.perpet.equal.presentation.sign.viewmodel.PetTypeViewModel
import pet.perpet.equal.support.navigation.showBreedSearch
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class PetTypeViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetType>(
        viewGroup,
        R.layout.item_sign_pet_name_type
    ) {

    //======================================================================
    // Variables
    //======================================================================

    private val useCase = SignUseCase()

    val binding: ItemSignPetNameTypeBinding =
        ItemSignPetNameTypeBinding.bind(itemView)

    val viewmodel: PetTypeViewModel = PetTypeViewModel()

    private var getPetType: String? = null

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }
        viewmodel.notifyDialog {
            activity.asAppCompatActivity()?.let {
                item.petSelect?.let { type ->
                    activity?.showBreedSearch(type , item.petBreedId.toString()) {
                        item.error = false
                        item.petTypeName = it.name
                        item.petBreedId = it.id
                        viewmodel.itemListDiffer.clearList()
                        binding.model = viewmodel
                        sendEvent(hashCode())
                    }
                }
            }
        }

        viewmodel.itemListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is BreedTwoViewHolder) {
                    val data = holder.item
                    if (data is Breed) {
                        item.error = false
                        item.petTypeName = data.name
                        item.petBreedId = data.id
                        viewmodel.itemListDiffer.clearList()
                        binding.model = viewmodel
                        sendEvent(hashCode())
                    }
                }
            }
        })

    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetType?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel

        if (viewmodel.itemListDiffer.itemSize == 0) {
            getPetType(item.petSelect)
            getPetType = viewmodel.model?.petSelect
        } else {
            getPetType?.let {
                if (it.isNotEmpty() && getPetType != viewmodel.model?.petSelect) {
                    getPetType = viewmodel.model?.petSelect
                    getPetType(item.petSelect)
                } else {
                    getPetType = viewmodel.model?.petSelect
                }
            }
        }
    }

    fun getPetType(type: String?) {
        createMainScope().launch {
            useCase.getBreedList("", type.nonnull()).success { data ->
                data?.content?.let { data ->
                    val items = mutableListOf<Any>()
                    this.let {
                        data.forEach {
                            if (it.is_main == true) {
                                items.add(it)
                            }
                        }
                    }
                    viewmodel.itemListDiffer.allList(items)
                }
                viewmodel.itemListDiffer.adapter.notifyDataSetChanged()
            }.fail {
            }.execute()

        }
    }


}