package pet.perpet.equal.presentation.subscribe.viewmodel.delivery

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import pet.perpet.data.nonnull
import pet.perpet.domain.model.tracker.TrackerProgresse
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.getString
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("SimpleDateFormat")
class ItemDeliveryStartOneViewModel(var model: TrackerProgresse? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val formatter = SimpleDateFormat("MM.dd")
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

    val comment: String?
        get() = model?.text +
                if(model?.etc.nonnull().isNotEmpty()) {
                    "\n${model?.etc}"
                }else{
                    ""
                }

    val startText: String?
        get() = if (formatter.format(Calendar.getInstance().time) == dateFormatter.parse(model?.time.nonnull())
                ?.let { formatter.format(it) }
        ) {
            getString(R.string.delivery_comment6)
        } else {
            getString(R.string.delivery_comment5)
        }


    val startColor: Int?
        get() =
            if (formatter.format(Calendar.getInstance().time) == dateFormatter.parse(model?.time.nonnull())
                    ?.let { formatter.format(it) }
            ) {
                getColor(R.color.delivery_today)
            } else {
                getColor(R.color.aaa)
            }

    val startDrawable: Drawable?
        get() =
            if (formatter.format(Calendar.getInstance().time) == dateFormatter.parse(model?.time.nonnull())
                    ?.let { formatter.format(it) }
            ) {
                getDrawable(R.drawable.ic_delivery_on)
            } else {
                getDrawable(R.drawable.ic_delivery_off)
            }


}