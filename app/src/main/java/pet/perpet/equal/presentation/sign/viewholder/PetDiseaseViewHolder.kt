package pet.perpet.equal.presentation.sign.viewholder

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.domain.model.profile.Disease
import pet.perpet.domain.usecase.profile.SignUseCase
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetDiseaseBinding
import pet.perpet.equal.presentation.createMainScope
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.equal.presentation.sign.model.PetCatEar
import pet.perpet.equal.presentation.sign.model.PetDisease
import pet.perpet.equal.presentation.sign.viewmodel.PetDiseaseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class PetDiseaseViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetDisease>(
        viewGroup,
        R.layout.item_sign_pet_disease
    ) {

    //======================================================================
    // Variables
    //======================================================================

    private val useCase = SignUseCase()

    val binding: ItemSignPetDiseaseBinding =
        ItemSignPetDiseaseBinding.bind(itemView)

    val viewmodel: PetDiseaseViewModel = PetDiseaseViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.petDisease = it.toInt()
            item.error = false
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }

        viewmodel.itemDiseaseListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is DiseaseViewHolder) {
                    val data = holder.item
                    if (data is Disease) {
                        data.toggle()
                        item.diseaseId = ""
                        item.error = false
                        binding.model = viewmodel

                        if(data.isChecked)
                            viewmodel.itemDiseaseCommentListDiffer.add(data)
                        else {
                            val it: MutableIterator<*> = viewmodel.itemDiseaseCommentListDiffer.allList().iterator()
                            while (it.hasNext()) {
                                val n = it.next() as Any
                                if (n is Disease ) {
                                    if( data.id == n.id) {
                                        n.comment = ""
                                        n.dataSetting = ArrayList()

                                        if(item.diseaseSub.contains(n.id)) {
                                            item.diseaseSub.remove(n.id)
                                        }
                                        it.remove()

                                    }
                                }
                            }
                            viewmodel.itemDiseaseCommentListDiffer.adapter.notifyDataSetChanged()
                        }
                        viewmodel.itemDiseaseListDiffer.snapList().forEach {
                            if(it.isChecked) {
                                if( item.diseaseId?.isEmpty().nonnull()) {
                                    item.diseaseId = "${it.id}"
                                }else {
                                    item.diseaseId = item.diseaseId+ ",${it.id}"
                                }
                            }
                        }
                        sendEvent(hashCode())
                    }

                }
            }
        })

        viewmodel.itemDiseaseCommentListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is ItemDiseaseCommentViewHolder) {
                    val data = holder.item
                    if (data is Disease) {

                        var diseaseIdResult = ""
                        data.dataSetting.forEach {
                            diseaseIdResult = if(diseaseIdResult.isNotEmpty()) {
                                diseaseIdResult + ","+it.id.toString()
                            }else {
                                it.id.toString()
                            }
                        }

                        if(item.diseaseSub.contains(data.id)) {
                            item.diseaseSub.remove(data.id)
                            item.diseaseSub[data.id.nonnull()] = diseaseIdResult
                        }else {
                            item.diseaseSub[data.id.nonnull()] = diseaseIdResult
                        }
                    }

                }
            }
        })



        getDiseaseList()
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetDisease?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }


    fun getDiseaseList() {
        createMainScope().launch {
            useCase.getDiseaseList(true).success { data ->
                data?.content?.let { data ->
                    val items = mutableListOf<Any>()
                    this.let {
                        items.addAll(data)
                        data.forEach {
                            if(it._main == true) {
                                items.add(it)
                            }
                        }

                    }
                    viewmodel.itemDiseaseListDiffer.allList(items)
                }
            }.fail {
            }.execute()
        }
    }


}