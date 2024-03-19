package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemPetCardBinding
import pet.perpet.equal.databinding.ItemPetInsertBinding
import pet.perpet.equal.presentation.more.viewmodel.MoreItemCardInsertViewModel
import pet.perpet.framework.fragment.Fragment

class MoreItemCardInsertFragment : Fragment<MoreItemCardInsertViewModel>() {
    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: ItemPetInsertBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_pet_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ItemPetInsertBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}