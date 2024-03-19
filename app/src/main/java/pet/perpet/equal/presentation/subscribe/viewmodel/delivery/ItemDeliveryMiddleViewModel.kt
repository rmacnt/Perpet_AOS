package pet.perpet.equal.presentation.subscribe.viewmodel.delivery

import pet.perpet.data.nonnull
import pet.perpet.domain.model.tracker.TrackerProgresse

class ItemDeliveryMiddleViewModel(var model: TrackerProgresse? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val comment: String?
        get() = model?.text +
                if (model?.etc.nonnull().isNotEmpty()) {
                    "\n${model?.etc}"
                } else {
                    ""
                }
}