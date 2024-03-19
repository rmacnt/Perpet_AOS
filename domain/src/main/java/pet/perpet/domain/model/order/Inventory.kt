package pet.perpet.domain.model.order

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import pet.perpet.domain.asDate
import java.text.SimpleDateFormat

data class Inventory(
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("delivery_id")
    val delivery_id: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Any?,
    @SerializedName("order_id")
    val order_id: Int?,
    @SerializedName("pay_method")
    val pay_method: String?,
    @SerializedName("payment_dt")
    val payment_dt: String?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("pet_name")
    val pet_name: String?,
    @SerializedName("update_date")
    val update_date: Any?,
    @SerializedName("update_user")
    val update_user: Any?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("address")
    val address: OrderAddress?,
    @SerializedName("cards")
    val cards: OrderCards?,
    @SerializedName("delivery")
    val delivery: Delivery?,
    @SerializedName("medical_id")
    val medical_id: Int?,
    @SerializedName("order_number")
    val order_number: String?,
    @SerializedName("pay_id")
    val pay_id: Any?,
    @SerializedName("products")
    val products: OrderAddProduct?,
    @SerializedName("shipping_fee")
    val shipping_fee: Double?,
    @SerializedName("status")
    val status: String?

) {

    @SuppressLint("SimpleDateFormat")
    fun toDiff(): String {
        val dst = this.insert_date?.asDate()
        if (dst != null) {
            val formatter = SimpleDateFormat("yyyy. MM. dd HH:mm")


            return  formatter.format(dst)
        }
        return ""
    }
}