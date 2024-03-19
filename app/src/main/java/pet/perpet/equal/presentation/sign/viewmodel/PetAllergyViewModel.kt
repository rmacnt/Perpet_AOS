package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import pet.perpet.data.nonnull
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getContext
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.differ.SearchFlexItemTwoListDiffer
import pet.perpet.equal.presentation.sign.model.PetAllergy
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class PetAllergyViewModel (var model: PetAllergy? = null) {

    //======================================================================
    // Variables
    //======================================================================

    var dataSetting: ArrayList<Allergy> = ArrayList()

    val petAllegi: Int
        get() = model?.petAllegi.nonnull()

    val petAllegiAsk: Boolean?
        get() = model?.petAsk

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val error: Boolean?
        get() = model?.error

    val itemResultListDiffer by lazy {
        SearchFlexItemTwoListDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemResultListDiffer.adapter
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


    private var notify: ((value: String) -> Unit)? = null

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

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

    fun notifyDialog(value: ((check: Boolean) -> Unit)?) {
        notifyDialog = value
    }

    fun onAllegiAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onAllegiClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }

    fun onSearchClick(view: View?) {
        notifyDialog?.let { call ->
            call(true)
        }
    }
}