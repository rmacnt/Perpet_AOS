package pet.perpet.equal.presentation.notification.viewmodel

import android.util.Log
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.notification.PushList
import pet.perpet.domain.usecase.notification.PushReadUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.support.push.navigation.Navigation

class NotificationNoticeViewModel(var model: PushList? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.tn.orEmpty()

    val comment: String?
        get() = model?.cn.orEmpty()

    val date: String?
        get() = model?.toDiff().orEmpty()

    val type: String?
        get() =  when (model?.gbn) {
            "signUpComplete" -> "회원 가입 완료"
            "petRegistration" -> "첫 반려 동물 등록"
            "questionnaire" -> "신규 문진 수행"
            "questionnaireComplete" -> "문진 완료"
            "addQuestionnaire" -> "추가 문진 수행"
            "ordersSuccess" -> "구독 신청 완료"
            "paySuccess" -> "결제 완료"
            "payCancel" -> "결제 취소"
            "newArticle" -> "신규 게시물"
            "event" -> "이벤트"
            "notice" -> "공지사항"
            "unknown" -> ""
            else -> ""
        }


    val dateColor: Int?
        get() = when(model?.checked_yn) {
            "Y" -> {
                getColor(R.color.text_aaa)
            }
            else->{
                getColor(R.color.push_date_none_color)
            }

        }

    val contentColor: Int?
        get() = when(model?.checked_yn) {
            "Y" -> {
                getColor(R.color.text_aaa)
            }
            else->{
                getColor(R.color.black)
            }

        }

    //======================================================================
    // Private Variables
    //======================================================================

    private var navigation: Navigation? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun bind(model: PushList?) {
        this.model = model
        navigation = model?.let { Navigation.Factory.create(it) }
    }


    fun onDetailClick(view: View) {
        PushReadUseCase().parameter2 {
            this.id = model?.id.nonnull()
        }.success {
            navigation?.process(view.context)
        }.execute()
    }
}