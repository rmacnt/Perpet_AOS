package pet.perpet.equal.support.deeplink.model

import android.net.Uri
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import pet.perpet.data.nonnull
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.logger.w

@Parcelize
class Argument(override val uri: Uri) : BaseArgument(uri) {

    //======================================================================
    // Variables
    //======================================================================

    @IgnoredOnParcel
    override val type: Type by lazy {
        val path = uri.path.orEmpty()
        val scheme = uri.scheme.orEmpty()
        val host = uri.host.orEmpty()
        val query = uri.query.orEmpty()

        val isEqualRoot = when {
            host == "equal-pet" && (path.isEmpty() || path == "/") -> {
                true
            }

            else -> false
        }

        val t = if (navigation.nonnull()) {
            when (scene) {
                "home" -> Type.Home
                "search" -> Type.Search
                "publication" -> Type.Publication
                "examination" -> Type.Examination
                "additionalExamination" -> Type.AdditionalExamination
                "subscription" -> Type.Subscription
                "more" -> Type.More
                "intake" -> Type.Intake
                "orderList" -> Type.OrderList
                "examinationResult" -> Type.ExaminationResult
                "shippingInfo" -> Type.ShippingInfo
                "paymentInfo" -> Type.PaymentInfo
                "notifications" -> Type.Notifications
                "account" -> Type.Account
                "nutrientReport" -> Type.NutrientReport

                else -> Type.Invalid
            }
        } else {
            Type.Invalid
        }
        AppLogger.Tag.DEEP_LINK.w("Argument2 > scheme : $scheme")
        AppLogger.Tag.DEEP_LINK.w("Argument2 > host : $host")
        AppLogger.Tag.DEEP_LINK.w("Argument2 > path : $path")
        AppLogger.Tag.DEEP_LINK.w("Argument2 > query : $query")
        AppLogger.Tag.DEEP_LINK.w("Argument2 > isEqualRoot : $isEqualRoot")
        AppLogger.Tag.DEEP_LINK.w("Argument2 > type : $t")
        AppLogger.Tag.DEEP_LINK.w("Argument2 > scene : $scene")

        t
    }

    override val scene: String
        get() {
            return uri.safetyQueryParameter("scene")
        }

    override val petId: String
        get() = uri.safetyQueryParameter("petId")

    override val keyword: String
        get() = uri.safetyQueryParameter("keyword")

    override val subscriptionId: String
        get() = uri.safetyQueryParameter("subscriptionId")

    val navigation: Boolean?
        get() = uri.host?.contains("navigation")

    override val contentId: String
        get() = uri.safetyQueryParameter("contentId")

    override val examinationId: String
        get() = uri.safetyQueryParameter("examinationId")


    //======================================================================
    // Private Methods
    //======================================================================

    private fun String.isRegMatch(reg: String): Boolean {
        return this.matches(Regex(reg))
    }
}