package pet.perpet.equal.presentation.paperweight.viewmodel

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.paperweight.fragment.PagerWeightIntroFragmentArgs
import pet.perpet.framework.fragment.UseViewModel

class PagerWeightIntroViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        PagerWeightIntroFragmentArgs.fromBundle(arguments)
    }

    val img: Drawable?
        get() = when(args.type) {
            FragmentItem.id_intro_1-> {
                getDrawable(R.drawable.intro_petcard_1)
            }
            FragmentItem.id_intro_2-> {
                getDrawable(R.drawable.intro_petcard_2)
            }
            FragmentItem.id_intro_3-> {
                getDrawable(R.drawable.intro_petcard_3)
            }
            FragmentItem.id_intro_4-> {
                getDrawable(R.drawable.intro_petcard_4)
            }
            FragmentItem.id_intro_5-> {
                getDrawable(R.drawable.intro_petcard_5)
            }
            else -> {
                getDrawable(R.drawable.intro_petcard_1)
            }
        }
}