package pet.perpet.equal.support.deeplink

import android.content.Context
import android.content.Intent
import pet.perpet.equal.support.deeplink.model.BaseArgument

fun Context.goDeepLinkProcess(deep: BaseArgument) {
    val i = Intent(this, DeepLinkProcessActivity::class.java)
    deep.log()

    DeepLink.saveParameter(i, deep)
    this.startActivity(i)
}

