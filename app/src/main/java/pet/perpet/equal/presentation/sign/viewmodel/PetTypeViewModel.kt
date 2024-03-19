package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getContext
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.differ.TypeItemListDiffer
import pet.perpet.equal.presentation.sign.model.PetType
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class PetTypeViewModel (var model: PetType? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = getString(R.string.sign_comment_4)?.let { String.format(it, model?.petName.nonnull()) }

    val typeName: String?
        get() = model?.petTypeName

    val petTypeAsk: Boolean?
        get() = model?.petAsk

    val error: Boolean?
        get() = model?.error

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }


    val itemListDiffer by lazy {
        TypeItemListDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return FlexboxLayoutManager(getContext()).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                    alignItems = AlignItems.CENTER
                }
            }
        }
    }

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    private var notify: ((value: String) -> Unit)? = null

    private var notifyDialog: ((check: Boolean) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun notifyAsk(value: ((check: Boolean) -> Unit)?) {
        notifyAsk = value
    }

    fun onTypeAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun notifyDialog(value: ((check: Boolean) -> Unit)?) {
        notifyDialog = value
    }

    fun onTypeClick(view: View?) {
        notifyDialog?.let { call ->
            call(true)
        }
    }
}