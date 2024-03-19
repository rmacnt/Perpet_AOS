package pet.perpet.equal.support.deeplink.model

import android.net.Uri
import android.util.Log

object ArgumentFactory {

    fun create(url: Uri): BaseArgument {
        return when (url.scheme) {
            "https" -> Argument(url)
            "equal-pet" -> {
                Argument(url)
            }
            else -> Argument(url)
        }
    }

    fun String.parseDeepLinkArgument(): BaseArgument {
        return create(Uri.parse(this))
    }
}