package pet.perpet.equal

import pet.perpet.framework.util.HashUtil

object AppConfig {

    //======================================================================
    // Variables
    //======================================================================


    val appName: String
        get() = "perpat"

    @JvmStatic
    val LOG_ENABLE: Boolean
        get() = true


    @JvmStatic
    val API_DOMAIN: String
        get() = ""

    @JvmStatic
    val VERSION_CODE = BuildConfig.VERSION_CODE

    @JvmStatic
    val VERSION_NAME = BuildConfig.VERSION_NAME

    //======================================================================
    // Private Variables
    //======================================================================

    private val property = Property()


    private const val isProd = true

    //======================================================================
    // Private Methods
    //======================================================================

    private fun key(): String {
        return HashUtil.checkSignature(MyApplication.application?.packageName)
    }

    //======================================================================
    // Property
    //======================================================================

    internal data class Property(
        var choiceDomain: String? = null,
        var choiceModuleFlavor: String? = null
    )
}