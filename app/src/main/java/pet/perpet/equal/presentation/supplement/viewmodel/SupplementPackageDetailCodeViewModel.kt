package pet.perpet.equal.presentation.supplement.viewmodel

import android.graphics.drawable.Drawable
import android.text.Spanned
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.stocard.markdown_to_spanned.Markdown
import pet.perpet.data.nonnull
import pet.perpet.domain.model.medical.Product
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.goSubscribe
import pet.perpet.equal.presentation.goSupplementComponent
import pet.perpet.equal.presentation.supplement.differ.CodeImageItemListDiffer
import pet.perpet.equal.presentation.supplement.differ.CodeItemListDiffer
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageDetailCodeFragmentArgs
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageFragmentArgs
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import java.util.Collections

class SupplementPackageDetailCodeViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        SupplementPackageDetailCodeFragmentArgs.fromBundle(arguments)
    }

    val result: Boolean?
        get() = args.result

    var selectIndex : Int = -1

    val code1: MutableLiveData<Boolean> = MutableLiveData(false)
    val code2: MutableLiveData<Boolean> = MutableLiveData(false)
    val code3: MutableLiveData<Boolean> = MutableLiveData(false)
    val code4: MutableLiveData<Boolean> = MutableLiveData(false)
    val code5: MutableLiveData<Boolean> = MutableLiveData(false)
    val code6: MutableLiveData<Boolean> = MutableLiveData(false)
    val code7: MutableLiveData<Boolean> = MutableLiveData(false)
    val code8: MutableLiveData<Boolean> = MutableLiveData(false)
    val code9: MutableLiveData<Boolean> = MutableLiveData(false)
    val code10: MutableLiveData<Boolean> = MutableLiveData(false)
    val code11: MutableLiveData<Boolean> = MutableLiveData(false)
    val code12: MutableLiveData<Boolean> = MutableLiveData(false)
    val code13: MutableLiveData<Boolean> = MutableLiveData(false)
    val code14: MutableLiveData<Boolean> = MutableLiveData(false)
    val code15: MutableLiveData<Boolean> = MutableLiveData(false)

    val omega: Boolean?
        get() = codeName.value.nonnull().contains("이퀄 오메가3")

    val cautionAsk: MutableLiveData<Boolean> = MutableLiveData(false)
    val storagePrecautionsAsk: MutableLiveData<Boolean> = MutableLiveData(false)

    val code1Drawable: Drawable?
        get() = if(selectIndex == 0) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code1.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code2Drawable: Drawable?
        get() = if(selectIndex == 1) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code2.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code3Drawable: Drawable?
        get() = if(selectIndex == 2) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code3.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code4Drawable: Drawable?
        get() = if(selectIndex == 3) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code4.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code5Drawable: Drawable?
        get() = if(selectIndex == 4) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code5.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code6Drawable: Drawable?
        get() = if(selectIndex == 5) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code6.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code7Drawable: Drawable?
        get() = if(selectIndex == 6) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code7.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code8Drawable: Drawable?
        get() = if(selectIndex == 7) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code8.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code9Drawable: Drawable?
        get() = if(selectIndex == 8) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code9.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code10Drawable: Drawable?
        get() = if(selectIndex == 9) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code10.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code11Drawable: Drawable?
        get() = if(selectIndex == 10) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code11.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code12Drawable: Drawable?
        get() = if(selectIndex == 11) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code12.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code13Drawable: Drawable?
        get() = if(selectIndex == 12) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code13.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code14Drawable: Drawable?
        get() = if(selectIndex == 13) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code14.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    val code15Drawable: Drawable?
        get() = if(selectIndex == 14) {
            getDrawable(R.drawable.package_code_drawable)
        } else {
            if(code15.value.nonnull()) {
                getDrawable(R.drawable.package_code_drawable_on)
            } else {
                getDrawable(R.drawable.package_code_drawable_off)
            }
        }

    private var supplementComponent: Product? = null

    val code1Count: MutableLiveData<Int> = MutableLiveData(0)
    val code2Count: MutableLiveData<Int> = MutableLiveData(0)
    val code3Count: MutableLiveData<Int> = MutableLiveData(0)
    val code4Count: MutableLiveData<Int> = MutableLiveData(0)
    val code5Count: MutableLiveData<Int> = MutableLiveData(0)
    val code6Count: MutableLiveData<Int> = MutableLiveData(0)
    val code7Count: MutableLiveData<Int> = MutableLiveData(0)
    val code8Count: MutableLiveData<Int> = MutableLiveData(0)
    val code9Count: MutableLiveData<Int> = MutableLiveData(0)
    val code10Count: MutableLiveData<Int> = MutableLiveData(0)
    val code11Count: MutableLiveData<Int> = MutableLiveData(0)
    val code12Count: MutableLiveData<Int> = MutableLiveData(0)
    val code13Count: MutableLiveData<Int> = MutableLiveData(0)
    val code14Count: MutableLiveData<Int> = MutableLiveData(0)
    val code15Count: MutableLiveData<Int> = MutableLiveData(0)

    var codeIndex1 = -1
    var codeIndex2 = -1
    var codeIndex3 = -1
    var codeIndex4 = -1
    var codeIndex5 = -1
    var codeIndex6 = -1
    var codeIndex7 = -1
    var codeIndex8 = -1
    var codeIndex9 = -1
    var codeIndex10 = -1
    var codeIndex11 = -1
    var codeIndex12 = -1
    var codeIndex13 = -1
    var codeIndex14 = -1
    var codeIndex15 = -1

    val code1CountText: String?
        get() = code1Count.value.toString()
    val code2CountText: String?
        get() = code2Count.value.toString()
    val code3CountText: String?
        get() = code3Count.value.toString()
    val code4CountText: String?
        get() = code4Count.value.toString()
    val code5CountText: String?
        get() = code5Count.value.toString()
    val code6CountText: String?
        get() = code6Count.value.toString()
    val code7CountText: String?
        get() = code7Count.value.toString()
    val code8CountText: String?
        get() = code8Count.value.toString()
    val code9CountText: String?
        get() = code9Count.value.toString()
    val code10CountText: String?
        get() = code10Count.value.toString()
    val code11CountText: String?
        get() = code11Count.value.toString()
    val code12CountText: String?
        get() = code12Count.value.toString()
    val code13CountText: String?
        get() = code13Count.value.toString()
    val code14CountText: String?
        get() = code14Count.value.toString()
    val code15CountText: String?
        get() = code15Count.value.toString()


    val cautionValue: Spanned?
        get() = caution.value

    val storagePrecautionsValue: Spanned?
        get() = storagePrecautions.value

    val codeImage: MutableLiveData<String> = MutableLiveData("")
    val codeSummary: MutableLiveData<String> = MutableLiveData("")
    val codeName: MutableLiveData<String> = MutableLiveData("")
    val codeDescription: MutableLiveData<String> = MutableLiveData("")
    val storagePrecautions: MutableLiveData<Spanned> = MutableLiveData()
    val caution: MutableLiveData<Spanned> = MutableLiveData()


    val name: String?
        get() = codeName.value

    val image: String?
        get() = codeImage.value

    val summary: String?
        get() = codeSummary.value

    val description: String?
        get() = codeDescription.value?.replace(" ", "\u00A0")


    val price: MutableLiveData<Spanned> = MutableLiveData()
    val priceMonth: MutableLiveData<String> = MutableLiveData("")
    val priceTotalPrice: MutableLiveData<String> = MutableLiveData("")


    val itemListDiffer by lazy {
        CodeItemListDiffer()
    }



    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    init {
        args.medical?.let {
            it.packages?.let { data ->
                data.forEachIndexed { index, it ->

                    when (it.product?.code_index) {
                        0 -> {
                            code1.value = true
                            code1Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex1 = index
                            if(selectIndex == -1) {
                                selectIndex = 0
                            }
                        }

                        1 -> {
                            code2.value = true
                            code2Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex2 = index
                            if(selectIndex == -1) {
                                selectIndex = 1
                            }
                        }

                        2 -> {
                            code3.value = true
                            code3Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex3 = index
                            if(selectIndex == -1) {
                                selectIndex = 2
                            }
                        }

                        3 -> {
                            code4.value = true
                            code4Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex4 = index
                            if(selectIndex == -1) {
                                selectIndex = 3
                            }
                        }

                        4 -> {
                            code5.value = true
                            code5Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex5 = index
                            if(selectIndex == -1) {
                                selectIndex = 4
                            }
                        }

                        5 -> {
                            code6.value = true
                            code6Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex6 = index
                            if(selectIndex == -1) {
                                selectIndex = 5
                            }
                        }

                        6 -> {
                            code7.value = true
                            code7Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex7 = index
                            if(selectIndex == -1) {
                                selectIndex = 6
                            }
                        }

                        7 -> {
                            code8.value = true
                            code8Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex8= index
                            if(selectIndex == -1) {
                                selectIndex = 7
                            }
                        }

                        8 -> {
                            code9.value = true
                            code9Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex9= index
                            if(selectIndex == -1) {
                                selectIndex = 8
                            }
                        }

                        9 -> {
                            code10.value = true
                            code10Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex10= index
                            if(selectIndex == -1) {
                                selectIndex = 9
                            }
                        }

                        10 -> {
                            code11.value = true
                            code11Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex11= index
                            if(selectIndex == -1) {
                                selectIndex = 10
                            }
                        }

                        11 -> {
                            code12.value = true
                            code12Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex12= index
                            if(selectIndex == -1) {
                                selectIndex = 11
                            }
                        }

                        12 -> {
                            code13.value = true
                            code13Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex13= index
                            if(selectIndex == -1) {
                                selectIndex = 12
                            }
                        }

                        13 -> {
                            code14.value = true
                            code14Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex14= index
                            if(selectIndex == -1) {
                                selectIndex = 13
                            }
                        }

                        14 -> {
                            code15.value = true
                            code15Count.value = it.rxInfo?.daily_dosage_mg
                            codeIndex15= index
                            if(selectIndex == -1) {
                                selectIndex = 14
                            }
                        }
                    }
                }

                if(selectIndex != -1) {
                    settingCode(selectIndex)
                }
            }
        }


    }
    //======================================================================
    // Public Methods
    //======================================================================


    fun onSubmitClick(view: View) {
        activity?.let {
            args.medical?.let {
                activity?.goSubscribe(it, null, args.petId.nonnull() , args.name.nonnull())
                activity?.finish()
            }
        }
    }

    fun onHomeClick(view: View) {
        activity?.let {
            it.finish()
        }

    }

    fun onCodeDetailClick(view: View) {
        activity?.let {activity->
            supplementComponent?.let {
                activity.goSupplementComponent(it)
            }
        }

    }

    fun onCautionClick(view: View) {
        cautionAsk.value = cautionAsk.value?.not()
        executeViewBinding()
    }

    fun onStoragePrecautionsClick(view: View) {
        storagePrecautionsAsk.value = storagePrecautionsAsk.value?.not()
        executeViewBinding()
    }



    fun onCodeClick(view: View) {
        when (view.tag.toString().toInt()) {
            0 -> {
                if(code1.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex1].product?.image
                            codeName.value = data[codeIndex1].product?.name_kor
                            codeSummary.value = data[codeIndex1].product?.summary
                            codeDescription.value = data[codeIndex1].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex1].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex1].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex1].product
                            data[codeIndex1].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }

                    }
                    executeViewBinding()
                }


            }

            1 -> {
                if(code2.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex2].product?.image
                            codeName.value = data[codeIndex2].product?.name_kor
                            codeSummary.value = data[codeIndex2].product?.summary
                            codeDescription.value = data[codeIndex2].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex2].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex2].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex2].product
                            data[codeIndex2].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }
                    executeViewBinding()
                }
            }

            2 -> {
                if(code3.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex3].product?.image
                                codeName.value = data[codeIndex3].product?.name_kor
                                codeSummary.value = data[codeIndex3].product?.summary
                                codeDescription.value = data[codeIndex3].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex3].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex3].product?.storage_precautions?.trim())

                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex3].product
                                data[codeIndex3].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }

                    }

                    executeViewBinding()
                }
            }

            3 -> {
                if(code4.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex4].product?.image
                                codeName.value = data[codeIndex4].product?.name_kor
                                codeSummary.value = data[codeIndex4].product?.summary
                                codeDescription.value = data[codeIndex4].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex4].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex4].product?.storage_precautions?.trim())

                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex4].product
                                data[codeIndex4].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }

                    }
                    executeViewBinding()
                }
            }

            4 -> {
                if(code5.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex5].product?.image
                                codeName.value = data[codeIndex5].product?.name_kor
                                codeSummary.value = data[codeIndex5].product?.summary
                                codeDescription.value = data[codeIndex5].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex5].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex5].product?.storage_precautions?.trim())

                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex5].product
                                data[codeIndex5].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            5 -> {
                if(code6.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex6].product?.image
                                codeName.value = data[codeIndex6].product?.name_kor
                                codeSummary.value = data[codeIndex6].product?.summary
                                codeDescription.value = data[codeIndex6].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex6].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex6].product?.storage_precautions?.trim())

                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex6].product
                                data[codeIndex6].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                    executeViewBinding()
                }
            }

            6 -> {
                if(code7.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex7].product?.image
                            codeName.value = data[codeIndex7].product?.name_kor
                            codeSummary.value = data[codeIndex7].product?.summary
                            codeDescription.value = data[codeIndex7].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex7].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex7].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex7].product
                            data[codeIndex7].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            7 -> {
                if(code8.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex8].product?.image
                            codeName.value = data[codeIndex8].product?.name_kor
                            codeSummary.value = data[codeIndex8].product?.summary
                            codeDescription.value = data[codeIndex8].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex8].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex8].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex8].product
                            data[codeIndex8].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            8 -> {
                if(code9.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex9].product?.image
                            codeName.value = data[codeIndex9].product?.name_kor
                            codeSummary.value = data[codeIndex9].product?.summary
                            codeDescription.value = data[codeIndex9].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex9].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex9].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex9].product
                            data[codeIndex9].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            9 -> {
                if(code10.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex10].product?.image
                            codeName.value = data[codeIndex10].product?.name_kor
                            codeSummary.value = data[codeIndex10].product?.summary
                            codeDescription.value = data[codeIndex10].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex10].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex10].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex10].product
                            data[codeIndex10].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            10 -> {
                if(code11.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex11].product?.image
                            codeName.value = data[codeIndex11].product?.name_kor
                            codeSummary.value = data[codeIndex11].product?.summary
                            codeDescription.value = data[codeIndex11].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex11].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex11].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex11].product
                            data[codeIndex11].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }
                    executeViewBinding()
                }
            }

            11 -> {
                if(code12.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex12].product?.image
                            codeName.value = data[codeIndex12].product?.name_kor
                            codeSummary.value = data[codeIndex12].product?.summary
                            codeDescription.value = data[codeIndex12].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex12].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex12].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex12].product
                            data[codeIndex12].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            12 -> {
                if(code13.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex13].product?.image
                            codeName.value = data[codeIndex13].product?.name_kor
                            codeSummary.value = data[codeIndex13].product?.summary
                            codeDescription.value = data[codeIndex13].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex13].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex13].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex13].product
                            data[codeIndex13].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            13 -> {
                if(code14.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex14].product?.image
                            codeName.value = data[codeIndex14].product?.name_kor
                            codeSummary.value = data[codeIndex14].product?.summary
                            codeDescription.value = data[codeIndex14].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex14].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex14].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex14].product
                            data[codeIndex14].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            14 -> {
                if(code15.value.nonnull()) {
                    selectIndex = view.tag.toString().toInt()
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex15].product?.image
                            codeName.value = data[codeIndex15].product?.name_kor
                            codeSummary.value = data[codeIndex15].product?.summary
                            codeDescription.value = data[codeIndex15].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex15].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex15].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex15].product
                            data[codeIndex15].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

        }

    }

    fun settingCode(code: Int) {
        when (code) {
            0 -> {
                if(code1.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex1].product?.image
                            codeName.value = data[codeIndex1].product?.name_kor
                            codeSummary.value = data[codeIndex1].product?.summary
                            codeDescription.value = data[codeIndex1].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex1].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex1].product?.storage_precautions?.trim())

                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex1].product
                            data[codeIndex1].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()

                            }
                        }

                    }
                    executeViewBinding()
                }


            }

            1 -> {
                if(code2.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex2].product?.image
                            codeName.value = data[codeIndex2].product?.name_kor
                            codeSummary.value = data[codeIndex2].product?.summary
                            codeDescription.value = data[codeIndex2].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex2].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex2].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex2].product
                            data[codeIndex2].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }
                    executeViewBinding()
                }
            }

            2 -> {
                if(code3.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex3].product?.image
                                codeName.value = data[codeIndex3].product?.name_kor
                                codeSummary.value = data[codeIndex3].product?.summary
                                codeDescription.value = data[codeIndex3].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex3].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex3].product?.storage_precautions?.trim())


                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex3].product
                                data[codeIndex3].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }

                    }

                    executeViewBinding()
                }
            }

            3 -> {
                if(code4.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex4].product?.image
                                codeName.value = data[codeIndex4].product?.name_kor
                                codeSummary.value = data[codeIndex4].product?.summary
                                codeDescription.value = data[codeIndex4].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex4].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex4].product?.storage_precautions?.trim())


                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex4].product
                                data[codeIndex4].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }

                    }
                    executeViewBinding()
                }
            }

            4 -> {
                if(code5.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex5].product?.image
                                codeName.value = data[codeIndex5].product?.name_kor
                                codeSummary.value = data[codeIndex5].product?.summary
                                codeDescription.value = data[codeIndex5].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex5].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex5].product?.storage_precautions?.trim())


                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex5].product
                                data[codeIndex5].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            5 -> {
                if(code6.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            it.packages?.let { data ->
                                codeImage.value = data[codeIndex6].product?.image
                                codeName.value = data[codeIndex6].product?.name_kor
                                codeSummary.value = data[codeIndex6].product?.summary
                                codeDescription.value = data[codeIndex6].product?.description
                                caution.value = Markdown.fromMarkdown(data[codeIndex6].product?.caution?.trim())
                                storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex6].product?.storage_precautions?.trim())


                                val items = mutableListOf<Any>()
                                supplementComponent = data[codeIndex6].product
                                data[codeIndex6].product?.ingredient?.let {
                                    items.addAll(it)
                                    itemListDiffer.clearList()
                                    Collections.sort(items, Collections.reverseOrder())
                                    itemListDiffer.allList(items)
                                    itemListDiffer.adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                    executeViewBinding()
                }
            }

            6 -> {
                if(code7.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex7].product?.image
                            codeName.value = data[codeIndex7].product?.name_kor
                            codeSummary.value = data[codeIndex7].product?.summary
                            codeDescription.value = data[codeIndex7].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex7].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex7].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex7].product
                            data[codeIndex7].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            7 -> {
                if(code8.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex8].product?.image
                            codeName.value = data[codeIndex8].product?.name_kor
                            codeSummary.value = data[codeIndex8].product?.summary
                            codeDescription.value = data[codeIndex8].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex8].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex8].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex8].product
                            data[codeIndex8].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            8 -> {
                if(code9.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex9].product?.image
                            codeName.value = data[codeIndex9].product?.name_kor
                            codeSummary.value = data[codeIndex9].product?.summary
                            codeDescription.value = data[codeIndex9].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex9].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex9].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex9].product
                            data[codeIndex9].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            9 -> {
                if(code10.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex10].product?.image
                            codeName.value = data[codeIndex10].product?.name_kor
                            codeSummary.value = data[codeIndex10].product?.summary
                            codeDescription.value = data[codeIndex10].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex10].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex10].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex10].product
                            data[codeIndex10].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            10 -> {
                if(code11.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex11].product?.image
                            codeName.value = data[codeIndex11].product?.name_kor
                            codeSummary.value = data[codeIndex11].product?.summary
                            codeDescription.value = data[codeIndex11].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex11].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex11].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex11].product
                            data[codeIndex11].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }
                    executeViewBinding()
                }
            }

            11 -> {
                if(code12.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex12].product?.image
                            codeName.value = data[codeIndex12].product?.name_kor
                            codeSummary.value = data[codeIndex12].product?.summary
                            codeDescription.value = data[codeIndex12].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex12].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex12].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex12].product
                            data[codeIndex12].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            12 -> {
                if(code13.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex13].product?.image
                            codeName.value = data[codeIndex13].product?.name_kor
                            codeSummary.value = data[codeIndex13].product?.summary
                            codeDescription.value = data[codeIndex13].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex13].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex13].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex13].product
                            data[codeIndex13].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            13 -> {
                if(code14.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex14].product?.image
                            codeName.value = data[codeIndex14].product?.name_kor
                            codeSummary.value = data[codeIndex14].product?.summary
                            codeDescription.value = data[codeIndex14].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex14].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex14].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex14].product
                            data[codeIndex14].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

            14 -> {
                if(code15.value.nonnull()) {
                    args.medical?.let {
                        it.packages?.let { data ->
                            codeImage.value = data[codeIndex15].product?.image
                            codeName.value = data[codeIndex15].product?.name_kor
                            codeSummary.value = data[codeIndex15].product?.summary
                            codeDescription.value = data[codeIndex15].product?.description
                            caution.value = Markdown.fromMarkdown(data[codeIndex15].product?.caution?.trim())
                            storagePrecautions.value = Markdown.fromMarkdown(data[codeIndex15].product?.storage_precautions?.trim())


                            val items = mutableListOf<Any>()
                            supplementComponent = data[codeIndex15].product
                            data[codeIndex15].product?.ingredient?.let {
                                items.addAll(it)
                                itemListDiffer.clearList()
                                Collections.sort(items, Collections.reverseOrder())
                                itemListDiffer.allList(items)
                                itemListDiffer.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    executeViewBinding()
                }
            }

        }
    }


}