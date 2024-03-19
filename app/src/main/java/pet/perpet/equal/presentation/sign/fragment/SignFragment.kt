package pet.perpet.equal.presentation.sign.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SignNewFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.sign.model.PetAllergyKnow
import pet.perpet.equal.presentation.sign.model.PetCatEar
import pet.perpet.equal.presentation.sign.model.PetDiseaseTherapy
import pet.perpet.equal.presentation.sign.model.PetLiving
import pet.perpet.equal.presentation.sign.model.PetLivingTogether
import pet.perpet.equal.presentation.sign.model.PetWalk
import pet.perpet.equal.presentation.sign.viewholder.PetAllergyKnowViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetAllergyViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetAppetiteViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetBcsViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetBirthViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetCatEarViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetDiseaseTherapyViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetDiseaseViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetEnergyViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetFeedViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetLivingTogetherViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetLivingViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetNameViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetNeuteringViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSelectViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSexViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSnackViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSubmitViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetTypeViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetWalkViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetWaterViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetWeightViewHolder
import pet.perpet.equal.presentation.sign.viewmodel.SignNewViewModel
import pet.perpet.framework.fragment.v2.AbstractRecyclerViewFragment
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SignFragment : AbstractRecyclerViewFragment<SignNewViewModel>() {


    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SignNewFragmentBinding

    override val recyclerView: RecyclerView
        get() = binding.recyclerview

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.sign_new_fragment, container, false)
    }

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = SignNewFragmentBinding.bind(view)
        binding.model = viewmodel

        viewmodel.items.add(viewmodel.petSelect)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onRecyclerViewHolderEvent(
        holder: BaseRecyclerViewHolder<*>,
        id: Int,
        bundle: Bundle,
    ) {
        super.onRecyclerViewHolderEvent(holder, id, bundle)
        binding.model = viewmodel
        when (holder) {
            is PetSelectViewHolder -> {

                if (viewmodel.items.contains(viewmodel.petName).not()) {
                    viewmodel.items.add(viewmodel.petName)
                }

                if (viewmodel.items.contains(viewmodel.petType)) {
                    viewmodel.petType.petSelect = viewmodel.petSelect.petSelect
                    viewmodel.petType.petTypeName = ""
                    viewmodel.petType.petBreedId = 0
                }

                if (viewmodel.items.contains(viewmodel.petCatEar)) {
                    if (viewmodel.petSelect.petSelect == "dog") {
                        viewmodel.petCatEar.petEar = ""
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetCatEar) {
                                it.remove()
                            }
                        }
                    }
                } else {
                    if (viewmodel.items.contains(viewmodel.petType)) {
                        viewmodel.petType.petSelect = viewmodel.petSelect.petSelect
                        viewmodel.petType.petTypeName = ""
                        viewmodel.petType.petBreedId = 0
                        if (viewmodel.items.contains(viewmodel.petCatEar).not()) {

                            if (viewmodel.items.size == 4) {

                                viewmodel.petCatEar.petName = viewmodel.petName.petName
                                viewmodel.items.add(viewmodel.petCatEar)
                                recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)

                            } else if (viewmodel.items.size > 5) {
                                viewmodel.petCatEar.petName = viewmodel.petName.petName
                                viewmodel.items.add(5, viewmodel.petCatEar)
                                recyclerView.smoothScrollToPosition(5)
                            }

                        }
                    }
                }

                if (viewmodel.items.contains(viewmodel.petBcs)) {
                    viewmodel.petBcs.petSelect = viewmodel.petSelect.petSelect
                    viewmodel.petBcs.petBcsAllView = false
                    viewmodel.petBcs.petBcsCode = -1
                }
                if (viewmodel.items.contains(viewmodel.petAllergy)) {

                    if (viewmodel.petAllergy.petAllegi == 1) {
                        if (viewmodel.petSelect.petSelect == "cat") {

                            if (viewmodel.items.contains(viewmodel.petLivingTogether).not()) {
                                viewmodel.items.add(viewmodel.petLivingTogether)
                                recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                            }
                        }
                    }
                }

                if (viewmodel.items.contains(viewmodel.petAllergyKnow)) {

                    if (viewmodel.petAllergyKnow.howToKnowAllergyCode != -1) {
                        if (viewmodel.petSelect.petSelect == "cat") {

                            if (viewmodel.items.contains(viewmodel.petLivingTogether).not()) {
                                viewmodel.items.add(viewmodel.petLivingTogether)
                                recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                            }
                        }
                    }
                }

                if (viewmodel.items.contains(viewmodel.petWalk)) {

                    if (viewmodel.petSelect.petSelect == "cat") {
                        viewmodel.petWalk.walkCode = -1
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetWalk) {
                                it.remove()
                            }
                        }

                        if (viewmodel.items.contains(viewmodel.petLivingTogether).not()) {
                            viewmodel.items.add(viewmodel.petLivingTogether)
                            recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                        }
                    }
                }

                if (viewmodel.items.contains(viewmodel.petLivingTogether)) {

                    if (viewmodel.petSelect.petSelect == "dog") {
                        viewmodel.petLivingTogether.relationshipCode = -1
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetLivingTogether) {
                                it.remove()
                            }
                        }

                        if (viewmodel.items.contains(viewmodel.petLiving).not()) {
                            viewmodel.items.add(viewmodel.petLiving)
                            recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                        }
                    }
                }

                if (viewmodel.items.contains(viewmodel.petLiving)) {
                    if (viewmodel.petSelect.petSelect == "cat") {
                        viewmodel.petLiving.mainActPlaceCode = -1
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetLiving) {
                                it.remove()
                            }
                        }
                    }
                }

                if (viewmodel.items.contains(viewmodel.petSubmit)) {
                    val it: MutableIterator<*> = viewmodel.items.iterator()
                    while (it.hasNext()) {
                        val n = it.next() as Any
                        if (n is String) {
                            it.remove()
                        }
                    }
                }

                notifySupportDataSetChanged()
            }

            is PetNameViewHolder -> {
                viewmodel.petBirth.petName = viewmodel.petName.petName
                viewmodel.petWeight.petName = viewmodel.petName.petName
                viewmodel.petType.petName = viewmodel.petName.petName
                viewmodel.petCatEar.petName = viewmodel.petName.petName
                viewmodel.petSex.petName = viewmodel.petName.petName
                viewmodel.petBcs.petName = viewmodel.petName.petName
                viewmodel.petFeed.petName = viewmodel.petName.petName
                if (viewmodel.items.contains(viewmodel.petBirth).not()) {
                    viewmodel.items.add(viewmodel.petBirth)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
                notifySupportDataSetChanged()
            }

            is PetBirthViewHolder -> {
                viewmodel.petWeight.petName = viewmodel.petName.petName
                if (viewmodel.items.contains(viewmodel.petWeight).not()) {
                    viewmodel.items.add(viewmodel.petWeight)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
                notifySupportDataSetChanged()
            }

            is PetWeightViewHolder -> {
                viewmodel.petType.petName = viewmodel.petName.petName
                viewmodel.petType.petSelect = viewmodel.petSelect.petSelect
                if (viewmodel.items.contains(viewmodel.petType).not()) {
                    viewmodel.items.add(viewmodel.petType)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
                notifySupportDataSetChanged()
            }

            is PetTypeViewHolder -> {
                if (viewmodel.petSelect.petSelect == "cat") {
                    viewmodel.petCatEar.petName = viewmodel.petName.petName
                    if (viewmodel.items.contains(viewmodel.petCatEar).not()) {
                        viewmodel.items.add(viewmodel.petCatEar)
                        recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                    }
                } else {
                    if (viewmodel.items.contains(viewmodel.petSex).not()) {
                        viewmodel.items.add(viewmodel.petSex)
                        recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                    }
                }

            }

            is PetCatEarViewHolder -> {
                viewmodel.petSex.petName = viewmodel.petName.petName
                if (viewmodel.items.contains(viewmodel.petSex).not()) {
                    viewmodel.items.add(viewmodel.petSex)
                    recyclerView.adapter?.notifyDataSetChanged()
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetSexViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petNeutering).not()) {
                    viewmodel.items.add(viewmodel.petNeutering)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetNeuteringViewHolder -> {
                viewmodel.petBcs.petName = viewmodel.petName.petName
                viewmodel.petBcs.petSelect = viewmodel.petSelect.petSelect
                if (viewmodel.items.contains(viewmodel.petBcs).not()) {
                    viewmodel.items.add(viewmodel.petBcs)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetBcsViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petDisease).not()) {
                    viewmodel.items.add(viewmodel.petDisease)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetDiseaseViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petDiseaseTherapy).not()) {
                    if (viewmodel.petDisease.petDisease == 0) {
                        if (viewmodel.petDisease.diseaseId != null && viewmodel.petDisease.diseaseId?.isEmpty() == false) {
                            viewmodel.items.add(
                                viewmodel.items.indexOf(viewmodel.petDisease) + 1,
                                viewmodel.petDiseaseTherapy
                            )
                            recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petDisease) + 1)
                        }
                    } else {
                        if (viewmodel.items.contains(viewmodel.petEnergy).not()) {
                            viewmodel.items.add(
                                viewmodel.items.indexOf(viewmodel.petDisease) + 1,
                                viewmodel.petEnergy
                            )
                            recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petDisease) + 1)
                        }
                    }
                } else {
                    if (viewmodel.petDisease.petDisease == 1) {
                        viewmodel.petDiseaseTherapy.diseaseTreatCode = -1
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetDiseaseTherapy) {
                                it.remove()
                            }
                        }

                        if (viewmodel.items.contains(viewmodel.petEnergy).not()) {
                            viewmodel.items.add(
                                viewmodel.items.indexOf(viewmodel.petDisease) + 1,
                                viewmodel.petEnergy
                            )
                            recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                        }

                    }

                    if (viewmodel.petDisease.petDisease == 0 && viewmodel.petDisease.diseaseId?.isEmpty() == true) {
                        viewmodel.petDiseaseTherapy.diseaseTreatCode = -1
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetDiseaseTherapy) {
                                it.remove()
                            }
                        }
                    }
                    notifySupportDataSetChanged()
                }
            }

            is PetDiseaseTherapyViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petEnergy).not()) {
                    viewmodel.items.add(viewmodel.petEnergy)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetEnergyViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petAppetite).not()) {
                    viewmodel.items.add(viewmodel.petAppetite)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetAppetiteViewHolder -> {
                viewmodel.petFeed.petName = viewmodel.petName.petName
                if (viewmodel.items.contains(viewmodel.petFeed).not()) {
                    viewmodel.items.add(viewmodel.petFeed)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }

            }

            is PetFeedViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petSnack).not()) {
                    viewmodel.items.add(viewmodel.petSnack)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetSnackViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petWater).not()) {
                    viewmodel.items.add(viewmodel.petWater)
                    recyclerView.adapter?.notifyDataSetChanged()
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetWaterViewHolder -> {

                if (viewmodel.items.contains(viewmodel.petAllergy).not()) {
                    viewmodel.items.add(viewmodel.petAllergy)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetAllergyViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petAllergyKnow).not()) {

                    if (viewmodel.petAllergy.petAllegi == 0) {
                        if (viewmodel.petAllergy.allergyId?.isEmpty() == false) {
                            viewmodel.items.add(
                                viewmodel.items.indexOf(viewmodel.petAllergy) + 1,
                                viewmodel.petAllergyKnow
                            )
                            recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petAllergy) + 1)
                        }
                    } else {
                        if (viewmodel.petSelect.petSelect == "dog") {
                            if (viewmodel.items.contains(viewmodel.petWalk).not()) {
                                viewmodel.items.add(viewmodel.petWalk)
                                recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                            }
                        } else {
                            if (viewmodel.items.contains(viewmodel.petLivingTogether).not()) {
                                viewmodel.items.add(viewmodel.petLivingTogether)
                                recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                            }
                        }
                    }

                    notifySupportDataSetChanged()
                } else {
                    if (viewmodel.petAllergy.petAllegi == 1) {
                        viewmodel.petAllergyKnow.howToKnowAllergyCode = -1
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetAllergyKnow) {
                                it.remove()
                            }
                        }

                        if (viewmodel.petSelect.petSelect == "dog") {
                            if (viewmodel.items.contains(viewmodel.petWalk).not()) {
                                viewmodel.items.add(viewmodel.petWalk)
                                recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                            }
                        } else {
                            if (viewmodel.items.contains(viewmodel.petLivingTogether).not()) {
                                viewmodel.items.add(viewmodel.petLivingTogether)
                                recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                            }
                        }

                    }

                    if (viewmodel.petAllergy.petAllegi == 0 && viewmodel.petAllergy.allergyId?.isEmpty() == true) {
                        viewmodel.petAllergyKnow.howToKnowAllergyCode = -1
                        val it: MutableIterator<*> = viewmodel.items.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n is PetAllergyKnow) {
                                it.remove()
                            }
                        }
                    }

                    notifySupportDataSetChanged()
                }
            }

            is PetAllergyKnowViewHolder -> {
                if (viewmodel.petSelect.petSelect == "dog") {
                    if (viewmodel.items.contains(viewmodel.petWalk).not()) {
                        viewmodel.items.add(viewmodel.petWalk)
                        recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                    }
                } else {
                    if (viewmodel.items.contains(viewmodel.petLivingTogether).not()) {
                        viewmodel.items.add(viewmodel.petLivingTogether)
                        recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                    }
                }

            }

            is PetWalkViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petLiving).not()) {
                    viewmodel.items.add(viewmodel.petLiving)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetLivingTogetherViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petSubmit).not()) {
                    viewmodel.items.add(viewmodel.petSubmit)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetLivingViewHolder -> {
                if (viewmodel.items.contains(viewmodel.petSubmit).not()) {
                    viewmodel.items.add(viewmodel.petSubmit)
                    recyclerView.smoothScrollToPosition(viewmodel.items.size - 1)
                }
            }

            is PetSubmitViewHolder -> {
                viewmodel.onSubmitClick {
                    notifySupportDataSetChanged()
                    when(it) {
                        1 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petName))
                        2 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petWeight))
                        3 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petType))
                        4 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petCatEar))
                        5 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petNeutering))
                        6 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petBcs))
                        7 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petDisease))
                        8 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petDiseaseTherapy))
                        9 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petEnergy))
                        10 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petAppetite))
                        11 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petFeed))
                        12 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petWater))
                        13 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petAllergy))
                        14 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petAllergyKnow))
                        15 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petLivingTogether))
                        16 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petWalk))
                        17 -> recyclerView.smoothScrollToPosition(viewmodel.items.indexOf(viewmodel.petLiving))
                    }
                }
            }

        }
    }

    override fun onBackPressed(): Boolean {
        viewmodel.onBackClick(null)
        return false
    }

}