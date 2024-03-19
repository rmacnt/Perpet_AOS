package pet.perpet.equal.presentation.paperweight.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.PagerWeightIntroFragmentBinding
import pet.perpet.equal.presentation.paperweight.viewmodel.PagerWeightIntroViewModel
import pet.perpet.framework.fragment.Fragment

class PagerWeightIntroFragment : Fragment<PagerWeightIntroViewModel>() {
    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: PagerWeightIntroFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pager_weight_intro_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = PagerWeightIntroFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}