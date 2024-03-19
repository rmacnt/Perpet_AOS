package pet.perpet.domain.model.address

import android.widget.Checkable
import com.google.gson.annotations.SerializedName
import pet.perpet.domain.model.card.OrdersInfo
import java.io.Serializable

data class ListAddress(
    @SerializedName("address")
    val address: String,
    @SerializedName("address_detail")
    val address_detail: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("insert_date")
    val insert_date: String,
    @SerializedName("main_yn")
    val main_yn: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("recipient")
    val recipient: String,
    @SerializedName("update_date")
    val update_date: String,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("zipcode")
    val zipcode: String,
    @SerializedName("memo")
    val memo: String?,
    @SerializedName("ordersInfo")
    val ordersInfo: ArrayList<OrdersInfo>?,
): Checkable , Serializable {

    //======================================================================
    // Variables
    //======================================================================

    private var check = false

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
