package pet.perpet.equal.presentation.supplement.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SupplementPackageDetailCodeFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.supplement.viewmodel.SupplementPackageDetailCodeViewModel
import pet.perpet.framework.fragment.Fragment

class SupplementPackageDetailCodeFragment : Fragment<SupplementPackageDetailCodeViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SupplementPackageDetailCodeFragmentBinding

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
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.supplement_package_detail_code_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SupplementPackageDetailCodeFragmentBinding.bind(view)
        binding.model = viewmodel

        super.onViewCreated(view, savedInstanceState)

    }

}