package pet.perpet.equal.presentation.examination.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomCommentFragmentBinding
import pet.perpet.equal.presentation.examination.viewmodel.BottomCommentViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.BottomSheetDialogFragment

class BottomCommentFragment : BottomSheetDialogFragment<BottomCommentViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomCommentFragmentBinding

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
        return inflater.inflate(R.layout.bottom_comment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomCommentFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }
}