package pet.perpet.equal.support.deeplink

import android.content.Intent
import android.net.Uri
import pet.perpet.equal.support.deeplink.model.BaseArgument
import pet.perpet.equal.support.logger.AppLogger

object DeepLink {

    //======================================================================
    // Variables
    //======================================================================

    private const val KEY_DEEP_LINK_BUNDLE = "KEY_DEEP_LINK_BUNDLE"

    //======================================================================
    // Public Methods
    //======================================================================

    fun init() {

    }

    @JvmStatic
    fun saveParameter(intent: Intent, parameter: BaseArgument) {
        intent.putExtra(KEY_DEEP_LINK_BUNDLE, parameter)
    }

    @JvmStatic
    fun getParameter(intent: Intent?): BaseArgument? {
        if (intent != null) {
            return intent.getParcelableExtra(KEY_DEEP_LINK_BUNDLE)
        }
        return null
    }

    fun Uri.createUriDeepLinkIntent(): Intent {
        val intent = Intent(Intent.ACTION_VIEW, this)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return intent
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun logD(message: String) {
        AppLogger.d(AppLogger.Tag.DEEP_LINK.toString(), message)
    }

    private fun logE(message: String) {
        AppLogger.e(AppLogger.Tag.DEEP_LINK.toString(), message)
    }

}