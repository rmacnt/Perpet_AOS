package pet.perpet.equal.support.push.presentation

import android.app.Activity
import android.content.Context
import pet.perpet.equal.MyApplication
import pet.perpet.equal.MyApplication.Companion.mainOpen
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.support.push.BadgeStore
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.model.PushMessage
import pet.perpet.equal.support.push.process.ExternalBrowserProcess
import pet.perpet.equal.support.push.process.InAppBrowserProcess
import pet.perpet.equal.support.push.process.PushAccountDetailProcess
import pet.perpet.equal.support.push.process.PushAdditionalExaminationDetailsProcess
import pet.perpet.equal.support.push.process.PushExaminationDetailsProcess
import pet.perpet.equal.support.push.process.PushExaminationResultDetailProcess
import pet.perpet.equal.support.push.process.PushHomeDetailsProcess
import pet.perpet.equal.support.push.process.PushIntakeDetailsProcess
import pet.perpet.equal.support.push.process.PushMoreDetailsProcess
import pet.perpet.equal.support.push.process.PushNotificationsDetailProcess
import pet.perpet.equal.support.push.process.PushNutrientReportDetailProcess
import pet.perpet.equal.support.push.process.PushOrderListDetailsProcess
import pet.perpet.equal.support.push.process.PushPaymentInfoDetailProcess
import pet.perpet.equal.support.push.process.PushPublicationDetailsProcess
import pet.perpet.equal.support.push.process.PushSearchDetailsProcess
import pet.perpet.equal.support.push.process.PushShippingInfoDetailProcess
import pet.perpet.equal.support.push.process.PushSubscriptionDetailsProcess
import pet.perpet.equal.support.push.update

abstract class PushProcess(val body: MessageBody) {

    internal var preActivitySplash: Boolean = false

    fun process(context: Context) {
        goReMakeChoiceHome(context)
        observable(context)
    }

    abstract fun observable(context: Context)

    fun finish(context: Context) {
        if (context is Activity) {
            context.finish()
        }
    }

    private fun goReMakeChoiceHome(context: Context) {
        context.applicationContext?.run {
            if (this is MyApplication) {
                this
            } else {
                null
            }
        }?.run {

            if (mainOpen == null) {
                context.goHome()
            } else {
                context.goHome()
                finishAll()
            }
            true
        }
    }
}

object PushProcessFactory {

    fun create(body: MessageBody): PushProcess? {
        BadgeStore.update(0)
        return when (body.type) {
            PushMessage.Type.home -> {
                PushHomeDetailsProcess(body)
            }
            PushMessage.Type.search -> {
                PushSearchDetailsProcess(body)
            }
            PushMessage.Type.publication -> {
                PushPublicationDetailsProcess(body)
            }
            PushMessage.Type.examination -> {
                PushExaminationDetailsProcess(body)
            }
            PushMessage.Type.additionalExamination ->{
                PushAdditionalExaminationDetailsProcess(body)
            }
            PushMessage.Type.subscription -> {
                PushSubscriptionDetailsProcess(body)
            }
            PushMessage.Type.more -> {
                PushMoreDetailsProcess(body)
            }
            PushMessage.Type.orderList -> {
                PushOrderListDetailsProcess(body)
            }
            PushMessage.Type.intake -> {
                PushIntakeDetailsProcess(body)
            }
            PushMessage.Type.AppBrowser -> {
                InAppBrowserProcess(body)
            }
            PushMessage.Type.examinationResult -> {
                PushExaminationResultDetailProcess(body)
            }
            PushMessage.Type.shippingInfo -> {
                PushShippingInfoDetailProcess(body)
            }
            PushMessage.Type.paymentInfo -> {
                PushPaymentInfoDetailProcess(body)
            }
            PushMessage.Type.notifications -> {
                PushNotificationsDetailProcess(body)
            }
            PushMessage.Type.account -> {
                PushAccountDetailProcess(body)
            }
            PushMessage.Type.nutrientReport -> {
                PushNutrientReportDetailProcess(body)
            }
            PushMessage.Type.ExternalBrowser -> {
                ExternalBrowserProcess(body)
            }
            else -> null
        }
    }
}