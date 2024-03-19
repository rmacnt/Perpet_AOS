package pet.perpet.equal.presentation.health.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.HealthStartResultPagerFragmentBinding
import pet.perpet.equal.presentation.health.viewmodel.HealthStartResultPagerViewModel
import pet.perpet.framework.fragment.Fragment

class HealthStartResultPagerFragment : Fragment<HealthStartResultPagerViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: HealthStartResultPagerFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.health_start_result_pager_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = HealthStartResultPagerFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}