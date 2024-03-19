package pet.perpet.equal.support.push.navigation

import android.content.Context
import android.net.Uri
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.notification.PushList
import pet.perpet.domain.usecase.search.SearchDetailArticleUseCase
import pet.perpet.equal.presentation.goSearchDetail

class PublicationNavigation (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {
        val contentId = Uri.parse(args.link.orEmpty()).getQueryParameter("contentId")
        SearchDetailArticleUseCase().parameter2 {
            this.id = contentId.nonnull().toInt()
            this.userId = UserStore.user?.id.nonnull().toString()
        }.success {
            it?.let {
                context.goSearchDetail(it , "https://equal.pet/content/ViewApp/${it.id}")
            }
        }.fail {
        }.execute()
    }
}