package pet.perpet.equal.presentation.sign.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.model.profile.Disease
import pet.perpet.domain.usecase.profile.SignUseCase
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.sign.differ.DiseaseSearchFlexItemListDiffer
import pet.perpet.equal.presentation.sign.differ.SearchDiseaseListDiffer
import pet.perpet.equal.presentation.sign.fragment.BottomDiseaseSearchFragmentArgs
import pet.perpet.equal.presentation.sign.viewholder.DiseaseSearchFlexItemViewHolder
import pet.perpet.equal.presentation.sign.viewholder.DiseaseSearchViewHolder
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class BottomDiseaseSearchViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val args by lazy {
        BottomDiseaseSearchFragmentArgs.fromBundle(arguments)
    }

    var dataSetting: ArrayList<Disease> = ArrayList()

    private val useCase = SignUseCase()

    val search: MutableLiveData<String> = MutableLiveData()

    val itemListDiffer by lazy {
        SearchDiseaseListDiffer()
    }

    val itemResultListDiffer by lazy {
        DiseaseSearchFlexItemListDiffer()
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

                if (holder is DiseaseSearchViewHolder) {
                    val item = holder.item
                    if (item is Disease) {
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

                if (holder is DiseaseSearchFlexItemViewHolder) {
                    val item = holder.item
                    if (item is Disease) {
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

        getDiseaseSearch()
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

    fun onDiseaseTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        search.value = text.toString()
        executeViewBindingNew()
    }

    fun getDiseaseSearch() {
        viewModelScope.launch {
            useCase.getDiseaseCommentList(false, search.value.orEmpty() ,args.diseaseId?.toInt().nonnull() ).success { data ->
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