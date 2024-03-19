package pet.perpet.equal.presentation.subscribe.viewmodel

import pet.perpet.domain.model.product.Product
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.framework.nonnull
import java.text.DecimalFormat

class PackageItemViewModel(var model: Product? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = model?.name_kor

    val set: String?
        get() = String.format(
            getString(R.string.set).nonnull(),
            model?.procutCount
        )

    val price: String?
        get() = String.format(
            getString(R.string.price).nonnull(),
            DecimalFormat("#,###").format(
                (kotlin.math.floor((model?.price_per_count).nonnull())
                    .toInt() * model?.procutCount.nonnull())
            )
        )
}