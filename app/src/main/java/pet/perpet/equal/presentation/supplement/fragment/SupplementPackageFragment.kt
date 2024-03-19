package pet.perpet.equal.presentation.supplement.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SupplementPackageDetailFragmentBinding
import pet.perpet.equal.databinding.SupplementPackageFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.supplement.viewmodel.SupplementPackageDetailViewModel
import pet.perpet.equal.presentation.supplement.viewmodel.SupplementPackageViewModel
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.fragment.OnTabSelectedBehavior

class SupplementPackageFragment : Fragment<SupplementPackageViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SupplementPackageFragmentBinding

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
        return inflater.inflate(R.layout.supplement_package_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SupplementPackageFragmentBinding.bind(view)
        binding.model = viewmodel

        super.onViewCreated(view, savedInstanceState)

    }

}