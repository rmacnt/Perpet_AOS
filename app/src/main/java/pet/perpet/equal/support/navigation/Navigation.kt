package pet.perpet.equal.support.navigation

import android.content.Context
import android.os.Bundle
import pet.perpet.domain.model.address.Juso
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.card.CardList
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.domain.model.profile.Breed
import pet.perpet.domain.model.profile.Disease
import pet.perpet.equal.presentation.asAppCompatActivity
import pet.perpet.equal.presentation.examination.fragment.BottomCodeCommentFragment
import pet.perpet.equal.presentation.examination.fragment.BottomCommentFragment
import pet.perpet.equal.presentation.examination.fragment.BottomCommentFragmentArgs
import pet.perpet.equal.presentation.home.fragment.BottomFeedFragment
import pet.perpet.equal.presentation.home.fragment.BottomFeedFragmentArgs
import pet.perpet.equal.presentation.intakecheck.fragment.BottomAlarmFragment
import pet.perpet.equal.presentation.intakecheck.fragment.BottomAlarmFragmentArgs
import pet.perpet.equal.presentation.intakecheck.fragment.BottomHealthFragment
import pet.perpet.equal.presentation.intakecheck.fragment.BottomSubscribeFragment
import pet.perpet.equal.presentation.intakecheck.model.IntakeAlarm
import pet.perpet.equal.presentation.more.fragment.BottomGptFragment
import pet.perpet.equal.presentation.more.fragment.BottomInfoFragment
import pet.perpet.equal.presentation.more.fragment.BottomInfoFragmentArgs
import pet.perpet.equal.presentation.more.fragment.MoreWebFragment
import pet.perpet.equal.presentation.more.fragment.MoreWebFragmentArgs
import pet.perpet.equal.presentation.more.fragment.payment.BottomPaymentTermFragment
import pet.perpet.equal.presentation.more.fragment.shipping.BottomAddressSearchFragment
import pet.perpet.equal.presentation.search.fragment.SearchContentFragment
import pet.perpet.equal.presentation.search.fragment.SearchContentFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchFaqFragment
import pet.perpet.equal.presentation.search.fragment.SearchFaqFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchSupplementFragment
import pet.perpet.equal.presentation.search.fragment.SearchSupplementFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchTagFragment
import pet.perpet.equal.presentation.search.fragment.SearchTagFragmentArgs
import pet.perpet.equal.presentation.sign.fragment.BottomAllergySearchFragment
import pet.perpet.equal.presentation.sign.fragment.BottomAllergySearchFragmentArgs
import pet.perpet.equal.presentation.sign.fragment.BottomBirthFragment
import pet.perpet.equal.presentation.sign.fragment.BottomBirthFragmentArgs
import pet.perpet.equal.presentation.sign.fragment.BottomBreedSearchFragment
import pet.perpet.equal.presentation.sign.fragment.BottomBreedSearchFragmentArgs
import pet.perpet.equal.presentation.sign.fragment.BottomDiseaseSearchFragment
import pet.perpet.equal.presentation.sign.fragment.BottomDiseaseSearchFragmentArgs
import pet.perpet.equal.presentation.sign.model.PetDisease
import pet.perpet.equal.presentation.start.fragment.PolicyAlertFragment
import pet.perpet.equal.presentation.start.fragment.PolicyInfoAlertFragment
import pet.perpet.equal.presentation.start.fragment.PolicyInfoAlertFragmentArgs
import pet.perpet.equal.presentation.start.model.Policy
import pet.perpet.equal.presentation.subscribe.fragment.BottomAddressSettingFragment
import pet.perpet.equal.presentation.subscribe.fragment.BottomCardSettingFragment
import pet.perpet.framework.fragment.DialogFragmentNavigation

fun Context.showSignPolicy(callback: (Policy) -> Unit) {
    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<Policy>(
            fragmentManager,
            PolicyAlertFragment::class.java,
            Bundle()
        ) {
            it?.let(callback)
        }
    }
}

fun Context.showPolicyInfo(title: String, url: String) {
    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            PolicyInfoAlertFragment::class.java,
            PolicyInfoAlertFragmentArgs
                .Builder()
                .setTitle(title)
                .setUrl(url)
                .build()
                .toBundle()
        )
    }
}

fun Context.showPaymentPolicy(callback: (Boolean) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<Boolean>(
            fragmentManager,
            BottomPaymentTermFragment::class.java,
            Bundle()
        ) {
            it?.let(callback)
        }
    }
}

fun Context.showComment(name: String) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            BottomCommentFragment::class.java,
            BottomCommentFragmentArgs
                .Builder()
                .setName(name)
                .build()
                .toBundle()
        )
    }
}

