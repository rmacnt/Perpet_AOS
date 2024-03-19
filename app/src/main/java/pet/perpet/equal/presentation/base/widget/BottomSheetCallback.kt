package pet.perpet.equal.presentation.base.widget

import android.view.View

interface BottomSheetCallback {
    fun onStateChanged(bottomSheet: View, newState: Int)
    fun onSlide(bottomSheet: View, slideOffset: Float)
}