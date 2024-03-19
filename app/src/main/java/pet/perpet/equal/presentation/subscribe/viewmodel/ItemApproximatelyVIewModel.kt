package pet.perpet.equal.presentation.subscribe.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import pet.perpet.data.nonnull
import pet.perpet.domain.model.product.Product
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import java.text.DecimalFormat

class ItemApproximatelyVIewModel(var model: Product? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = model?.name_kor + " (30개입)"

    val price: String?
        get() = String.format(
            getString(R.string.product_price).nonnull(),
            DecimalFormat("#,###").format(
                kotlin.math.floor((model?.count_per_unit.nonnull() * 30).toDouble()).toInt()
            )
        )

    val coverThumbnailUrl: String?
        get() = model?.image.orEmpty()

    val description: String?
        get() = model?.description

    val count: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice: MutableLiveData<Int> = MutableLiveData(0)

    val countCall: String?
        get() = count.value.toString()

    val totalPriceCall: String?
        get() = String.format(
            getString(R.string.price).nonnull(),
            DecimalFormat("#,###").format(totalPrice.value)
        )

    private var notify: ((product: Product) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((product: Product) -> Unit)?) {
        notify = value
    }


    fun onMinusClick(view: View?) {
        notify?.let { call ->
            model?.let {
                count.value = count.value?.minus(1)
                it.setCount(count.value.nonnull())
                if (count.value == 0) {
                    totalPrice.value = 0
                } else {
                    totalPrice.value =
                        (kotlin.math.floor((it.count_per_unit.nonnull() * 30).toDouble())
                            .toInt()) * count.value.nonnull()

                }
                call(it)
            }
        }
    }

    fun onPulsClick(view: View?) {
        notify?.let { call ->
            model?.let {

                count.value = count.value?.plus(1)
                it.setCount(count.value.nonnull())
                totalPrice.value =
                    (kotlin.math.floor((it.count_per_unit.nonnull() * 30).toDouble())
                        .toInt()) * count.value.nonnull()
                call(it)
            }
        }
    }

}