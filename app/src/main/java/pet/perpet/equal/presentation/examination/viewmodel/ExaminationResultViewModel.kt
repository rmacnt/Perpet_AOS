package pet.perpet.equal.presentation.examination.viewmodel

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.data.nonnull
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.usecase.medical.MedicalListUseCase
import pet.perpet.domain.usecase.medical.MedicalResultUseCase
import pet.perpet.equal.MyApplication
import pet.perpet.equal.R
import pet.perpet.equal.presentation.examination.fragment.ExaminationResultFragmentArgs
import pet.perpet.equal.presentation.finishAll
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.goHealthResultDetail
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.goSubscribe
import pet.perpet.equal.presentation.goSupplementPackage
import pet.perpet.equal.presentation.sign.differ.PackageItemListDiffer
import pet.perpet.equal.support.navigation.showComment
import pet.perpet.equal.support.navigation.showCommentCode
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date

class ExaminationResultViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        ExaminationResultFragmentArgs.fromBundle(arguments)
    }

    val bottom: String?
        get() = getString(R.string.subscribe_comment66)
            ?.let { String.format(it, args.name.nonnull()) }

    private var medical: Medical? = null

    val titleMain: String?
        get() = getString(R.string.examination_comment2)
            ?.let { String.format(it, args.name.nonnull()) }

    val titlePackage: String?
        get() = getString(R.string.examination_comment4)
            ?.let { String.format(it, args.name.nonnull()) }

    val titleCode: String?
        get() = getString(R.string.examination_comment5)
            ?.let { String.format(it, args.name.nonnull()) }

    val titlePackageComment: String?
        get() = getString(R.string.examination_comment6)
            ?.let { String.format(it, args.name.nonnull()) }

    val subscribeResult: Boolean?
        get() = args.result

    val startDate: String?
        get() = medical?.let {
            "문진일: "+ titleDate(it.update_date)
        }

    val code1Drawable: Drawable?
        get() = medical?.let {
            if (code1.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code2Drawable: Drawable?
        get() = medical?.let {
            if (code2.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code3Drawable: Drawable?
        get() = medical?.let {
            if (code3.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code4Drawable: Drawable?
        get() = medical?.let {
            if (code4.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code5Drawable: Drawable?
        get() = medical?.let {
            if (code5.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code6Drawable: Drawable?
        get() = medical?.let {
            if (code6.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code7Drawable: Drawable?
        get() = medical?.let {
            if (code7.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code8Drawable: Drawable?
        get() = medical?.let {
            if (code8.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code9Drawable: Drawable?
        get() = medical?.let {
            if (code9.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code10Drawable: Drawable?
        get() = medical?.let {
            if (code10.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code11Drawable: Drawable?
        get() = medical?.let {
            if (code11.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code12Drawable: Drawable?
        get() = medical?.let {
            if (code12.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code13Drawable: Drawable?
        get() = medical?.let {
            if (code13.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code14Drawable: Drawable?
        get() = medical?.let {
            if (code14.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

    val code15Drawable: Drawable?
        get() = medical?.let {
            if (code15.value.nonnull()) {
                getDrawable(R.drawable.ic_green_circle)
            } else {
                getDrawable(R.drawable.ic_white_circle)
            }
        } ?: run {
            getDrawable(R.drawable.ic_white_circle)
        }

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

    val arrow: Int?
        get() = arrowType.value

    val arrowCount: Int?
        get() = arrowTypeCount.value

    val arrowType: MutableLiveData<Int> = MutableLiveData(0)
    val arrowTypeCount: MutableLiveData<Int> = MutableLiveData(0)

    val result: ArrayList<Medical> = ArrayList()


    val itemListDiffer by lazy {
        PackageItemListDiffer()
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


    val resultComment: MutableLiveData<String> = MutableLiveData()
    val packageComment: MutableLiveData<String> = MutableLiveData()


    val packageCommentValue: String?
        get() = packageComment.value.nonnull().replace(" ", "\u00A0")

    val resultCommentText: String?
        get() = resultComment.value.nonnull().replace(" ", "\u00A0")


    //======================================================================
    // Public Methods
    //======================================================================

    fun onSubmitClick(view: View) {
        activity?.let { activity ->
            medical?.let {
                activity.goSubscribe(it, null, args.petId.nonnull(), args.name.nonnull())
            }
        }
    }

    fun onHomeClick(view: View) {
        activity?.let {
            Toast.makeText(it , args.name+"의 진단 결과는 이후에 더보기에서 확인 가능합니다.", Toast.LENGTH_SHORT).show()

            if(MyApplication.mainOpen.nonnull().not()) {
                it.finishAll()
                it.goHome()
            } else {
                it.finish()
            }
        }
    }

    fun onRightClick(view: View) {
        executeViewBindingView(view)
    }

    fun onLeftClick(view: View) {
        executeViewBindingView(view)
    }

    fun onDetailClick(view: View) {
        activity?.let { activity ->
            medical?.let {
                activity.goHealthResultDetail(it, args.petId.nonnull(), args.name.nonnull() , args.result)
            }
        }
    }

    fun onPackageDetailClick(view: View) {
        activity?.let { activity ->
            medical?.let {
                activity.goSupplementPackage(it, args.petId.nonnull(), args.name.nonnull() , args.result)
            }
        }
    }

    fun onCommentClick(view: View) {
        activity?.showComment(args.name.nonnull())
    }

    fun onCodeCommentClick(view: View) {
        activity?.showCommentCode()
    }

    @SuppressLint("SimpleDateFormat")
    fun getData(medicalId : Int , result: (Medical) -> Unit) {
        MedicalResultUseCase().parameter2 {
            this.id = medicalId
        }.success {
            it?.let {
                this.medical = it
                resultComment.postValue(medical?.resultComment.toString())
                packageComment.postValue(medical?.packageComment.toString())

                medical?.packages?.let { data ->
                    val items = mutableListOf<Any>()
                    this.let {
                        items.addAll(data)
                    }
                    Collections.sort(items, Collections.reverseOrder())
                    itemListDiffer.allList(items)
                }

                itemListDiffer.adapter.notifyDataSetChanged()

                it.packages?.let { data ->
                    data.forEachIndexed { index, it ->
                        when (it.product?.code_index) {
                            0 -> code1.value = true
                            1 -> code2.value = true
                            2 -> code3.value = true
                            3 -> code4.value = true
                            4 -> code5.value = true
                            5 -> code6.value = true
                            6 -> code7.value = true
                            7 -> code8.value = true
                            8 -> code9.value = true
                            9 -> code10.value = true
                            10 -> code11.value = true
                            11 -> code12.value = true
                            12 -> code13.value = true
                            13 -> code14.value = true
                            14 -> code15.value = true
                        }
                    }

                }
                executeViewBinding()
                result(it)
            }
        }.fail {
        }.execute()
    }


    fun getDataList() {
        MedicalListUseCase().parameter2 {
            this.petId = args.id?.toInt().nonnull()
        }.success {
            it?.content?.let {
                result.clear()
                result.addAll(it)

                if(result.size > 1) {
                    arrowType.value = 1
                } else {
                    arrowType.value = 0
                }

                executeViewBinding()
            }
        }.fail {
        }.execute()
    }

    @SuppressLint("SimpleDateFormat")
    private fun titleDate(date: String?) : String? {

        val formatter = SimpleDateFormat("yyyy. MM. dd")
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")

        date?.let {
            return formatter.format(dateFormatter.parse(it) as Date)
        } ?: run {
            return ""
        }

    }
}