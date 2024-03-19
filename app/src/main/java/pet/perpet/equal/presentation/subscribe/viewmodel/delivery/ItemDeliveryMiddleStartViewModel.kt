package pet.perpet.equal.presentation.subscribe.viewmodel.delivery

import android.annotation.SuppressLint
import pet.perpet.data.nonnull
import pet.perpet.domain.model.tracker.TrackerProgresse
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
class ItemDeliveryMiddleStartViewModel(var model: TrackerProgresse? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val formatter = SimpleDateFormat("MM.dd")
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

    val date: String?
        get() = dateFormatter.parse(model?.time.nonnull())
            ?.let { formatter.format(it) }

    val comment: String?
        get() = model?.text +
                if (model?.etc.nonnull().isNotEmpty()) {
                    "\n${model?.etc}"
                } else {
                    ""
                }
}