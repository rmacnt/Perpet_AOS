package pet.perpet.equal.presentation.start.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pet.perpet.domain.UserStore
import pet.perpet.equal.support.push.FirebaseMessageCompat

class TokenCheckViewModel : ViewModel() {

    //======================================================================
    // Public Methods
    //======================================================================

    fun check(
        fail: (state: ErrorState) -> Unit,
        success: () -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                FirebaseMessageCompat.updateDeviceToken()
                getUserInfo()
                launch(Dispatchers.Main) {
                    success()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    if (e is StateException) {
                        fail(e.state)
                    } else {
                        fail(ErrorState.internal_exception)
                    }
                }
            }
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================


    @Throws(Exception::class)
    private fun getUserInfo(): ErrorState? {
        val sync = UserStore.remoteSync()
        val statusCode = sync?.first
        val user = sync?.second

        return when {
            statusCode == 400 -> {
                ErrorState.not_token_info
            }

            statusCode == 401 || user == null -> {
                ErrorState.not_user_info
            }

            else -> {
                null
            }
        }.run {
            if (this != null) {
                throw StateException(this)
            } else {
                null
            }
        }
    }
}

//======================================================================
// ErrorState
//======================================================================

enum class ErrorState {
    not_connetion,
    not_user_info,
    not_token_info,
    internal_exception,
    update_normal,
    update_force,
}

//======================================================================
// StateException
//======================================================================

class StateException(val state: ErrorState) : RuntimeException(state.toString())