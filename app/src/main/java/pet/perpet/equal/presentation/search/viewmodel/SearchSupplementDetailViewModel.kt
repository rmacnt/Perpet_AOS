package pet.perpet.equal.presentation.search.viewmodel

import android.os.Bundle
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import de.stocard.markdown_to_spanned.Markdown
import pet.perpet.data.nonnull
import pet.perpet.equal.presentation.goSearchSupplementComponent
import pet.perpet.equal.presentation.search.fragment.SearchSupplementDetailFragmentArgs
import pet.perpet.equal.presentation.search.viewholder.ItemCodeViewHolder
import pet.perpet.equal.presentation.search.viewholder.ItemTagViewHolder
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import java.util.Collections

class SearchSupplementDetailViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        SearchSupplementDetailFragmentArgs.fromBundle(arguments)
    }

    val name: String?
        get() = args.searchProduct?.name_kor

    val image: String?
        get() = args.searchProduct?.image

    val summary: String?
        get() = args.searchProduct?.summary

    val description: String?
        get() = args.searchProduct?.description?.replace(" ", "\u00A0")

    val caution: Spanned?
        get() = Markdown.fromMarkdown(args.searchProduct?.caution?.trim())

    val storagePrecautions: Spanned?
        get() = Markdown.fromMarkdown(args.searchProduct?.storage_precautions?.trim())

    val omega: Boolean?
        get() = args.searchProduct?.name_kor.nonnull().contains("이퀄 오메가3")

    val cautionAsk: MutableLiveData<Boolean> = MutableLiveData(false)
    val storagePrecautionsAsk: MutableLiveData<Boolean> = MutableLiveData(false)

    val adapter: BaseRecyclerViewAdapter by lazy {
        val a = object : BaseRecyclerViewAdapter(provider) {
            override fun onCreateItemViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerViewHolder<*>? {
                return ItemCodeViewHolder(parent)
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

    val tagAdapter: BaseRecyclerViewAdapter by lazy {
        val a = object : BaseRecyclerViewAdapter(tagProvider) {
            override fun onCreateItemViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerViewHolder<*>? {
                return ItemTagViewHolder(parent)
            }
        }
        a.setHasStableIds(true)
        a
    }

    val tagProvider: BaseRecyclerViewAdapter.ArgumentsProvider by lazy {
        object : BaseRecyclerViewAdapter.ArgumentsProvider {
            override fun getItemHeaderCount(): Int = 0

            override fun getSupportItemCount(): Int {
                return tagDataSet.size.nonnull()
            }

            override fun getSupportItemViewType(position: Int): Int = -1

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return tagDataSet[position]
            }

            override fun onBindArguments(viewType: Int, arguments: Bundle?) {
            }
        }
    }


    private val dataSet = mutableListOf<Any>()
    private val tagDataSet = mutableListOf<Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args.searchProduct?.ingredient?.let { data->
            val items = mutableListOf<Any>()
            this.let {
                items.addAll(data)
            }
            Collections.sort(items, Collections.reverseOrder())
            dataSet.addAll(items)
        }

        args.searchProduct?.tags?.let { data->
            val items = mutableListOf<Any>()
            this.let {
                items.addAll(data)
            }
            tagDataSet.addAll(items)
        }
    }


    fun onCodeDetailClick(view: View) {
        activity?.let {activity->
            args.searchProduct?.let {
                activity.goSearchSupplementComponent(it)
            }
        }
    }

    fun onHomeClick(view: View) {
        activity?.let {
            it.finish()
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


}