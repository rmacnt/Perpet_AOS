package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getContext
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.differ.DiseaseCommentItemListDiffer
import pet.perpet.equal.presentation.sign.differ.DiseaseItemListDiffer
import pet.perpet.equal.presentation.sign.differ.TypeItemListDiffer
import pet.perpet.equal.presentation.sign.model.PetDisease
import pet.perpet.equal.presentation.sign.model.PetType
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class PetDiseaseViewModel (var model: PetDisease? = null) {

    //======================================================================
    // Variables
    //======================================================================

    var nowSelect: Int? = -1


    val petDisease: Int?
        get() = model?.petDisease

    val error: Boolean?
        get() = model?.error

    val itemDiseaseListDiffer by lazy {
        DiseaseItemListDiffer()
    }

    val diseaseMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemDiseaseListDiffer.adapter
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

    val itemDiseaseCommentListDiffer by lazy {
        DiseaseCommentItemListDiffer()
    }

    val diseaseCommentMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemDiseaseCommentListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }
    val petDiseaseAsk: Boolean?
        get() = model?.petAsk

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    private var notify: ((value: String) -> Unit)? = null

    private var notifyDialog: ((check: Boolean) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }
    fun notifyAsk(value: ((value: Boolean) -> Unit)?) {
        notifyAsk = value
    }



    fun notifyDialog(value: ((check: Boolean) -> Unit)?) {
        notifyDialog = value
    }

    fun onDiseaseAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onDiseaseClick(view: View?) {
        notify?.let { call->
            if(nowSelect != view?.tag.toString().toInt()) {
                nowSelect = view?.tag.toString().toInt()
                call(view?.tag.toString())
            }

        }
    }
}