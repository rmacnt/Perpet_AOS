package pet.perpet.equal.support.deeplink.process

import android.app.Activity
import android.content.Context
import pet.perpet.equal.support.deeplink.model.BaseArgument
import pet.perpet.equal.support.deeplink.model.InvalidTarget
import pet.perpet.equal.support.deeplink.model.Type
import pet.perpet.equal.support.logger.AppLogger

abstract class DeepLinkProcess(val body: BaseArgument) {

    fun process(context: Context) {
        body.log()
        AppLogger.i(AppLogger.Tag.DEEP_LINK, "next observable process > ${body.type.name} ")
        observable(context)
    }

    abstract fun observable(context: Context)

    fun finish(context: Context) {
        if (context is Activity) {
            context.finish()
        }
    }
}

object DeepLinkProcessFactory {

    fun create(
        body: BaseArgument,
        invalidTarget: InvalidTarget = InvalidTarget.NotProgress
    ): DeepLinkProcess? {
        return when (body.type) {
            Type.Home -> HomeDetailsProcess(body)
            Type.Search -> SearchDetailsProcess(body)
            Type.Publication -> PublicationDetailsProcess(body)
            Type.Examination -> ExaminationDetailsProcess(body)
            Type.AdditionalExamination -> AdditionalExaminationDetailsProcess(body)
            Type.Subscription -> SubscriptionDetailsProcess(body)
            Type.More -> MoreDetailsProcess(body)
            Type.Intake -> IntakeDetailsProcess(body)
            Type.OrderList -> OrderListDetailsProcess(body)
            Type.ExaminationResult -> ExaminationResultDetailProcess(body)
            Type.ShippingInfo -> ShippingInfoDetailProcess(body)
            Type.PaymentInfo -> PaymentInfoDetailProcess(body)
            Type.Notifications -> NotificationsDetailProcess(body)
            Type.Account -> AccountDetailProcess(body)
            Type.NutrientReport -> NutrientReportDetailProcess(body)
            Type.Invalid -> {
                if (invalidTarget == InvalidTarget.InAppBrowser) {
                    if (body.isHttpUrlMatch) {
                        InAppBrowserProcess(body)
                    } else {
                        return null
                    }
                } else {
                    return null
                }
            }
        }
    }
}