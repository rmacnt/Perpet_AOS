package pet.perpet.data.api.request.pet

import com.google.gson.annotations.SerializedName

data class PetProfileRequest (
    @SerializedName("weight") //몸무게
    val weight: Double?,
    @SerializedName("ford") //X 평소에 귀가 접혀있는지 (빈 값/Y/N) (고양이만)
    val ford: String?,
    @SerializedName("neutralization_code") //O 중성화 수술 여부 (code값)
    val neutralization_code: Int?,
    @SerializedName("body_form_code") //O 체형 (code값)
    val body_form_code: Int?,
    @SerializedName("disease_id") //X 질병 고유번호 (,로 묶인 고유번호)
    val disease_id: String?,
    @SerializedName("disease_treat_code")
    val disease_treat_code: Int?, //X 진단받은 질병에 대해 치료하고 있는지 (code값)
    @SerializedName("conditions_code")
    val conditions_code: Int?, //O 최근 아이의 기력에 변화가 있었는지 (code값)
    @SerializedName("appetite_change_code")
    val appetite_change_code: Int?, //O 최근 아이의 식욕에 변화가 있었는지 (code값)
    @SerializedName("feed_amount_code")
    val feed_amount_code: Int?, //	O	사료 배급량 (code값)
    @SerializedName("snack")
    val snack: String?, //X	간식을 먹고 있는지 (빈 값/Y/N)
    @SerializedName("drinking_amount_code")
    val drinking_amount_code: Int?, // O	평소 물을 얼마나 마시는지 (code값)
    @SerializedName("allergy_id")
    val allergy_id: String?, //X	알레르기 고유번호 (,로 묶인 고유번호)
    @SerializedName("how_to_know_allergy_code")
    val how_to_know_allergy_code: Int?, //X	알레르기가 있는지 알게된 경로 (code값)
    @SerializedName("walk_code")
    val walk_code: Int?, //X 하루에 산책을 얼마나 하는지 (code값) (강아지만)
    @SerializedName("relationship_code")
    val relationship_code: Int?, //	X동거묘 혹은 동거견이 있는지 (code값) (고양이만)
    @SerializedName("main_act_place")
    val main_act_place: Int?, // X 주 활동지 직접입력 (강아지만)



)