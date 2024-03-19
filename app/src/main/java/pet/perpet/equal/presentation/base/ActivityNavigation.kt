package pet.perpet.equal.presentation.base

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import pet.perpet.equal.presentation.base.activity.ContainerActivity
import pet.perpet.framework.activity.AppCompatActivity

object ActivityNavigation {

    //======================================================================
    // Variables
    //======================================================================

    const val CONTAINER_FRAGMENT_BUNDLE = "CONTAINER_FRAGMENT_BUNDLE"

    const val CONTAINER_FRAGMENT_CLASS_NAME = "CONTAINER_FRAGMENT_CLASS_NAME"

    //======================================================================
    // Public Methods
    //======================================================================

    @JvmStatic
    fun createContainerIntent(
        context: Context,
        activity: Class<out ContainerActivity>,
        fragment: Class<out Fragment>,
        bundle: Bundle?
    ): Intent {
        return createContainerIntentInternal(
            context,
            activity,
            fragment,
            bundle
        )
    }

    @JvmStatic
    fun createContainerIntent(
        context: Context,
        fragment: Class<out Fragment>,
        bundle: Bundle?
    ): Intent {
        return createContainerIntentInternal(
            context,
            ContainerActivity::class.java,
            fragment,
            bundle
        )
    }

    @JvmStatic
    fun goContainerActivity(context: Context, fragment: Class<out Fragment>) {
        goContainerActivityInternal(
            context,
            fragment,
            null,
            null
        )
    }

    @JvmStatic
    fun goContainerActivity(
        context: Context,
        fragment: Class<out Fragment>,
        bundle: Bundle?
    ) {
        goContainerActivityInternal(
            context,
            fragment,
            bundle,
            null
        )
    }
    @JvmStatic
    fun goContainerActivity(
        context: Context,
        fragment: Class<out Fragment>,
        bundle: Bundle?,
        options: ActivityOptions?
    ) {
        goContainerActivityInternal(
            context,
            fragment,
            bundle,
            options
        )
    }

    @JvmStatic
    fun goContainerActivity(
        context: Context,
        activity: Class<out ContainerActivity>,
        fragment: Class<out Fragment>,
        bundle: Bundle?
    ) {
        goActivityContainerInternal(
            context,
            activity,
            fragment,
            bundle,
            null
        )
    }

    fun startActivity(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

    fun Fragment.startActivity(target: Class<out AppCompatActivity>, bundle: Bundle? = null) {
        val intent = Intent(requireContext(), target)
        bundle?.let {
            intent.putExtras(it)
        }
        startActivity(intent)
    }


    //======================================================================
    // Private Methods
    //======================================================================

    private fun goContainerActivityInternal(
        context: Context,
        fragment: Class<out Fragment>,
        bundle: Bundle?,
        options: ActivityOptions?
    ) {
        goActivityContainerInternal(
            context,
            ContainerActivity::class.java,
            fragment,
            bundle,
            options
        )
    }

    private fun goActivityContainerInternal(
        context: Context?,
        activity: Class<out Activity>,
        fragment: Class<out Fragment>?,
        bundle: Bundle?,
        options: ActivityOptions?
    ) {
        if (context == null) {
            return
        }

        val intent =
            createContainerIntentInternal(
                context,
                activity,
                fragment,
                bundle
            )
        if (options != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(intent, options.toBundle())
            } else {
                context.startActivity(intent)
            }
        } else {
            context.startActivity(intent)
        }
    }

    private fun createContainerIntentInternal(
        context: Context,
        activity: Class<out Activity>,
        fragment: Class<out Fragment>?,
        bundle: Bundle?
    ): Intent {
        val intent = Intent(context, activity)
        if (fragment != null) {
            intent.putExtra(CONTAINER_FRAGMENT_CLASS_NAME, fragment.name)
        }
        intent.putExtra(CONTAINER_FRAGMENT_BUNDLE, bundle)
        return intent
    }
}