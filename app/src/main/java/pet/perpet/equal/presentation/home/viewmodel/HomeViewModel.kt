package pet.perpet.equal.presentation.home.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.main.MainCard
import pet.perpet.domain.model.pet.Pet
import pet.perpet.domain.usecase.main.MainUseCase
import pet.perpet.domain.usecase.medical.MedicalResultUseCase
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.createMainScope
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.presentation.goInbox
import pet.perpet.equal.presentation.goMore
import pet.perpet.equal.presentation.goPagerWeight
import pet.perpet.equal.presentation.goSearch
import pet.perpet.equal.presentation.goSupplementPackage
import pet.perpet.equal.presentation.home.differ.MainPetListDiffer
import pet.perpet.equal.presentation.home.viewholder.ItemHomeCardNewViewHolder
import pet.perpet.equal.support.push.BadgeStore
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import java.text.SimpleDateFormat
import java.util.Date


class HomeViewModel (fragment: Fragment) : UseViewModel(fragment) {


    //=========================w=============================================
    // Variables
    //======================================================================

    private val useCase = MainUseCase()

    val more: Boolean?
        get() = moreResult.value

    val name: String?
        get() = petName.value

    var first: Boolean = false

    var push: Boolean = false

    val itemPetListDiffer by lazy {
        MainPetListDiffer()
    }
    val pushTalkCount: MutableLiveData<Boolean> = MutableLiveData(BadgeStore.badgeCount?.isTalkCount)

    val petMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemPetListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    var moreResult : MutableLiveData<Boolean> = MutableLiveData(false)
    var petName : MutableLiveData<String> = MutableLiveData("")



    //======================================================================
    // Private Variables
    //======================================================================

    private var mainPet: Pet? = null

    private val dataList: ArrayList<MainCard> = ArrayList()

    //======================================================================
    // Public Methods
    //======================================================================

    fun createPreviewAdapter( dataList: ArrayList<MainCard>?): BaseRecyclerViewAdapter {
        val a = object : BaseRecyclerViewAdapter() {
            override fun onCreateItemViewHolder(
                parent: ViewGroup,
                viewType: Int,
            ): RecyclerViewHolder<*> {
                return ItemHomeCardNewViewHolder(
                    parent
                )
            }
        }
        a.setHasStableIds(true)
        a.provider = object : BaseRecyclerViewAdapter.ArgumentsProvider {

            override fun getItemHeaderCount(): Int {
                return 0
            }

            override fun getSupportItemCount(): Int {
                return dataList?.size.nonnull()
            }

            override fun getSupportItemViewType(position: Int): Int {
                return -1
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return dataList?.get(position)
            }

            override fun onBindArguments(viewType: Int, arguments: Bundle?) {
            }
        }
        return a
    }

    fun onInBoxClick(view: View) {
        activity?.goInbox()
    }
    fun onSearchClick(view: View) {
        activity?.goSearch()
    }

