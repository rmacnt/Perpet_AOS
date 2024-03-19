package pet.perpet.equal.presentation.base.ui

import android.app.AlertDialog
import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import pet.perpet.equal.R

class AppAlertDialog (
    private val context: Context,
    private val title: String? = null,
    private val msg: String? = null,
    private val positiveText: String? = null,
    private val negativeText: String? = null,
) {
    private lateinit var positiveListener: (AlertDialog) -> Unit
    private lateinit var negativeListener: (AlertDialog) -> Unit


    private val dialog: AlertDialog.Builder by lazy {
        if (positiveText.isNullOrEmpty().not() && positiveText.isNullOrEmpty().not()) {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveText) { _, _ ->
                    if (::positiveListener.isInitialized) positiveListener(dialog.create())
                }
                .setNegativeButton(negativeText) { _, _ ->
                    if (::negativeListener.isInitialized) negativeListener(dialog.create())
                }.setNeutralButton("") { _, _ ->

                }
        } else if (positiveText.isNullOrEmpty().not() && negativeText.isNullOrEmpty()) {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveText) { _, _ ->
                    if (::positiveListener.isInitialized) positiveListener(dialog.create())
                }
                .setNeutralButton("") { _, _ ->

                }
        } else if (positiveText.isNullOrEmpty() && negativeText.isNullOrEmpty().not()) {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(positiveText) { _, _ ->
                    if (::negativeListener.isInitialized) negativeListener(dialog.create())
                }.setNeutralButton("") { _, _ ->

                }
        } else {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
        }
    }

    fun show(onClickNegative: (AlertDialog) -> Unit = {}, onClickPositive: (AlertDialog) -> Unit = {}) {
        this.negativeListener = onClickNegative
        this.positiveListener = onClickPositive
        dialog.show().apply {
            /**
             * Title TextView
             */
            findViewById<TextView>(androidx.appcompat.R.id.alertTitle)?.apply {
                typeface = ResourcesCompat.getFont(context, R.font.gmarket_sans_medium)
                setTextColor(context.getColor(R.color.black))
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
            }


            /**
             * Message TextView
             */
            findViewById<TextView>(android.R.id.message)?.apply {
                typeface = ResourcesCompat.getFont(context, R.font.gmarket_sans_medium)
                setTextColor(context.getColor(R.color.black))
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
            }

            /**
             * Positive Button
             */
            findViewById<TextView>(android.R.id.button1)?.apply {
                typeface = ResourcesCompat.getFont(context, R.font.gmarket_sans_medium)
                setTextColor(context.getColor(R.color.black))
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
            }
            /**
             * Negative Button
             */
            findViewById<TextView>(android.R.id.button2)?.apply {
                typeface = ResourcesCompat.getFont(context, R.font.gmarket_sans_medium)
                setTextColor(context.getColor(R.color.text_hint_color))
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
            }
        }
    }
}