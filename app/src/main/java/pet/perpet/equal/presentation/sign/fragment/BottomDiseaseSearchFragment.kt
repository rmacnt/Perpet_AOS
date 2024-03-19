package pet.perpet.equal.presentation.sign.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomDiseaseSearchFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.observeBindingNotifyNew
import pet.perpet.equal.presentation.sign.viewmodel.BottomDiseaseSearchViewModel
import pet.perpet.framework.fragment.BottomSheetDialogFragment

class BottomDiseaseSearchFragment : BottomSheetDialogFragment<BottomDiseaseSearchViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomDiseaseSearchFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
        viewmodel.observeBindingNotifyNew {
            viewmodel.getDiseaseSearch()
        }

        setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.DialogTheme_Trans)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bottom_disease_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomDiseaseSearchFragmentBinding.bind(view)
        binding.model = viewmodel

        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetBehavior = bottomSheetDialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        super.onViewCreated(view, savedInstanceState)

    }
}