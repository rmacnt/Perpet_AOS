package pet.perpet.equal.presentation.more.viewmodel.payment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.main.MainUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.goHome
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseListAdapter.Companion.isValid
import java.util.regex.Matcher
import java.util.regex.Pattern


class PaymentInsertViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val useCase = MainUseCase()

    val cardPass: MutableLiveData<String> = MutableLiveData("")
    val cardYear: MutableLiveData<String> = MutableLiveData("")
    val cardNumber: MutableLiveData<String> = MutableLiveData("")
    val birth: MutableLiveData<String> = MutableLiveData("")

    val cardPassBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    val cardYearBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    val cardNumberBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    val birthBoolean: MutableLiveData<Boolean> = MutableLiveData(false)

    val cardPassText: String?
        get() = cardPass.value

    val cardYearText: String?
        get() = cardYear.value

    val cardNumberText: String?
        get() = cardNumber.value

    val birthText: String?
        get() = birth.value

    val cardPassBooleanResult: Boolean?
        get() = cardPassBoolean.value

    val cardYearResult: Boolean?
        get() = cardYearBoolean.value

    val cardNumberResult: Boolean?
        get() = cardNumberBoolean.value

    val birthResult: Boolean?
        get() = birthBoolean.value

    private var startNetwork = false

    //======================================================================
    // Public Methods
    //======================================================================

    fun onHomeClick(view: View) {
        context?.let {
            it.goHome()
        }

    }

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onCardPassTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        cardPassBoolean.postValue(false)
        cardPass.value = text.toString()
        executeViewBinding()
    }

    fun onCardYearTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        cardYearBoolean.postValue(false)
        cardYear.value = text.toString()
        executeViewBinding()
    }

    fun onCardNumberTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        cardNumberBoolean.postValue(false)
        cardNumber.value = text.toString()
        executeViewBinding()
    }

    fun onBirthTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        birthBoolean.postValue(false)
        birth.value = text.toString()
        executeViewBinding()
    }

    fun onSubmitClick(view: View) {

        if (getInfoCheck() && startNetwork.not()) {
            startNetwork = true
            viewModelScope.launch {
                val cardYear = "20${cardYearText?.substring(3)}-${cardYearText?.substring(0, 2)}"
                useCase.setCardInsert(
                    cardNumberText.nonnull(),
                    cardYear,
                    cardPassText.nonnull(),
                    birthText.nonnull()
                ).success {
                    startNetwork = false
                    it?.let {
                        if(it is DefaultResponse) {
                            if( it.success ) {
                                activity?.finish()
                            } else {
                                activity?.let {
                                    AppAlertDialog(
                                        it,
                                        getString(R.string.dialog_title),
                                        getString(R.string.payment_comment27),
                                        getString(R.string.app_dialog_action_confirm)

                                    ).show(
                                        onClickPositive = {
                                            it.dismiss()
                                        }

                                    )
                                }
                            }
                        }
                    }
                }.fail {
                    startNetwork = false
                }.execute()
            }
        } else {
            if (cardPassText?.length.nonnull() != 2) {
                cardPassBoolean.postValue(true)
            }

            if (cardYearText?.length.nonnull().isValid().not()) {
                cardYearBoolean.postValue(true)
            } else {
                cardYearText?.let {
                    if (it.isNotEmpty()) {
                        val cardYear = Pattern.compile("^([0|1])?([0-9])/?([0-9]{2})\$")
                        val matcherYear: Matcher = cardYear.matcher(it)
                        if (matcherYear.matches().not()) {
                            cardYearBoolean.postValue(true)
                        }
                    }
                }
            }

            if (cardNumberText?.length.nonnull().isValid().not()) {
                cardNumberBoolean.postValue(true)
            } else {
                cardNumberText?.let {
                    if (it.isNotEmpty()) {
                        val cardNumber =
                            Pattern.compile("^([0-9]{4})-?([0-9]{4})-?([0-9]{4})-?([0-9]{4})\$")
                        val matcher: Matcher = cardNumber.matcher(it)
                        if (matcher.matches().not()) {
                            cardNumberBoolean.postValue(true)
                        }
                    }
                }
            }

            if (birthText?.length.nonnull() != 6) {
                birthBoolean.postValue(true)
            } else {
                if (birthText?.length == 6) {
                    val birth = Pattern.compile("^([0-9]{2})?([0|1])?([0-9])?([0|1|2|3])?([0-9])\$")
                    val matcher: Matcher = birth.matcher(birthText)
                    if (matcher.matches().not()) {
                        cardNumberBoolean.postValue(true)
                    }
                }
            }

            executeViewBinding()
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================
    private fun getInfoCheck(): Boolean {
        if (cardPassText?.length.nonnull() != 2) {
            cardPassBoolean.postValue(true)
            return false
        }
        if (cardYearText?.length.nonnull().isValid().not()) {
            cardYearBoolean.postValue(true)
            return false
        } else {
            cardYearText?.let {
                if (it.isNotEmpty()) {
                    val cardYear = Pattern.compile("^([0|1])?([0-9])/?([0-9]{2})\$")
                    val matcherYear: Matcher = cardYear.matcher(it)
                    if (matcherYear.matches().not()) {
                        cardYearBoolean.postValue(true)
                        return false
                    }
                }
            }
        }

        if (cardNumberText?.length.nonnull().isValid().not()) {
            cardNumberBoolean.postValue(true)
            return false
        } else {
            cardNumberText?.let {
                if (it.isNotEmpty()) {
                    val cardNumber =
                        Pattern.compile("^([0-9]{4})-?([0-9]{4})-?([0-9]{4})-?([0-9]{4})\$")
                    val matcher: Matcher = cardNumber.matcher(it)
                    if (matcher.matches().not()) {
                        cardNumberBoolean.postValue(true)
                        return false
                    }
                }
            }
        }

        if (birthText?.length.nonnull() != 6) {
            birthBoolean.postValue(true)
            return false
        } else {
            if (birthText?.length == 6) {
                val birth = Pattern.compile("^([0-9]{2})?([0|1])?([0-9])?([0|1|2|3])?([0-9])\$")
                val matcher: Matcher = birth.matcher(birthText)
                if (matcher.matches().not()) {
                    birthBoolean.postValue(true)
                    return false
                }
            }
        }

        return true
    }
}