package pet.perpet.equal.support.push.model

import com.google.gson.annotations.SerializedName
import pet.perpet.equal.GsonLoader.gson

data class MessageBody(

    @SerializedName("title")
    val title: String?,
    @SerializedName("body")
    var body: String?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("action")
    var action: String?,
    @SerializedName("imageUrl")
    var imageUrl: String?) {

    var type: PushMessage.Type =
        PushMessage.Type.Invalid

    var message: String? = ""
}