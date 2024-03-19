package pet.perpet.framework.activity

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

fun Window.overviewStatus(light: Boolean = false) {
    val window = this
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }

    if (light) {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    } else {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}


fun Window.setStatusBarColor(color: Int, light: Boolean = false) {
    val window = this
    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }*/

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
        /*window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE*/
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
    if (light) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    } else {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

}

fun Window.setNavigationColor(color: Int, light: Boolean = false) {
    val window = this
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.navigationBarColor = color

        if (light) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }
}

fun Window.setSystemUI(
    statusBarColor: Int = Color.TRANSPARENT,
    navigationBarColor: Int = Color.TRANSPARENT,
    overlay: Boolean = false,
    light: Boolean = true
) {
    if (overlay == true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            this.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            this.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        this.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }*/

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
        this.statusBarColor = statusBarColor
        this.navigationBarColor = navigationBarColor
    }

    if (light) {
        this.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    } else {
        this.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}