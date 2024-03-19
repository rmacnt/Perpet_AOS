package pet.perpet.equal.presentation.examination.viewmodel

import android.content.Context
import pet.perpet.domain.model.medical.Package
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.framework.nonnull
import java.text.DecimalFormat

open class PackageListItemViewModel (var model: Package? = null) {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var context: (() -> Context)

    val name: String?
        get() = model?.product?.name_kor

    val count: String?
        get() = getString(R.string.subscribe_comment53)?.let { String.format(it,DecimalFormat("#,###").format((model?.rxInfo?.daily_dosage_mg.nonnull() ) )) }

}