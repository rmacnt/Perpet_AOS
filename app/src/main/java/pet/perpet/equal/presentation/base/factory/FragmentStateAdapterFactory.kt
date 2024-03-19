package pet.perpet.equal.presentation.base.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.model.medical.Result
import pet.perpet.domain.model.pet.Pet
import pet.perpet.equal.presentation.examination.fragment.ExaminationHealthResultDetailFragmentArgs
import pet.perpet.equal.presentation.more.fragment.MoreItemCardFragmentArgs
import pet.perpet.equal.presentation.start.fragment.IntoPagerFragmentArgs
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageDetailCodeFragmentArgs
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageFragmentArgs

object FragmentStateAdapterFactory {

    //======================================================================
    // Public Methods
    //======================================================================

    fun Fragment.createAdapter(
        list: ArrayList<FragmentItem>,
        channelId: String,
        liveId: String,
        borderType: String = "",
        showFocusBar: Boolean = false
    ): FragmentStatePagerAdapter {
        return object : FragmentStatePagerAdapter(
            this.childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int {
                return list.size
            }

            override fun getItem(position: Int): Fragment {
                return list[position].createFragment()
            }
        }
    }
}

//======================================================================
// FragmentItem
//======================================================================

data class FragmentItem(val id: Int, val fragment: Class<out Fragment>) {

    fun createFragment(): Fragment {
        val f = fragment.newInstance()
        f.arguments =  IntoPagerFragmentArgs
            .Builder()
            .setType(id)
            .build()
            .toBundle()

        return f
    }

    companion object {
        const val id_intro_1 = 0
        const val id_intro_2 = 1
        const val id_intro_3 = 2
        const val id_intro_4 = 3
        const val id_intro_5 = 4
    }
}

data class FragmentMoreItem(val pet: Pet, val fragment: Class<out Fragment>) {

    fun createFragment(): Fragment {
        val f = fragment.newInstance()
        f.arguments =  MoreItemCardFragmentArgs
            .Builder()
            .setPet(pet)
            .build()
            .toBundle()

        return f
    }

    companion object {
        const val id_intro_1 = 0
        const val id_intro_2 = 1
        const val id_intro_3 = 2
        const val id_intro_4 = 3
        const val id_intro_5 = 4
    }
}

data class FragmentHealthItem(val medical: Medical?, val result: Result?,val petId: String , val name: String , val fragment: Class<out Fragment>) {

    fun createFragment(): Fragment {
        val f = fragment.newInstance()
        f.arguments =  ExaminationHealthResultDetailFragmentArgs
            .Builder()
            .setMedical(medical)
            .setName(name)
            .setPetId(petId)
            .setResult(result)
            .build()
            .toBundle()

        return f
    }
}

data class FragmentSupplementItem(val medical: Medical?,val petId: String , val name: String ,val result: Boolean ,  val fragment: Class<out Fragment>) {

    fun createFragment(): Fragment {
        val f = fragment.newInstance()
        f.arguments =  SupplementPackageFragmentArgs
            .Builder()
            .setName(name)
            .setPetId(petId)
            .setMedical(medical)
            .setResult(result)
            .build()
            .toBundle()

        return f
    }
}
data class FragmentSupplementCodeItem(val medical: Medical?,val petId: String , val name: String , val result: Boolean, val fragment: Class<out Fragment>) {

    fun createFragment(): Fragment {
        val f = fragment.newInstance()
        f.arguments =  SupplementPackageDetailCodeFragmentArgs
            .Builder()
            .setName(name)
            .setPetId(petId)
            .setMedical(medical)
            .setResult(result)
            .build()
            .toBundle()

        return f
    }
}