package pet.perpet.equal.support.push

import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import pet.perpet.data.nonnull
import pet.perpet.domain.TokenStore
import pet.perpet.domain.usecase.account.AccountPushTokenUseCase
import pet.perpet.equal.presentation.getContext

object FirebaseMessageCompat {


    //======================================================================
    // Public Methods
    //======================================================================

    @JvmStatic
    fun updateDeviceToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            val token = it?.token
            TokenStore.syncDeviceToken(token.orEmpty())
        }
    }
}