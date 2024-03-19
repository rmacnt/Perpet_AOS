package pet.perpet.domain.model.card

import android.annotation.SuppressLint
import android.widget.Checkable
import com.google.gson.annotations.SerializedName
import pet.perpet.domain.asDate
import java.io.Serializable
import java.text.SimpleDateFormat

data class CardList(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("card_nickname")
    val card_nickname: String?,
    @SerializedName("card_number")
    val card_number: String?,
    @SerializedName("card_name")
    val card_name: String?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("ordersInfo")
    val ordersInfo: ArrayList<OrdersInfo>?
): Checkable , Serializable {

    //======================================================================
    // Variables
    //======================================================================

    @SuppressLint("SimpleDateFormat")
    fun toDiff(): String {
        val dst = this.insert_date?.asDate()
        if (dst != null) {
            val formatter = SimpleDateFormat("yyyy. MM. dd")


            return  formatter.format(dst)
        }
        return ""
    }

    private var check = false

    private var position = 0

    override fun setChecked(checked: Boolean) {
        check = checked
    }

    override fun isChecked(): Boolean {
        return check
    }

    override fun toggle() {
        isChecked = !isChecked
    }

}