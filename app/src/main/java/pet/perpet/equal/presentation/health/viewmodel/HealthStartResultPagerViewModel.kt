package pet.perpet.equal.presentation.health.viewmodel

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.health.fragment.HealthStartPagerFragmentArgs
import pet.perpet.framework.fragment.UseViewModel

class HealthStartResultPagerViewModel (fragment: Fragment) : UseViewModel(fragment) {

    private val args by lazy {
        HealthStartPagerFragmentArgs.fromBundle(arguments)
    }

    val title: String
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getString(R.string.health_comment3)
            }
            FragmentItem.id_intro_2-> {
                getString(R.string.health_comment17)
            }
            FragmentItem.id_intro_3-> {
                getString(R.string.health_comment19)
            }
            else -> {
                getString(R.string.health_comment3)
            }
        }

    val subTitle: String
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getString(R.string.health_comment23)
            }
            FragmentItem.id_intro_2-> {
                getString(R.string.health_comment24)
            }
            FragmentItem.id_intro_3-> {
                getString(R.string.health_comment25)
            }
            else -> {
                getString(R.string.health_comment23)
            }
        }

    val img: Drawable?
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getDrawable(R.drawable.slider_img_chart)
            }
            FragmentItem.id_intro_2-> {
                getDrawable(R.drawable.slider_img_package)
            }
            FragmentItem.id_intro_3-> {
                getDrawable(R.drawable.slider_img_gpt)
            }
            else -> {
                getDrawable(R.drawable.slider_img_chart)
            }
        }

}