    fun onSignClick(view: View) {

        getPetListSize {
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
                moreResult.value = false
                executeViewBinding()
                activity?. goPagerWeight()
            }
        }
    }

    fun onMoreClick(view: View) {
        activity?.goMore()
    }

    fun onOutSideOutClick(view: View) {
        moreResult.value = false
        executeViewBinding()
    }
    fun onOutSideOutInClick(view: View) {
        moreResult.value = moreResult.value?.not()
        executeViewBindingNew()
    }

    fun onHomeResetClick(view: View) {
        executeViewBindingView(view)
    }

    fun onPackageClick(view: View) {
        if (UserStore.mainPet?.latest_medical_id != null) {
            MedicalResultUseCase().parameter2 {
                this.id = UserStore.mainPet?.latest_medical_id.nonnull()
            }.success {
                it?.let {
                    if (UserStore.mainPet?.latest_order_id != null) {
                        activity?.goSupplementPackage(it,  mainPet?.id.toString().nonnull() ,  mainPet?.name.toString().nonnull() , true)
                    }else {
                        activity?.goSupplementPackage(it,  mainPet?.id.toString().nonnull() ,  mainPet?.name.toString().nonnull() , false)
                    }
                }
            }.fail {
            }.execute()

//            if (UserStore.mainPet?.latest_order_id != null) {
//                activity?.getSubscribeDetail(UserStore.mainPet?.latest_order_id.nonnull().toString() , UserStore.mainPet?.name.nonnull() )
//            } else {
//
//                MedicalResultUseCase().parameter2 {
//                    this.id = UserStore.mainPet?.latest_medical_id.nonnull()
//                }.success {
//                    it?.let {
//                        activity?.goSupplementPackage(it,  mainPet?.id.toString().nonnull() ,  mainPet?.name.toString().nonnull() , false)
//                    }
//                }.fail {
//                }.execute()
//            }
        } else {
            activity?.let { activity ->
                AppAlertDialog(
                    activity,
                getString(R.string.dialog_title),
                getString(R.string.medical_call),
                getString(R.string.subscribe_comment65)

                ).show(
                onClickNegative = {
                    it.dismiss()
                },
                onClickPositive = {
                    activity.goHeathStart()
                    it.dismiss()
                }

                )
            }

        }
    }
    private fun getPetListSize(result: (size: Int) -> Unit) {
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

    fun getPetList(callback: (ArrayList<MainCard>) -> Unit) {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                it.content?.let { data ->
                    var last = false
                    data.forEach {
                        if (it.main_yn == "Y") {
                            last = true
                            mainPet = it
                            UserStore.mainPetSync(it)
                            petName.value = mainPet?.name
                        }
                    }
                    if (last.not()) {
                        mainPet = data[0]
                        petName.value = data[0].name
                    }
                    first = true
                    val items = mutableListOf<Any>()
                    this.let {
                        items.addAll(data)
                    }
                    itemPetListDiffer.clearList()
                    itemPetListDiffer.allList(items)
                    itemPetListDiffer.adapter.notifyDataSetChanged()
                    executeViewBinding()
                    createHome {
                        callback(it)
                    }
                }

            }
        }.fail {
        }.execute()
    }

    @SuppressLint("SimpleDateFormat")
    private fun createHome(callback: (ArrayList<MainCard>) -> Unit) {
        createMainScope().launch {
            useCase.getMainCard(mainPet?.id.toString()).success { data ->
                data?.content?.let { data ->
                    this.let {
                        dataList.clear()
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                        mainPet?.let {
                            if(it.latest_medical_id == null) {
                                val sampleCard = MainCard(null , null,null , null, null, null, null, null , null, null, null, getString(R.string.main_comment2), null , null, getString(R.string.main_comment1), getString(R.string.main_comment3), null, null, null , R.drawable.main_card_sample1 , 1)
                                dataList.add(sampleCard)
                            } else {
                                if(it.next_medical_date != null) {
                                    val nextDate = it.next_medical_date?.let { date -> dateFormat.parse(date) }
                                    val nowtDate = dateFormat.parse(dateFormat.format(Date(System.currentTimeMillis())))

                                    val compare  = nextDate?.compareTo(nowtDate)
                                    compare?.let { data->
                                        if (data < 0) {
                                            val sampleCard = MainCard(null , null,null , null, null, null, null, null , null, null, null, getString(R.string.main_comment5), null , null, getString(R.string.main_comment4), getString(R.string.main_comment6), null, null, null , R.drawable.main_card_sample2 , 2  ,mainPet?.id.nonnull())
                                            dataList.add(sampleCard)
                                        }
                                    }
                                }

                                if(it.has_medical == true && it.has_subscription == false) {
                                    val sampleCard = MainCard(null , null,null , null, null, null, null, null , null, null, null, getString(R.string.main_comment8), null , null, getString(R.string.main_comment7), getString(R.string.main_comment9), null, null, null , R.drawable.main_card_sample3  , 3 , mainPet?.id.nonnull())
                                    dataList.add(sampleCard)
                                }

                                val sampleCard = MainCard(null , null,null , null, null, null, null, null , null, null, null, getString(R.string.main_comment11), null , null, getString(R.string.main_comment10), getString(R.string.main_comment12), null, null, null , R.drawable.main_card_sample4 , 4 , mainPet?.id.nonnull() )
                                dataList.add(sampleCard)


                                if(it.latest_order_id != null) {
                                    val orderCard = MainCard(null , null,null , null, null, null, null, null , null, null, null, getString(R.string.main_comment14), null , null, getString(R.string.main_comment13), getString(R.string.main_comment15), null, null, null , R.drawable.main_card_sample5  , 5 , mainPet?.id.nonnull())
                                    dataList.add(orderCard)
                                    val intakeCard = MainCard(null , null,null , null, null, null, null, null , null, null, null, getString(R.string.main_comment17), null , null, getString(R.string.main_comment16), getString(R.string.main_comment18), null, null, null , R.drawable.main_card_sample1  , 6 , mainPet?.id.nonnull())
                                    dataList.add(intakeCard)
                                }else {}
                            }

                            val sampleSize = dataList.size
                            val maxCount = data.size + sampleSize
                            if(dataList.size < 8) {
                                data.forEach {
                                    if( dataList.size  > 7 || maxCount == dataList.size ) {
                                        return@forEach
                                    }else {
                                        dataList.add(it)
                                    }
                                }
                            }
                            callback(dataList)
                        }
                    }
                }
            }.fail {
            }.execute()
        }
    }
}