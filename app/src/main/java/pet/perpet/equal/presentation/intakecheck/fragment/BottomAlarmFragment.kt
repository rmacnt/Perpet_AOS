package pet.perpet.equal.presentation.intakecheck.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomAlarmFragmentBinding
import pet.perpet.equal.databinding.BottomHealthFragmentBinding
import pet.perpet.equal.presentation.intakecheck.viewmodel.BottomAlarmViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.support.widget.picker.DatePickerView
import pet.perpet.equal.support.widget.picker.TimePickerView
import pet.perpet.framework.fragment.BottomSheetDialogFragment

class BottomAlarmFragment : BottomSheetDialogFragment<BottomAlarmViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomAlarmFragmentBinding

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
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bottom_alarm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomAlarmFragmentBinding.bind(view)
        binding.model = viewmodel

        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetBehavior = bottomSheetDialog.behavior
        bottomSheetBehavior.isDraggable = false
        viewmodel.args.item?.let {
            binding.dayTimePickerView.setDate(it.type.nonnull() , it.time.nonnull()-1)
        }

        binding.dayTimePickerView.setWheelListener(object : TimePickerView.Listener {

            override fun didSelectData(type: Int, time: Int) {
                viewmodel.type = type
                viewmodel.time = time
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

        super.onViewCreated(view, savedInstanceState)

    }
}