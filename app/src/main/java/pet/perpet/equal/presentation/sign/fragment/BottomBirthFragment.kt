package pet.perpet.equal.presentation.sign.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomBirthFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.sign.viewmodel.BottomBirthViewModel
import pet.perpet.equal.support.widget.picker.DatePickerView
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Date


class BottomBirthFragment : BottomSheetDialogFragment<BottomBirthViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomBirthFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
        setStyle(
            androidx.fragment.app.DialogFragment.STYLE_NORMAL,
            R.style.DialogTheme_Trans_NotDragging
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bottom_birth_fragment, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomBirthFragmentBinding.bind(view)
        binding.model = viewmodel

        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetBehavior = bottomSheetDialog.behavior
        bottomSheetBehavior.isDraggable = false

        binding.dayTimePickerView.setDate(
            viewmodel.args.year?.toInt().nonnull(),
            viewmodel.args.month?.toInt().nonnull(),
            1
        )

        val currentTime: Date = Calendar.getInstance().time
        binding.dayTimePickerView.maxDate = currentTime
        binding.dayTimePickerView.setWheelListener(object : DatePickerView.Listener {
            override fun didSelectData(year: Int, month: Int, day: Int) {
                viewmodel.year = year
                viewmodel.month = month+1
                binding.model = viewmodel
            }

            override fun scroll(state: Int?) {
                when (state) {
                    MotionEvent.ACTION_MOVE,
                    MotionEvent.ACTION_DOWN -> {
                        if( bottomSheetBehavior.isDraggable) {
                            bottomSheetBehavior.isDraggable = false
                        }
                    }
                    MotionEvent.ACTION_UP ->{
                        bottomSheetBehavior.isDraggable = true
                    }
                }
            }
        })
        binding.dayTimePickerView.mode = DatePickerView.Mode.YEAR_MONTH
        super.onViewCreated(view, savedInstanceState)

    }
}