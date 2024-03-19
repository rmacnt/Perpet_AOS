package pet.perpet.framework.fragment.v2

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter

abstract class AbstractListFragment<P : AbstractListPresenter> : Fragment<P>(){

    //======================================================================
    // Variables
    //======================================================================

    protected abstract val recyclerView: RecyclerView

    //======================================================================
    // Public Methods
    //======================================================================

    val layoutManager: RecyclerView.LayoutManager?
        get() = recyclerView.layoutManager

    val adapter: BaseRecyclerViewAdapter
        get() {
            return recyclerView.adapter as BaseRecyclerViewAdapter
        }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.onAttachRecyclerView(recyclerView)
    }
}
