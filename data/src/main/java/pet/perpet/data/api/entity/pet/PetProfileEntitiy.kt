package pet.perpet.data.api.entity.pet

import com.google.gson.annotations.SerializedName

data class PetProfileEntitiy (
    @SerializedName("allergy")
    val allergy: String?,
    @SerializedName("allergy_id")
    val allergy_id: String?,
    @SerializedName("appetite_change")
    val appetite_change: String?,
    @SerializedName("body_form")
    val body_form: String?,
    @SerializedName("conditions")
    val conditions: String?,
    @SerializedName("disease")
    val disease: String?,
    @SerializedName("disease_id")
    val disease_id: String?,
    @SerializedName("disease_treat")
    val disease_treat: String?,
    @SerializedName("drinking_amount")
    val drinking_amount: String?,
    @SerializedName("feed_amount")
    val feed_amount: String?,
    @SerializedName("ford")
    val ford: Any?,
    @SerializedName("how_to_know_allergy")
    val how_to_know_allergy: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("main_act_place")
    val main_act_place: String?,
    @SerializedName("neutralization")
    val neutralization: String?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("relationship")
    val relationship: String?,
    @SerializedName("snack")
    val snack: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("walk")
    val walk: String?,
    @SerializedName("weight")
    val weight: String?
)