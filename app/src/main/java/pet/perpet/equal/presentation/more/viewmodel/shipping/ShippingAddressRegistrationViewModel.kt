package pet.perpet.equal.presentation.more.viewmodel.shipping

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.main.MainUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.more.fragment.shipping.ShippingAddressRegistrationFragmentArgs
import pet.perpet.equal.support.navigation.showAddressSearch
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseListAdapter.Companion.isValid

class ShippingAddressRegistrationViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        ShippingAddressRegistrationFragmentArgs.fromBundle(arguments)
    }

    val useCase = MainUseCase()

    var result: Boolean = false

    val zipNo: MutableLiveData<String> = MutableLiveData("")
    val zipAddress: MutableLiveData<String> = MutableLiveData("")

    val addressName: MutableLiveData<String> = MutableLiveData("")
    val phone: MutableLiveData<String> = MutableLiveData("")
    val addressDetail: MutableLiveData<String> = MutableLiveData("")
    val submitTitle: MutableLiveData<String> = MutableLiveData(getString(R.string.submit_1))
    val addressMessage: MutableLiveData<String> = MutableLiveData("")
    val title: MutableLiveData<String> = MutableLiveData(getString(R.string.shipping_comment6))

    val zipAddressBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    val addressNameBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    val phoneBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    val addressDetailBoolean: MutableLiveData<Boolean> = MutableLiveData(false)


    val titleText: String?
        get() = title.value

    val submitTitleText: String?
        get() = submitTitle.value

    val zipNoText: String?
        get() = zipNo.value

    val zipAddressText: String?
        get() = zipAddress.value

    val addressNameText: String?
        get() = addressName.value

    val phoneText: String?
        get() = phone.value

    val addressDetailText: String?
        get() = addressDetail.value

    val addressMessageText: String?
        get() = addressMessage.value

    val zipAddressBooleanResult: Boolean?
        get() = zipAddressBoolean.value

    val addressNameBooleanResult: Boolean?
        get() = addressNameBoolean.value

    val phoneBooleanResult: Boolean?
        get() = phoneBoolean.value

    val addressDetailBooleanResult: Boolean?
        get() = addressDetailBoolean.value

    private var startNetwork = false

    init {
        args.address?.let {
            zipNo.postValue(args.address?.zipcode)
            zipAddress.postValue(args.address?.address)
            addressDetail.postValue(args.address?.address_detail)
            phone.postValue(args.address?.phone)
            addressName.postValue(args.address?.recipient)
            addressMessage.postValue(args.address?.memo)
            submitTitle.postValue(getString(R.string.edit_1))
            title.postValue(getString(R.string.shipping_comment28))
        }

    }
    //======================================================================
    // Public Methods
    //======================================================================

    fun onAddressNameTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {

        addressNameBoolean.postValue(false)
        addressName.value = text.toString()
        executeViewBinding()
    }

    fun onPhoneTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {

        phone.value = text.toString()
        phoneBoolean.postValue(false)
        executeViewBinding()
    }


    fun onAddressDetailTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {

        addressDetailBoolean.postValue(false)
        addressDetail.value = text.toString()
        executeViewBinding()
    }

    fun onAddressMessageTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {

        addressMessage.value = text.toString()
        executeViewBinding()
    }


    fun onSubmitClick(view: View) {

        args.address?.let {
            if (getInfoCheck() && startNetwork.not()) {
                viewModelScope.launch {
                    useCase.editAddress(
                        args.address?.id.nonnull(),
                        addressNameText.nonnull(),
                        phoneText.nonnull(),
                        zipNoText.nonnull(),
                        zipAddressText.nonnull(),
                        addressDetailText.nonnull(),
                        addressMessageText.nonnull()
                    ).success {
                        startNetwork = false
                        activity?.let {
                            it.finish()
                        }
                    }.fail {
                        startNetwork = false
                    }.execute()
                }
            } else {
                if (zipAddressText?.length.nonnull().isValid().not()) {
                    zipAddressBoolean.postValue(true)
                }
                if (addressNameText?.length.nonnull().isValid().not()) {
                    addressNameBoolean.postValue(true)
                }
                if (phoneText?.length.nonnull().isValid().not()) {
                    phoneBoolean.postValue(true)
                }
                if (addressDetailText?.length.nonnull().isValid().not()) {
                    addressDetailBoolean.postValue(true)
                }

                executeViewBinding()
            }
        } ?: run {
            if (getInfoCheck() && startNetwork.not()) {

                viewModelScope.launch {
                    useCase.setAddressInsert(
                        addressNameText.nonnull(),
                        phoneText.nonnull(),
                        zipNoText.nonnull(),
                        zipAddressText.nonnull(),
                        addressDetailText.nonnull(),
                        addressMessageText.nonnull()
                    ).success {
                        startNetwork = false
                        activity?.let {
                            it.finish()
                        }
                    }.fail {
                        startNetwork = false
                    }.execute()
                }
            } else {
                if (zipAddressText?.length.nonnull().isValid().not()) {
                    zipAddressBoolean.postValue(true)
                }
                if (addressNameText?.length.nonnull().isValid().not()) {
                    addressNameBoolean.postValue(true)
                }
                if (phoneText?.length.nonnull().isValid().not()) {
                    phoneBoolean.postValue(true)
                }
                if (addressDetailText?.length.nonnull().isValid().not()) {
                    addressDetailBoolean.postValue(true)
                }

                executeViewBinding()
            }
        }



    }

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onAddressClick(view: View) {

        activity?.showAddressSearch {
            zipAddressBoolean.postValue(false)
            zipNo.postValue(it.zipNo)
            zipAddress.postValue(it.roadAddr)
            executeViewBinding()
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================
    private fun getInfoCheck(): Boolean {
        if (zipAddressText?.length.nonnull().isValid().not()) {
            zipAddressBoolean.postValue(true)
            return false
        }
        if (addressNameText?.length.nonnull().isValid().not()) {
            addressNameBoolean.postValue(true)
            return false
        }
        if (phoneText?.length.nonnull().isValid().not()) {
            phoneBoolean.postValue(true)
            return false
        }
        if (addressDetailText?.length.nonnull().isValid().not()) {
            addressDetailBoolean.postValue(true)
            return false
        }

        return true
    }

}