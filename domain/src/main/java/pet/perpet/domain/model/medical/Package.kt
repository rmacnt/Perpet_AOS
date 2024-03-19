package pet.perpet.domain.model.medical

import com.google.gson.annotations.SerializedName
import pet.perpet.data.nonnull
import java.io.Serializable

data class Package(
    @SerializedName("product")
    val product: Product?,
    @SerializedName("rxInfo")
    val rxInfo: RxInfo?
):Serializable , Comparable<Package> {

    override fun compareTo(other: Package): Int {

        if (other.rxInfo?.daily_dosage_mg.nonnull() < rxInfo?.daily_dosage_mg.nonnull()) {
            return 1
        } else if (other.rxInfo?.daily_dosage_mg.nonnull() > rxInfo?.daily_dosage_mg.nonnull()) {
            return -1
        }
        return 0
    }
}