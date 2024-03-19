package pet.perpet.equal.support.push.navigation

import android.content.Context
import pet.perpet.domain.model.notification.PushList
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.logger.w
import pet.perpet.equal.support.push.process.PushHomeDetailsProcess

abstract class Navigation(val args: PushList) {

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun isValid(args: PushList): Boolean

    protected abstract fun go(context: Context)

    //======================================================================
    // Public Methods
    //======================================================================

    fun process(context: Context) {
        val type = args.toType
        val valid = isValid(args)
        "Navigation > type : ${type}, valid : $valid".log()
        if (valid == true) {
            go(context)
        } else {
            "not working".log()
        }
    }

    //======================================================================
    // Protected Methods
    //======================================================================

    protected fun Int?.isValid(): Boolean {
        return this != null && this > 0
    }

    //======================================================================
    // Private Methods
    //======================================================================

    fun String.log() {
        AppLogger.Tag.HOLDER.w("${this@Navigation::class.java.simpleName} => ${this}")
    }

    //======================================================================
    // companion
    //======================================================================

    object Factory {

        fun create(value: PushList): Navigation? {
            return value.toNavigation()
        }

        private fun PushList.toNavigation(): Navigation? {
            if (link.isNullOrEmpty().not()) {
                return DeeplinkNavigation(this)
            }
            return when (this.toType) {
                PushList.Type.home -> HomeNavigation(this)
                PushList.Type.search -> SearchNavigation(this)
                PushList.Type.publication -> PublicationNavigation(this)
                PushList.Type.examination -> ExaminationNavigation(this)
                PushList.Type.additionalExamination -> AdditionalExaminationNavigation(this)
                PushList.Type.subscription -> SubscriptionNavigation(this)
                PushList.Type.more -> MoreNavigation(this)
                PushList.Type.orderList -> OrderListNavigation(this)
                PushList.Type.intake -> IntakeNavigation(this)
                PushList.Type.examinationResult -> ExaminationResultNavigation(this)
                PushList.Type.shippingInfo -> ShippingInfoNavigation(this)
                PushList.Type.paymentInfo -> PaymentInfoNavigation(this)
                PushList.Type.account -> AccountNavigation(this)
                PushList.Type.nutrientReport -> NutrientReportNavigation(this)
                PushList.Type.AppBrowser -> InAppBrowserNavigation(this)
                PushList.Type.ExternalBrowser -> ExternalBrowserNavigation(this)

                else -> null
            }
        }
    }
}