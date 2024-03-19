package pet.perpet.domain

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("SimpleDateFormat")
fun String?.asDate(format: String = "yyyy-MM-dd'T'HH:mm", asFormat: String? = null): Date? {
    try {
        if (!asFormat.isNullOrEmpty()) {
            return SimpleDateFormat(format).parse(this.orEmpty())?.let {
                SimpleDateFormat(asFormat).format(it).let { result ->
                    SimpleDateFormat(asFormat).parse(result)
                }
            }
        }
        return SimpleDateFormat(format).parse(this.orEmpty())
    } catch (e: java.lang.Exception) {
    }
    return null
}