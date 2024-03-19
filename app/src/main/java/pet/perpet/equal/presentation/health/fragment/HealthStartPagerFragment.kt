package pet.perpet.equal.presentation.health.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.HealthStartPagerFragmentBinding
import pet.perpet.equal.presentation.health.viewmodel.HealthStartPagerViewModel
import pet.perpet.framework.fragment.Fragment

class HealthStartPagerFragment : Fragment<HealthStartPagerViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: HealthStartPagerFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.health_start_pager_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = HealthStartPagerFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}