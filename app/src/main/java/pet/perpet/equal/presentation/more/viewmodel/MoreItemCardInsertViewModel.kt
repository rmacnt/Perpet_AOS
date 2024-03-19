package pet.perpet.equal.presentation.more.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.goPagerWeight
import pet.perpet.framework.fragment.UseViewModel

class MoreItemCardInsertViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Public Methods
    //======================================================================

    fun onInsertClick(view: View) {

        getPetList {
            if (it > 9) {
                AppAlertDialog(
                    view.context,
                    getString(R.string.dialog_title),
                    getString(R.string.more_comment28),
                    getString(R.string.app_dialog_action_confirm)

                ).show(
                    onClickPositive = {
                        it.dismiss()
                    }
                )
            } else {
                context?.let {
                    it.goPagerWeight()
                }
            }
        }
    }

    private fun getPetList(result: (size: Int) -> Unit) {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                it.content?.let { data ->
                    result(data.size)
                }
            }
        }.fail {
        }.execute()
    }

}