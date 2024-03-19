package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemPetCardBinding
import pet.perpet.equal.presentation.more.viewmodel.MoreItemCardViewModel
import pet.perpet.framework.fragment.Fragment

class MoreItemCardFragment : Fragment<MoreItemCardViewModel>() {
    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: ItemPetCardBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_pet_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ItemPetCardBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}