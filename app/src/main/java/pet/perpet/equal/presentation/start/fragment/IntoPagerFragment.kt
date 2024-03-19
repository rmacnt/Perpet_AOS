package pet.perpet.equal.presentation.start.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomPolicyInfoBinding
import pet.perpet.equal.databinding.IntroFragmentBinding
import pet.perpet.equal.presentation.start.viewmodel.IntroViewModel
import pet.perpet.framework.fragment.Fragment

class IntoPagerFragment : Fragment<IntroViewModel>() {
    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: IntroFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.intro_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = IntroFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}