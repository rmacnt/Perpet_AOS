package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.launch
import pet.perpet.domain.usecase.profile.SignUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.sign.differ.DiseaseItemListDiffer
import pet.perpet.equal.presentation.sign.differ.TypeItemListDiffer
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class SignViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val useCase = SignUseCase()

    var petAllegi: Int = -1
    var petSelect: Int = -1
    var petSex: Int = -1
    var petChild: Int = -1
    var petDisease: Int = -1
    var petActive: Int = -1
    var petFeeding: Int = -1
    var petOther: Int = -1
    var petEat: Int = -1
    var petBcs: Int = -1
    var petLiving: Int = -1
    var petLiving2: Int = -1
    var petBcsAllView: Boolean = false
    var petSexAsk: Boolean = true
    var petChildAsk: Boolean = true
    var petOtherAsk: Boolean = true
    var petLivingAsk: Boolean = true
    var petLiving2Ask: Boolean = true
    var petEatAsk: Boolean = true
    var petActiveAsk: Boolean = true
    var petBcsAsk: Boolean = true
    var petFeedingAsk: Boolean = true
    var petBirthAsk: Boolean = true
    var petWeightAsk: Boolean = true
    var petDiseaseAsk: Boolean = true
    var petAllegiAsk: Boolean = true
    var feeding: Boolean = petSex == 2 && petChild == 1
    var birth: Boolean =
        if (petSex == 1) {
            true
        } else {
            petChild != -1
        }

    var petBitrhType: Boolean = false
    var petNameType: Boolean = false
    var petSexType: Boolean = false
    var petweightType: Boolean = false
    val petName: MutableLiveData<String> = MutableLiveData()
    val petNameTypeText: MutableLiveData<String> = MutableLiveData()
    val petBirth: MutableLiveData<String> = MutableLiveData()
    val petWeight: MutableLiveData<String> = MutableLiveData()
    var petNameBreedId: Int = -1

    var title4: String = String.format(getString(R.string.sign_comment_4), petName.value)
    var title6: String = String.format(getString(R.string.sign_comment_6), petName.value)
    var title13: String = String.format(getString(R.string.sign_comment_13), petName.value)
    var title16: String = String.format(getString(R.string.sign_comment_16), petName.value)
    var title18: String = String.format(getString(R.string.sign_comment_18), petName.value)
    var title34: String = String.format(getString(R.string.sign_comment_34), petName.value)


    var petTypeSelect: Boolean = false

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val itemListDiffer by lazy {
        TypeItemListDiffer()
    }
    val itemDiseaseListDiffer by lazy {
        DiseaseItemListDiffer()
    }


    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return FlexboxLayoutManager(context).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                    alignItems = AlignItems.CENTER
                }
            }
        }
    }

    val diseaseMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemDiseaseListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return FlexboxLayoutManager(context).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                    alignItems = AlignItems.CENTER
                }
            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onTypeClick(view: View?) {
        petSelect = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onSexClick(view: View?) {
        petSex = view?.tag.toString().toInt()
        birth = true
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onBcsClick(view: View?) {
        petBcs = view?.tag.toString().toInt()
        petBcsAllView = true
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onAllegiClick(view: View?) {
        petAllegi = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onBackClick(view: View?) {
        activity?.let {
            it.finish()
        }
    }

    fun onChildClick(view: View?) {
        petChild = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onDiseaseClick(view: View?) {
        petDisease = view?.tag.toString().toInt()
        title34 = String.format(getString(R.string.sign_comment_34), petName.value)

        if(petDisease == 1)
            getDisease()
        else
            itemDiseaseListDiffer.clearList()

        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onActiveClick(view: View?) {
        petActive = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onOtherClick(view: View?) {
        petOther = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }


    fun onFeedingClick(view: View?) {
        petFeeding = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onEatClick(view: View?) {
        petEat = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onLivingClick(view: View?) {
        petLiving = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onLiving2Click(view: View?) {
        petLiving2 = view?.tag.toString().toInt()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onSexAskClick(view: View?) {
        petSexAsk = petSexAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onChildAskClick(view: View?) {
        petChildAsk = petChildAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onOtherAskClick(view: View?) {
        petOtherAsk = petOtherAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onLiving2AskClick(view: View?) {
        petLiving2Ask = petLiving2Ask.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onLivingAskClick(view: View?) {
        petLivingAsk = petLivingAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onAllegiAskClick(view: View?) {
        petAllegiAsk = petAllegiAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }


    fun onEatAskClick(view: View?) {
        petEatAsk = petEatAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onActiveAskClick(view: View?) {
        petActiveAsk = petActiveAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onDiseaseAskClick(view: View?) {
        petDiseaseAsk = petDiseaseAsk.not()
        title34 = String.format(getString(R.string.sign_comment_34), petName.value)
        executeViewBinding()
        executeViewBindingView(view)
    }


    fun onBcsAskClick(view: View?) {
        petBcsAsk = petBcsAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }


    fun onFeedingAskClick(view: View?) {
        petFeedingAsk = petFeedingAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onBirthAskClick(view: View?) {
        petBirthAsk = petBirthAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }

    fun onWeightAskClick(view: View?) {
        petWeightAsk = petWeightAsk.not()
        executeViewBinding()
        executeViewBindingView(view)
    }


    fun onSubmitClick(view: View?) {
    }


    fun onNameTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        title4 = String.format(getString(R.string.sign_comment_4), text.toString())
        title6 = String.format(getString(R.string.sign_comment_6), text.toString())
        title13 = String.format(getString(R.string.sign_comment_13), text.toString())
        petName.value = text.toString()
        petNameType = true
        executeViewBinding()
    }

    fun onBirthTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        title16 = String.format(getString(R.string.sign_comment_16),petName.value)
        petBirth.value = text.toString()
        petBitrhType = true
        executeViewBinding()
    }

    fun onWeightTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        title18 = String.format(getString(R.string.sign_comment_18), petName.value)
        petWeight.value = text.toString()
        petweightType = true
        executeViewBinding()
    }

    fun onNameTypeTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        petNameTypeText.value = text.toString()
        petSexType = true

        if(petTypeSelect.not())
            getPetType()
        else
            petTypeSelect = false

        executeViewBinding()
    }

    fun getPetType() {
        viewModelScope.launch {
            val type = if(petSelect == 2) "cat" else "dog"
            useCase.getBreedList(petNameTypeText.value.orEmpty() , type).success { data ->
                data?.content?.let { data ->
                    val items = mutableListOf<Any>()
                    this.let {
                        items.addAll(data)
                    }
                    itemListDiffer.allList(items)
                }
                itemListDiffer.adapter.notifyDataSetChanged()
            }.fail {
            }.execute()

        }

    }

    fun getDisease() {
        viewModelScope.launch {
            val type = if(petSelect == 2) "cat" else "dog"
            useCase.getDiseaseList(false).success { data ->
                data?.content?.let { data ->
                    val items = mutableListOf<Any>()
                    this.let {
                        items.addAll(data)
                    }
                    itemDiseaseListDiffer.allList(items)
                }
                itemDiseaseListDiffer.adapter.notifyDataSetChanged()
            }.fail {
            }.execute()

        }
    }

//    fun getAlleigy() {
//        viewModelScope.launch {
//            val type = if(petSelect == 2) "cat" else "dog"
//            useCase.getBreedList(petNameTypeText.value.orEmpty() , type).success { data ->
//                data?.content?.let { data ->
//                    val items = mutableListOf<Any>()
//                    this.let {
//                        items.addAll(data)
//                    }
//                    itemListDiffer.allList(items)
//                }
//                itemListDiffer.adapter.notifyDataSetChanged()
//            }.fail {
//            }.execute()
//
//        }
//    }
}