package pet.perpet.equal.support.push.process

import android.content.Context
import android.net.Uri
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.search.SearchDetailArticleUseCase
import pet.perpet.equal.presentation.goSearchDetail
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushPublicationDetailsProcess(body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {
        val contentId = Uri.parse(body.link.orEmpty()).getQueryParameter("contentId")
        SearchDetailArticleUseCase().parameter2 {
            this.id = contentId.nonnull().toInt()
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