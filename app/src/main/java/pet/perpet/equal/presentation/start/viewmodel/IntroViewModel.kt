package pet.perpet.equal.presentation.start.viewmodel

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.start.fragment.IntoPagerFragmentArgs
import pet.perpet.framework.fragment.UseViewModel

class IntroViewModel(fragment: Fragment) : UseViewModel(fragment) {


    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        IntoPagerFragmentArgs.fromBundle(arguments)
    }

    val title: String
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getString(R.string.intro_comment1)
            }
            FragmentItem.id_intro_2-> {
                getString(R.string.intro_comment3)
            }
            FragmentItem.id_intro_3-> {
                getString(R.string.intro_comment5)
            }
            else -> {
                getString(R.string.intro_comment1)
            }
        }

    val subTitle: String
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getString(R.string.intro_comment2)
            }
            FragmentItem.id_intro_2-> {
                getString(R.string.intro_comment4)
            }
            FragmentItem.id_intro_3-> {
                getString(R.string.intro_comment6)
            }
            else -> {
                getString(R.string.intro_comment2)
            }
        }

    val img: Drawable?
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getDrawable(R.drawable.tutorial_img1)
            }
            FragmentItem.id_intro_2-> {
                getDrawable(R.drawable.tutorial_img2)
            }
            FragmentItem.id_intro_3-> {
                getDrawable(R.drawable.tutorial_img3)
            }
            else -> {
                getDrawable(R.drawable.tutorial_img1)
            }
        }


}
