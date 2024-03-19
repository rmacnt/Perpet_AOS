package pet.perpet.equal.presentation.search.viewmodel

import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.search.SearchSimple
import pet.perpet.domain.usecase.search.SearchDetailArticleUseCase
import pet.perpet.domain.usecase.search.SearchDetailProductUseCase
import pet.perpet.equal.presentation.goSearchDetail
import pet.perpet.equal.presentation.goSearchProductDetail

class SearchSimpleViewModel (var model: SearchSimple? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = model?.text

    val type: String?
        get() = when(model?.type) {
            "article"-> "콘텐츠"
            "product"-> "영양제 정보"
            "faq"-> "FAQ"
            "tag"-> "태그"
            else -> ""
        }

    private var notify: ((value: String) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun onClick(view: View) {
        when(model?.type) {
            "faq","article" -> {
                SearchDetailArticleUseCase().parameter2 {
                    this.id = model?.contents_id.nonnull()
                    this.userId = UserStore.user?.id.nonnull().toString()
                }.success {
                    it?.let {
                        view.context.goSearchDetail(it , "https://equal.pet/content/ViewApp/${it.id}")
                    }
                }.fail {
                }.execute()

            }
            "product" -> {
                SearchDetailProductUseCase().parameter2 {
                    this.id = model?.contents_id.nonnull()
                }.success {
                    it?.let {
                        view.context.goSearchProductDetail(it)
                    }
                }.fail {
                }.execute()
            }

            "tag" -> {
                notify?.let {call ->
                    call(model?.text.nonnull())
                }
            }

        }
    }
}