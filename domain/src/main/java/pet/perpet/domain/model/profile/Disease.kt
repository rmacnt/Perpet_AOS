package pet.perpet.domain.model.profile

import android.os.Parcelable
import android.widget.Checkable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Disease (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("main_ctgr_id")
    val main_ctgr_id: Int?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("_main")
    val _main: Boolean?,
    @SerializedName("_candidate")
    val _candidate: Boolean?,
    @SerializedName("_expired")
    val _expired: Boolean?,
    var comment: String?,
    var dataSetting: ArrayList<Disease>  = ArrayList()
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