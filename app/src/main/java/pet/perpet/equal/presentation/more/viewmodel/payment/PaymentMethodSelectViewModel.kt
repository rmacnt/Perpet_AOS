package pet.perpet.equal.presentation.more.viewmodel.payment

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.equal.presentation.goPaymentInsert
import pet.perpet.equal.support.navigation.showPaymentPolicy
import pet.perpet.framework.fragment.UseViewModel

class PaymentMethodSelectViewModel (fragment: Fragment) : UseViewModel(fragment) {


    //======================================================================
    // Public Methods
    //======================================================================


    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onPaymentTermClick(view: View) {
        activity?.showPaymentPolicy {result->
            if(result) {
                activity?.let {
                    it.goPaymentInsert()
                }
            }
        }
    }


}