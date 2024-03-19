package pet.perpet.equal.presentation.home.viewmodel

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.main.FeedSetUseCase
import pet.perpet.equal.presentation.home.fragment.BottomFeedFragmentArgs
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class BottomFeedViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val args by lazy {
        BottomFeedFragmentArgs.fromBundle(arguments)
    }

    val comment: MutableLiveData<String> = MutableLiveData("")


    //======================================================================
    // Public Methods
    //======================================================================

    fun onFeedTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        comment.value = text.toString()
        executeViewBinding()
    }

    fun onNegativeClick(view: View) {
        if(comment.value.toString().isNotEmpty()) {
            viewModelScope.launch {
                FeedSetUseCase().parameter2 {
                    this.targetId = args.targetId?.toLong().nonnull()
                    this.contents = comment.value.toString()
                }.success {
                    Toast.makeText(view.context, "피드백이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    dismissToResult()
                }.fail {
                    dismissToResult()
                }.execute()
            }
        }else {
            Toast.makeText(view.context, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }


    }

    fun dismissToResult() {
        if (fragment is BottomSheetDialogFragment<*>) {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
    }


}