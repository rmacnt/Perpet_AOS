package pet.perpet.equal.presentation.examination.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomCommentCodeFragmentBinding
import pet.perpet.equal.presentation.examination.viewmodel.BottomCodeCommentViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.BottomSheetDialogFragment

class BottomCodeCommentFragment : BottomSheetDialogFragment<BottomCodeCommentViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomCommentCodeFragmentBinding

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
        return inflater.inflate(R.layout.bottom_comment_code_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomCommentCodeFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }
}