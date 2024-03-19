package pet.perpet.equal.presentation.intakecheck.viewmodel

import android.graphics.drawable.Drawable
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.intake.IntakeCare
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.framework.util.Logger

class ItemCareViewModel(var model: IntakeCare? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.text

    val imageResult: Boolean?
        get() = when (model?.check.nonnull()) {
            true -> {
                true
            }
            false -> {
                model?.today.nonnull()
            }
        }

    val checkDrawable: Drawable?
        get() = when (model?.check.nonnull()) {
            true -> {
                if (model?.today.nonnull()) {
                    getDrawable(R.drawable.ic_intake_on)
                } else {
                    getDrawable(R.drawable.ic_intake_before_on)
                }
            }

            false -> {
                getDrawable(R.drawable.ic_intake_off)

            }
        }

    val todayColor
            : Int
    ?
        get() = if (model?.today.nonnull()) {
            getColor(R.color.black)
        } else {
            getColor(R.color.text_hint_color)
        }


    private var notify
            : ((
        value
        : IntakeCare,
    )
    -> Unit
    )
    ? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: IntakeCare) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("ItemCareViewModel", "onClick > ${model}")
        model?.let {
            notify?.let { call ->
                call(it)
            }

        }
    }
}