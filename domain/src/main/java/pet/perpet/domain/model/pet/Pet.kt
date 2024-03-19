package pet.perpet.domain.model.pet

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pet (
        @SerializedName("age")
        val age: String?,
        @SerializedName("breed")
        val breed: String?,
        @SerializedName("breeds_id")
        val breeds_id: Int?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("has_bookmark")
        val has_bookmark: Boolean?,
        @SerializedName("has_medical")
        val has_medical: Boolean?,
        @SerializedName("has_subscription")
        val has_subscription: Boolean? = false,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: ArrayList<String>?,
        @SerializedName("insert_date")
        val insert_date: String?,
        @SerializedName("insert_user")
        val insert_user: Int?,
        @SerializedName("main_yn")
        val main_yn: String?,
        @SerializedName("next_medical_date")
        val next_medical_date: String?,
        @SerializedName("next_pay_date")
        val next_pay_date: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("profile")
        val profile: PetProfile?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("latest_order_id")
        val latest_order_id: Int?,
        @SerializedName("latest_medical_id")
        val latest_medical_id: Int?,
        @SerializedName("update_date")
        val update_date: String?,
        @SerializedName("update_user")
        val update_user: Int?,
        @SerializedName("use_yn")
        val use_yn: String?,
        @SerializedName("user_id")
        val user_id: Int?
): Serializable