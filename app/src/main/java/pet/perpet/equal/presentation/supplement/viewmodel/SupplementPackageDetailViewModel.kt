package pet.perpet.equal.presentation.supplement.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentSupplementCodeItem
import pet.perpet.equal.presentation.base.factory.FragmentSupplementItem
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageDetailCodeFragment
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageDetailFragmentArgs
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageFragment
import pet.perpet.framework.fragment.UseViewModel

class SupplementPackageDetailViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        SupplementPackageDetailFragmentArgs.fromBundle(arguments)
    }


    val fragmentAdapter: androidx.fragment.app.FragmentStatePagerAdapter by lazy {
        val f = object :
            androidx.fragment.app.FragmentStatePagerAdapter(fragment.childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        FragmentSupplementItem(
                            args.medical,
                            args.petId.nonnull(),
                            args.name.nonnull(),
                            args.result.nonnull(),
                            SupplementPackageFragment::class.java
                        ).createFragment()
                    }

                    1 -> {
                        FragmentSupplementCodeItem(
                            args.medical,
                            args.petId.nonnull(),
                            args.name.nonnull(),
                            args.result.nonnull(),
                            SupplementPackageDetailCodeFragment::class.java
                        ).createFragment()

                    }

                    else -> {
                        FragmentSupplementItem(
                            args.medical,
                            args.petId.nonnull(),
                            args.name.nonnull(),
                            args.result.nonnull(),
                            SupplementPackageFragment::class.java
                        ).createFragment()
                    }
                }
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when (position) {
                    0 -> getString(R.string.examination_comment15)
                    1 -> getString(R.string.examination_comment16)
                    else -> getString(R.string.examination_comment15)
                }
            }

            override fun getCount(): Int {
                return 2
            }
        }
        f
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }

    }


}