package pet.perpet.equal.presentation.intakecheck.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.IntakeCheckAlarmListFragmentBinding
import pet.perpet.equal.presentation.intakecheck.viewmodel.IntakeCheckAlarmListViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class IntakeCheckAlarmListFragment : Fragment<IntakeCheckAlarmListViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: IntakeCheckAlarmListFragmentBinding

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
        return inflater.inflate(R.layout.intake_check_alarm_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = IntakeCheckAlarmListFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}