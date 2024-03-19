package pet.perpet.equal.presentation.supplement.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.domain.model.medical.Ingredient
import pet.perpet.equal.presentation.supplement.fragment.SupplementComponentFragmentArgs
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageDetailFragmentArgs
import pet.perpet.equal.presentation.supplement.viewholder.ItemSupplementComponentViewHolder
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class SupplementComponentViewModel(fragment: Fragment) : RecyclerViewPresenter(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        SupplementComponentFragmentArgs.fromBundle(arguments)
    }

    val items: ArrayList<Ingredient> = arrayListOf()

    //======================================================================
    // Public Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args.product?.let {
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
                get() = arrayOf(ItemSupplementComponentViewHolder::class.java)

            override fun asViewType(position: Int): Int {
                return ItemSupplementComponentViewHolder::class.java.hashCode()
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