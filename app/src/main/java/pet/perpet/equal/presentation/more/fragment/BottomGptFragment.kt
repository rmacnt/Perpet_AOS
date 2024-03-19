package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomGptFragmentBinding
import pet.perpet.equal.presentation.more.viewmodel.BottomGptViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.BottomSheetDialogFragment

class BottomGptFragment : BottomSheetDialogFragment<BottomGptViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomGptFragmentBinding


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }

        setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.DialogTheme_Trans)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_gpt_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomGptFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }
}