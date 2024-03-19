package pet.perpet.equal.presentation.notification.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.equal.R
import pet.perpet.equal.databinding.NotificationListFragmentBinding
import pet.perpet.equal.presentation.notification.viewmodel.NotificationViewModel
import pet.perpet.equal.support.push.BadgeStore
import pet.perpet.equal.support.push.update
import pet.perpet.framework.fragment.v2.AbstractSwipePagerRecyclerViewFragment
import pet.perpet.framework.widget.recyclerview.SwipeRefreshRecyclerView

class NotificationFragment : AbstractSwipePagerRecyclerViewFragment<NotificationViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: NotificationListFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override val recyclerView: SwipeRefreshRecyclerView
        get() = binding.recyclerview

    override val dependencyEmptyView: View?
        get() = binding.tvEmpty

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notification_list_fragment, container, false)
    }

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = NotificationListFragmentBinding.bind(view)
        binding.model = viewmodel
        BadgeStore.update(0)
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.setSwipeRefreshLayoutEnable(true)
    }
}