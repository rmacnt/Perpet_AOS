package pet.perpet.equal.presentation.sign.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomBreedSearchFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.sign.viewmodel.BottomBreedSearchViewModel
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.widget.recyclerview.AdapterUtil

class BottomBreedSearchFragment : BottomSheetDialogFragment<BottomBreedSearchViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomBreedSearchFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel.observeBindingNotify {
            binding.model = viewmodel
            viewmodel.getPetType()
        }
        setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.DialogTheme_Trans)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bottom_breed_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomBreedSearchFragmentBinding.bind(view)
        binding.model = viewmodel

        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetBehavior = bottomSheetDialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        viewmodel.getPetType()
        val b = binding
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)


        val recyclerView = b.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        b.recyclerview.adapter = viewmodel.adapter
        AdapterUtil.notifySupportDataSetChanged(viewmodel.adapter, true)

        super.onViewCreated(view, savedInstanceState)

    }
}