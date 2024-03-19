package pet.perpet.equal.presentation.examination.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentHealthItem
import pet.perpet.equal.presentation.examination.fragment.ExaminationHealthResultDetailFragment
import pet.perpet.equal.presentation.examination.fragment.ExaminationResultDetailFragmentArgs
import pet.perpet.equal.presentation.goSupplementPackage
import pet.perpet.framework.fragment.UseViewModel

class ExaminationResultDetailViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        ExaminationResultDetailFragmentArgs.fromBundle(arguments)
    }

    val btnName: String?
        get() = String.format(getString(R.string.health_comment21), args.name)

    val subscribeResult: Boolean?
        get() = args.result


    val fragmentAdapter: androidx.fragment.app.FragmentStatePagerAdapter by lazy {
        val f = object :
            androidx.fragment.app.FragmentStatePagerAdapter(fragment.childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return FragmentHealthItem(
                    args.medical,
                    args.medical?.results?.get(position),
                    args.petId.nonnull(),
                    args.name.nonnull(),
                    ExaminationHealthResultDetailFragment::class.java
                ).createFragment()
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return args.medical?.results?.get(position)?.diagnosis?.name_kor
            }

            override fun getCount(): Int {
                return args.medical?.results?.size.nonnull()
            }
        }
        f
    }


    //======================================================================
    // Public Methods
    //======================================================================

    fun onSubmitClick(view: View) {
        activity?.let { activity ->
            args.medical?.let {
                activity.goSupplementPackage(it, args.petId.nonnull(), args.name.nonnull() ,  args.result)
            }
        }
    }

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }

    }

}