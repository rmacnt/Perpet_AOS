package pet.perpet.equal.presentation.health.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.HealthInfoPagerFragmentBinding
import pet.perpet.equal.presentation.health.viewmodel.HealthInfoPagerViewModel
import pet.perpet.framework.fragment.Fragment

class HealthInfoPagerFragment : Fragment<HealthInfoPagerViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: HealthInfoPagerFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.health_info_pager_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = HealthInfoPagerFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}