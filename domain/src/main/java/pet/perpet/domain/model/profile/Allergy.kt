package pet.perpet.domain.model.profile

import android.os.Parcelable
import android.widget.Checkable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Allergy(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("is_candidate")
    val is_candidate: Boolean?,
    @SerializedName("is_expired")
    val is_expired: Boolean?,
    @SerializedName("is_main")
    val is_main: Boolean?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?
) : Checkable , Parcelable {
    //======================================================================
    // Variables
    //======================================================================

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
