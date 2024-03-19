package pet.perpet.equal.support

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import pet.perpet.data.nonnull
import pet.perpet.domain.TokenStore
import pet.perpet.equal.support.logger.AppLogger
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset

class AppAuthInterceptor : Interceptor {

    //======================================================================
    // Override Methods
    //======================================================================

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request().newBuilder().applyHeaders()
                .method(chain.request().method, chain.request().body)
                .build()
            Log.w(
                "MESSAGE",
                "intercept > url : ${request} "
            )
            return chain.proceed(request)
        } catch (e: java.lang.Exception) {
            AppLogger.printStackTrace(e)
        }
        return chain.proceed(chain.request())
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun Request.Builder.applyHeaders(): Request.Builder {
        val accessToken = TokenStore.accessToken?.nonnull()
        val userAgent = "Android"

        if(accessToken.isNullOrEmpty().not())
            this.header("Authorization", String.format("Bearer %s", accessToken))

        this.header("Content-Type", "application/json")
        this.header("User-Agent", userAgent)
        return this
    }

    //======================================================================
    // Companion
    //======================================================================

    companion object {

        private val UTF8 = Charset.forName("UTF-8")

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) buffer.size else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false // Truncated UTF-8 sequence.
            }

        }
    }
}
