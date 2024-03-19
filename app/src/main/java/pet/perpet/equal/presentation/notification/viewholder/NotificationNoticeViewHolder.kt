package pet.perpet.equal.presentation.notification.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.notification.PushList
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemNotificationBinding
import pet.perpet.equal.presentation.notification.viewmodel.NotificationNoticeViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class NotificationNoticeViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PushList>(viewGroup, R.layout.item_notification) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemNotificationBinding = ItemNotificationBinding.bind(itemView)

    val viewmodel: NotificationNoticeViewModel = NotificationNoticeViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PushList?) {
        super.onRefresh(t)
        viewmodel.bind(t)
        binding.model = viewmodel
    }
}