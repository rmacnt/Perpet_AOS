package pet.perpet.equal.presentation.sign.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore.user
import pet.perpet.domain.model.profile.Breed
import pet.perpet.domain.usecase.profile.SignUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.sign.fragment.BottomBreedSearchFragmentArgs
import pet.perpet.equal.presentation.sign.viewholder.BreedSearchViewHolder
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.AdapterUtil
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class BottomBreedSearchViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    private val useCase = SignUseCase()

    var selectBreed: Any? = null

    val message: String?
        get() = String.format(getString(R.string.sign_comment_61), petType.value)

    val args by lazy {
        BottomBreedSearchFragmentArgs.fromBundle(arguments)
    }

    val petType: MutableLiveData<String> = MutableLiveData()


    val petTypeLength: Int?
        get() = petType.value?.length

    private val dataSet = mutableListOf<Any>()

    val adapter: BaseRecyclerViewAdapter by lazy {
        val a = object : BaseRecyclerViewAdapter(provider) {
            override fun onCreateItemViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerViewHolder<*>? {
                return BreedSearchViewHolder(parent)
            }
        }
        a.setHasStableIds(true)
        a
    }

    val provider: BaseRecyclerViewAdapter.ArgumentsProvider by lazy {
        object : BaseRecyclerViewAdapter.ArgumentsProvider {
            override fun getItemHeaderCount(): Int = 0

            override fun getSupportItemCount(): Int {
                return dataSet.size.nonnull()
            }

            override fun getSupportItemViewType(position: Int): Int = -1

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return dataSet[position]
            }

            override fun onBindArguments(viewType: Int, arguments: Bundle?) {
            }
        }
    }


    //======================================================================
    // Public Methods
    //======================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is BreedSearchViewHolder) {
                    val item = holder.item
                    if (item is Breed) {
                        setClearCheck()
                        snapList().forEach { breed ->
                            if (item.id == breed.id) {
                                item.toggle()
                            }
                        }
                        AdapterUtil.notifySupportDataSetChanged(adapter, true)
                    }
                }
            }
        })

    }

    fun snapList(): List<Breed> {
        val newList = mutableListOf<Breed>()
        newList.addAll(dataSet.filterIsInstance<Breed>())
        return newList
    }

    fun setClearCheck(){
        try {
            dataSet.filterIsInstance<Breed>().forEach {
                it.isChecked = false
            }
        }catch (e: Exception) {
        }
    }

    fun onClick(view: View) {
        val checked = snapList().filter {
            it.isChecked
        }.toList()
        checked.forEach {
            if (it.isChecked) {
                fragment?.dispatchDismissToResult(it)
                return@forEach
            }
        }
    }

    fun onInsertClick(view: View) {
        viewModelScope.launch {
            useCase.setBreed(petType.value.orEmpty(), args.type.nonnull(), user?.id.nonnull())
                .success { data ->
                    if (data != null) {
                        fragment?.dispatchDismissToResult(data)
                        Toast.makeText(context, "품종이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "품종등록이 실패되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }.fail {
            }.execute()
        }
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

    fun onBreedTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        petType.value = text.toString()
        executeViewBinding()
    }

    fun getPetType() {
        viewModelScope.launch {
            useCase.getBreedList(petType.value.orEmpty(), args.type.nonnull()).success { data ->
                data?.content?.let { data ->
                    dataSet.clear()
                    val items = mutableListOf<Any>()
                    this.let {
                        items.addAll(data)
                    }
                    if(items.size > 0) {
                        items.forEach {
                            if(it is Breed ) {
                                if(args.breedId == it.id.toString())
                                    it.isChecked = true
                            }
                        }
                    }
                    dataSet.addAll(items)
                }
                adapter.notifyDataSetChanged()
            }.fail {
            }.execute()
        }
    }
}