package pet.perpet.equal.presentation.search.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.domain.model.search.Ingredient
import pet.perpet.equal.presentation.search.fragment.SearchSupplementComponentFragmentArgs
import pet.perpet.equal.presentation.search.viewholder.ItemSearchSupplementComponentViewHolder
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class SearchSupplementComponentViewModel (fragment: Fragment) : RecyclerViewPresenter(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        SearchSupplementComponentFragmentArgs.fromBundle(arguments)
    }

    val items: ArrayList<Ingredient> = arrayListOf()

    //======================================================================
    // Public Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args.searchProduct?.let {
            it.ingredient?.forEach {
                items.add(it)
            }
        }
    }

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }

    }

    override fun onCreateViewHolderSet(): ViewHolderSet? {
        return object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(ItemSearchSupplementComponentViewHolder::class.java)

            override fun asViewType(position: Int): Int {
                return ItemSearchSupplementComponentViewHolder::class.java.hashCode()
            }

        }
    }

    override fun onCreateProvider(): BaseRecyclerViewAdapter.Provider {
        return object : BaseRecyclerViewAdapter.Provider {
            override fun getItemHeaderCount(): Int {
                return 0
            }

            override fun getSupportItemCount(): Int {
                return items.size
            }

            override fun getSupportItemViewType(position: Int): Int {
                return viewHolderSet.asViewType(position)
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return items[position]
            }
        }
    }

}