fun Context.showCommentCode() {
    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            BottomCodeCommentFragment::class.java,
            Bundle()
        )
    }
}

fun Context.showBirth(year: String, month: String, callback: (String) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<String>(
            fragmentManager,
            BottomBirthFragment::class.java,
            BottomBirthFragmentArgs
                .Builder()
                .setYear(year)
                .setMonth(month)
                .build()
                .toBundle()
        ) {
            it?.let(callback)
        }
    }
}

fun Context.showBreedSearch(type: String, breedId : String ,callback: (Breed) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<Breed>(
            fragmentManager,
            BottomBreedSearchFragment::class.java,
            BottomBreedSearchFragmentArgs
                .Builder()
                .setType(type)
                .setBreedId(breedId)
                .build()
                .toBundle()
        ) {
            it?.let(callback)
        }
    }
}

fun Context.showAllergySearch(list: ArrayList<Allergy>, callback: (ArrayList<Allergy>) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<ArrayList<Allergy>>(
            fragmentManager,
            BottomAllergySearchFragment::class.java,
            BottomAllergySearchFragmentArgs
                .Builder()
                .setItem(list.toTypedArray())
                .build()
                .toBundle()
        ) {
            it?.let(callback)
        }
    }
}

fun Context.showAddressSearch(callback: (Juso) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<Juso>(
            fragmentManager,
            BottomAddressSearchFragment::class.java,
            Bundle()
        ) {
            it?.let(callback)
        }
    }
}

fun Context.showAddressSetting( callback: (ListAddress) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<ListAddress>(
            fragmentManager,
            BottomAddressSettingFragment::class.java,
            Bundle()
        ) {
            it?.let(callback)
        }
    }
}

fun Context.showPaymentSetting( callback: (CardList) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<CardList>(
            fragmentManager,
            BottomCardSettingFragment::class.java,
            Bundle()
        ) {
            it?.let(callback)
        }
    }
}


fun Context.showFeed(targetId: Int) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            BottomFeedFragment::class.java,
            BottomFeedFragmentArgs
                .Builder()
                .setTargetId(targetId.toString())
                .build()
                .toBundle()
        )
    }
}


fun Context.showGptAlert() {
    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            BottomGptFragment::class.java,
            Bundle()
        )
    }
}

fun Context.showWeb(url: String) {
    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            MoreWebFragment::class.java,
            MoreWebFragmentArgs
                .Builder()
                .setUrl(url)
                .build()
                .toBundle()
        )
    }
}

fun Context.showCardOrAddressOrderInfo(type: String) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            BottomInfoFragment::class.java,
            BottomInfoFragmentArgs
                .Builder()
                .setType(type)
                .build()
                .toBundle()
        )
    }
}

fun Context.showPetList() {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            BottomHealthFragment::class.java,
            Bundle()
        )
    }
}

fun Context.showSubscribeList() {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment(
            fragmentManager,
            BottomSubscribeFragment::class.java,
            Bundle()
        )
    }
}


fun Context.showAlarm( intake : IntakeAlarm? , callback: (IntakeAlarm) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<IntakeAlarm>(
            fragmentManager,
            BottomAlarmFragment::class.java,
            BottomAlarmFragmentArgs
                .Builder()
                .setItem(intake)
                .build()
                .toBundle()
        ){
            it?.let(callback)
        }
    }
}

fun Context.showDiseaseSearch(list: ArrayList<Disease>?, diseaseId: String , callback: (ArrayList<Disease>) -> Unit) {

    this.asAppCompatActivity()?.supportFragmentManager?.let { fragmentManager ->
        DialogFragmentNavigation.showDialogFragment3<ArrayList<Disease>>(
            fragmentManager,
            BottomDiseaseSearchFragment::class.java,
            BottomDiseaseSearchFragmentArgs
                .Builder()
                .setItem(list?.toTypedArray())
                .setDiseaseId(diseaseId)
                .build()
                .toBundle()
        ) {
            it?.let(callback)
        }
    }
}

fun SearchContentFragmentArgs.toFragment(): SearchContentFragment {
    return SearchContentFragment().apply {
        arguments = this@toFragment.toBundle()
    }
}

fun SearchSupplementFragmentArgs.toFragment(): SearchSupplementFragment {
    return SearchSupplementFragment().apply {
        arguments = this@toFragment.toBundle()
    }
}

fun SearchFaqFragmentArgs.toFragment(): SearchFaqFragment {
    return SearchFaqFragment().apply {
        arguments = this@toFragment.toBundle()
    }
}

fun SearchTagFragmentArgs.toFragment(): SearchTagFragment {
    return SearchTagFragment().apply {
        arguments = this@toFragment.toBundle()
    }
}



