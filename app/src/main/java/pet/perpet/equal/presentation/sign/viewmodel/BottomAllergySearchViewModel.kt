package pet.perpet.equal.presentation.sign.viewmodel

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore.user
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.domain.usecase.profile.SignUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.sign.differ.SearchAllergyListDiffer
import pet.perpet.equal.presentation.sign.differ.SearchFlexItemListDiffer
import pet.perpet.equal.presentation.sign.fragment.BottomAllergySearchFragmentArgs
import pet.perpet.equal.presentation.sign.fragment.BottomBreedSearchFragmentArgs
import pet.perpet.equal.presentation.sign.viewholder.AllergySearchViewHolder
import pet.perpet.equal.presentation.sign.viewholder.SearchFlexItemViewHolder
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class BottomAllergySearchViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================
    val args by lazy {
        BottomAllergySearchFragmentArgs.fromBundle(arguments)
    }

    var dataSetting: ArrayList<Allergy> = ArrayList()

    private val useCase = SignUseCase()


    val message: String?
        get() = pet.perpet.equal.presentation.getString(R.string.sign_comment_104)
            ?.let { String.format(it, search.value) }


    val search: MutableLiveData<String> = MutableLiveData()

    val searchLenght: Int?
        get() = search.value?.length

    val itemListDiffer by lazy {
        SearchAllergyListDiffer()
    }

    val itemResultListDiffer by lazy {
        SearchFlexItemListDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    val typeResultMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemResultListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(args.item?.toCollection(ArrayList())?.size.nonnull() > 0) {
            dataSetting.clear()
            args.item?.toCollection(ArrayList())?.let { dataSetting.addAll(it) }

            itemResultListDiffer.clearList()
            itemResultListDiffer.allList(dataSetting)
            itemResultListDiffer.adapter.notifyDataSetChanged()
        }

        itemListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is AllergySearchViewHolder) {
                    val item = holder.item
                    if (item is Allergy) {
                        itemListDiffer.snapList().forEach { allery ->
                            if (item.id == allery.id) {
                                item.toggle()
                                item.let {
                                    if (it.isChecked.nonnull()) {
                                        dataSetting.add(it)
                                    } else {
                                        dataSetting.remove(it)
                                    }

                                    itemResultListDiffer.clearList()
                                    itemResultListDiffer.allList(dataSetting)
                                    itemResultListDiffer.adapter.notifyDataSetChanged()

                                }
                            }
                        }
                        itemListDiffer.adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        itemResultListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is SearchFlexItemViewHolder) {
                    val item = holder.item
                    if (item is Allergy) {
                        val it: MutableIterator<*> = dataSetting.iterator()
                        while (it.hasNext()) {
                            val n = it.next() as Any
                            if (n == item) {
                                it.remove()

                                itemListDiffer.snapList().forEach { a ->
                                    if(item.id == a.id) {
                                        a.isChecked = false
                                        return@forEach
                                    }
                                }
                            }
                        }

                        itemResultListDiffer.clearList()
                        itemResultListDiffer.allList(dataSetting)
                        itemListDiffer.adapter.notifyDataSetChanged()
                        itemResultListDiffer.adapter.notifyDataSetChanged()
                    }

                }
            }
        })

        getAllergyList()
    }

    //======================================================================
    // Public Methods
    //======================================================================


    fun onClick(view: View) {
        fragment?.dispatchDismissToResult(dataSetting)
    }

    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            Bundle().apply {
                this.putBoolean("success", false)
            }.run {
                (fragment as BottomSheetDialogFragment<*>).dismiss(this)
            }
        }
    }

    fun onAllergyInsertClick(view: View) {
        viewModelScope.launch {
            useCase.setAllergy( search.value.nonnull(), user?.id.nonnull())
                .success { data ->
                    if (data != null) {
                        var result = false
                        dataSetting.forEach {
                            if(it == data) {
                                result = true
                                return@forEach
                            }
                        }

                        if(result.not()) {
                            dataSetting.add(data)
                            itemResultListDiffer.clearList()
                            itemResultListDiffer.allList(dataSetting)
                            itemResultListDiffer.adapter.notifyDataSetChanged()
                            Toast.makeText(context, "알레르기 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "이미 등록된 알레르기 입니다.", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(context, "등록이 실패되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }.fail {
                }.execute()
        }
    }

    fun onAllergyTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        search.value = text.toString()
        executeViewBindingNew()
    }

    fun getAllergyList() {
        viewModelScope.launch {
            useCase.getAllergyList(search.value.orEmpty()).success { data ->
                data?.content?.let { data ->
                    val item = mutableListOf<Any>()
                    this.let {
                        data.forEach { a ->
                            dataSetting.forEach {
                                if (it.id == a.id) {
                                    a.isChecked = it.isChecked
                                    return@forEach
                                }
                            }
                        }
                        item.addAll(data)
                    }
                    itemListDiffer.allList(item)
                }
                executeViewBinding()
                itemListDiffer.adapter.notifyDataSetChanged()
            }.fail {
            }.execute()

        }
    }
}