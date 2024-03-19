package pet.perpet.equal.presentation.health.viewmodel

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.health.fragment.HealthStartPagerFragmentArgs
import pet.perpet.framework.fragment.UseViewModel

class HealthStartPagerViewModel (fragment: Fragment) : UseViewModel(fragment) {

    private val args by lazy {
        HealthStartPagerFragmentArgs.fromBundle(arguments)
    }

    val title: String
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getString(R.string.health_comment3)
            }
            FragmentItem.id_intro_2-> {
                getString(R.string.health_comment5)
            }
            FragmentItem.id_intro_3-> {
                getString(R.string.health_comment7)
            }
            else -> {
                getString(R.string.health_comment3)
            }
        }

    val subTitle: String
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getString(R.string.health_comment11)
            }
            FragmentItem.id_intro_2-> {
                getString(R.string.health_comment6)
            }
            FragmentItem.id_intro_3-> {
                getString(R.string.health_comment8)
            }
            else -> {
                getString(R.string.health_comment4)
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
                getDrawable(R.drawable.slider_img_article)
            }
            else -> {
                getDrawable(R.drawable.slider_img_chart)
            }
        }

}