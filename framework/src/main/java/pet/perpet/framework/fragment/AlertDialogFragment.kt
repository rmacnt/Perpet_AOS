package pet.perpet.framework.fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import pet.perpet.framework.R

abstract class AlertDialogFragment<P : UseViewModel> : DialogFragment<P>() {

    //======================================================================
    // Private Methods
    //======================================================================

    protected var contentView: View? = null
        private set

    val config: Config = Config()

    //======================================================================
    // Abstract Methods
    //======================================================================

    protected abstract fun onDialogContentView(): View?

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: AlertDialog
        dialog = InnerAppAlertDialog(requireActivity())

        val config = config
        val title = config.getTitle(requireContext())
        if (StringUtil.isEmpty(title) == false) {
            dialog.setTitle(title)
        }

        contentView = onDialogContentView()
        val view = contentView
        if (view != null) {
            val padding = getDialogPreferredPadding(view.context)
            view.setPadding(padding, padding, padding, padding)
            dialog.setView(view)

            onViewCreated(view, savedInstanceState)
        }
        if (config.isNegativeButtonUse == true) {
            dialog.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                resources.getString(config.negativeButtonString),
                null as DialogInterface.OnClickListener?
            )
        }
        if (config.isPositiveButtonUse == true) {
            dialog.setButton(
                DialogInterface.BUTTON_POSITIVE,
                resources.getString(config.positiveButtonString),
                null as DialogInterface.OnClickListener?
            )
        }
        return dialog
    }

    //======================================================================
    // Protected Methods
    //======================================================================

    protected open fun onClickNegativeButton() {
        // Override
    }

    protected open fun onClickPositiveButton() {
        // Override
    }

    //======================================================================
    // Private Methods
    //======================================================================

    open fun getDialogPreferredPadding(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.dialogPreferredPadding, value, true)
        return context.resources.getDimensionPixelSize(value.resourceId)
    }

    //======================================================================
    // Config
    //======================================================================

    class Config {

        var isPositiveButtonUse = true

        var isNegativeButtonUse = true

        @StringRes
        var positiveButtonString = android.R.string.ok

        @StringRes
        var negativeButtonString = android.R.string.cancel

        var isCancelable = true

        var title: String? = null

        @StringRes
        var titleRes: Int = 0

        fun getTitle(context: Context): String? {
            if (titleRes != 0) {
                return context.getString(titleRes)
            }
            return title
        }
    }

    //======================================================================
    // InnerAppAlertDialog
    //======================================================================

    internal inner class InnerAppAlertDialog(context: Context) : AlertDialog(context) {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (config.isNegativeButtonUse == true) {
                getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener { onClickNegativeButton() }
            }
            if (config.isPositiveButtonUse == true) {
                getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { onClickPositiveButton() }
            }
        }
    }
}