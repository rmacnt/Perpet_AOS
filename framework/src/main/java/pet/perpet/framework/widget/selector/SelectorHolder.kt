package pet.perpet.framework.widget.selector

import android.widget.Checkable

interface SelectorHolder : Checkable {

    val id: Int

    var activated: Boolean

    val ignore : Boolean

    fun onChecked(isChecked: Boolean)
}