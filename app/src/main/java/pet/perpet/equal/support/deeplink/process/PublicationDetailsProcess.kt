package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.search.SearchDetailArticleUseCase
import pet.perpet.equal.presentation.goSearchDetail
import pet.perpet.equal.support.deeplink.model.BaseArgument

class PublicationDetailsProcess(body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        SearchDetailArticleUseCase().parameter2 {
            this.id = body.contentId.toInt()
            this.userId = UserStore.user?.id.nonnull().toString()
        }.success {
            it?.let {
                context.goSearchDetail(it , "https://equal.pet/content/ViewApp/${it.id}")
                finish(context)
            }
        }.fail {
            finish(context)
        }.execute()
    }
}