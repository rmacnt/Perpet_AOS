package pet.perpet.equal.presentation.examination.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ExaminationHealthResultDetailFragmentBinding
import pet.perpet.equal.presentation.examination.viewmodel.ExaminationHealthResultDetailViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class ExaminationHealthResultDetailFragment : Fragment<ExaminationHealthResultDetailViewModel>() {

    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: ExaminationHealthResultDetailFragmentBinding

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
        return inflater.inflate(R.layout.examination_health_result_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = ExaminationHealthResultDetailFragmentBinding.bind(view)
        binding.model = viewmodel

        super.onViewCreated(view, savedInstanceState)
    }
}