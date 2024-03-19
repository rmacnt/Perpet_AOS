package pet.perpet.equal.presentation

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.bookmark.Bookmark
import pet.perpet.domain.model.main.MainCard
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.model.product.Product
import pet.perpet.domain.model.search.SearchArticle
import pet.perpet.domain.model.search.SearchProduct
import pet.perpet.equal.MyApplication
import pet.perpet.equal.presentation.base.ActivityNavigation
import pet.perpet.equal.presentation.bookmark.fragment.BookmarkDetailFragment
import pet.perpet.equal.presentation.bookmark.fragment.BookmarkDetailFragmentArgs
import pet.perpet.equal.presentation.bookmark.fragment.BookmarkFragment
import pet.perpet.equal.presentation.bookmark.fragment.BookmarkFragmentArgs
import pet.perpet.equal.presentation.examination.fragment.ExaminationResultDetailFragment
import pet.perpet.equal.presentation.examination.fragment.ExaminationResultDetailFragmentArgs
import pet.perpet.equal.presentation.examination.fragment.ExaminationResultFragment
import pet.perpet.equal.presentation.examination.fragment.ExaminationResultFragmentArgs
import pet.perpet.equal.presentation.health.fragment.HealthInfoFragment
import pet.perpet.equal.presentation.health.fragment.HealthInfoResultFragment
import pet.perpet.equal.presentation.health.fragment.HealthStartFragment
import pet.perpet.equal.presentation.home.fragment.HomeDetailFragment
import pet.perpet.equal.presentation.home.fragment.HomeDetailFragmentArgs
import pet.perpet.equal.presentation.home.fragment.HomeFragment
import pet.perpet.equal.presentation.intakecheck.fragment.IntakeCheckAlarmListFragment
import pet.perpet.equal.presentation.intakecheck.fragment.IntakeCheckFragment
import pet.perpet.equal.presentation.more.fragment.AlramFragment
import pet.perpet.equal.presentation.more.fragment.CustomerCenterFragment
import pet.perpet.equal.presentation.more.fragment.MoreFragment
import pet.perpet.equal.presentation.more.fragment.MoreWebFragment
import pet.perpet.equal.presentation.more.fragment.MoreWebFragmentArgs
import pet.perpet.equal.presentation.more.fragment.OrderDetailFragment
import pet.perpet.equal.presentation.more.fragment.OrderDetailFragmentArgs
import pet.perpet.equal.presentation.more.fragment.OrderHistoryFragment
import pet.perpet.equal.presentation.more.fragment.SettingFragment
import pet.perpet.equal.presentation.more.fragment.payment.PaymentInsertFragment
import pet.perpet.equal.presentation.more.fragment.payment.PaymentManagementFragment
import pet.perpet.equal.presentation.more.fragment.payment.PaymentMethodSelectFragment
import pet.perpet.equal.presentation.more.fragment.shipping.ShippingAddressRegistrationFragment
import pet.perpet.equal.presentation.more.fragment.shipping.ShippingAddressRegistrationFragmentArgs
import pet.perpet.equal.presentation.more.fragment.shipping.ShippingManagementFragment
import pet.perpet.equal.presentation.mypage.fragment.MyPageFragment
import pet.perpet.equal.presentation.mypage.fragment.NickNameEditFragment
import pet.perpet.equal.presentation.notification.fragment.NotificationFragment
import pet.perpet.equal.presentation.paperweight.fragment.PaperWeightStartFragment
import pet.perpet.equal.presentation.paperweight.fragment.PaperWeightStartFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchDetailFragment
import pet.perpet.equal.presentation.search.fragment.SearchDetailFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchFragment
import pet.perpet.equal.presentation.search.fragment.SearchFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchSupplementComponentFragment
import pet.perpet.equal.presentation.search.fragment.SearchSupplementComponentFragmentArgs
import pet.perpet.equal.presentation.search.fragment.SearchSupplementDetailFragment
import pet.perpet.equal.presentation.search.fragment.SearchSupplementDetailFragmentArgs
import pet.perpet.equal.presentation.setting.fragment.CompanyInfoFragment
import pet.perpet.equal.presentation.setting.fragment.InfoWebFragment
import pet.perpet.equal.presentation.setting.fragment.InfoWebFragmentArgs
import pet.perpet.equal.presentation.setting.fragment.TermsAndConditionSettingFragment
import pet.perpet.equal.presentation.sign.fragment.SignFragment
import pet.perpet.equal.presentation.start.StartLoginActivity
import pet.perpet.equal.presentation.subscribe.fragment.DeliveryInformationFragment
import pet.perpet.equal.presentation.subscribe.fragment.DeliveryInformationFragmentArgs
import pet.perpet.equal.presentation.subscribe.fragment.SubscribeDetailFragment
import pet.perpet.equal.presentation.subscribe.fragment.SubscribeDetailFragmentArgs
import pet.perpet.equal.presentation.subscribe.fragment.SubscribeFragment
import pet.perpet.equal.presentation.subscribe.fragment.SubscribeFragmentArgs
import pet.perpet.equal.presentation.subscribe.fragment.SubscriptionDetailFragment
import pet.perpet.equal.presentation.subscribe.fragment.SubscriptionDetailFragmentArgs
import pet.perpet.equal.presentation.subscribe.fragment.SubscriptionOrderApplicationFragment
import pet.perpet.equal.presentation.subscribe.fragment.SubscriptionOrderApplicationFragmentArgs
import pet.perpet.equal.presentation.supplement.fragment.SupplementComponentFragment
import pet.perpet.equal.presentation.supplement.fragment.SupplementComponentFragmentArgs
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageDetailFragment
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageDetailFragmentArgs
import pet.perpet.equal.presentation.web.fragment.WebViewFragment
import pet.perpet.equal.support.deeplink.DeepLink
import pet.perpet.equal.support.deeplink.model.BaseArgument
import pet.perpet.domain.model.medical.Product as medicalProduct

fun createStartActivityIntent(context: Context): Intent {
    return Intent(context, StartLoginActivity::class.java)
}

fun Context.finishAll() {
    this.applicationContext?.run {
        if (this is MyApplication) {
            this
        } else {
            null
        }
    }?.run {
        finishAll()
    }
}


fun Context.goSign(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, SignFragment::class.java
    )
}

fun goStart(context: Context, deepLink: BaseArgument? = null) {
    val intent = Intent(context, StartLoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    deepLink?.let { DeepLink.saveParameter(intent, it) }
    context.startActivity(intent)
}

fun Context.goHome(singleTop: Boolean = false) {
    ActivityNavigation.createContainerIntent(
        this,
        HomeFragment::class.java,
        null
    ).run {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(this)
    }
}

fun Context.goNickNameEdit(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, NickNameEditFragment::class.java
    )
}

fun Context.goHeathInfo(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, HealthInfoFragment::class.java
    )
}

fun Context.goHeathWeb(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, WebViewFragment::class.java
    )
}

fun Context.goWeb(url: String) {
    ActivityNavigation.goContainerActivity(
        this, MoreWebFragment::class.java,
        MoreWebFragmentArgs.Builder().setUrl(url).build().toBundle()
    )
}

fun Context.goHeathStart(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, HealthStartFragment::class.java
    )
}


fun Context.goPaymentSelect(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, PaymentMethodSelectFragment::class.java
    )
}

fun Context.goPaymentInsert(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, PaymentInsertFragment::class.java
    )
}


fun Context.goPagerWeight(newUser: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, PaperWeightStartFragment::class.java,
        PaperWeightStartFragmentArgs.Builder().setUser(newUser).build().toBundle()
    )
}

fun Context.goHealthResult(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, HealthInfoResultFragment::class.java
    )
}

fun Context.goHealthResultDetail(medical: Medical, petId: String, name: String, result: Boolean) {
    ActivityNavigation.goContainerActivity(
        this, ExaminationResultDetailFragment::class.java,
        ExaminationResultDetailFragmentArgs.Builder().setMedical(medical).setResult(result)
            .setPetId(petId).setName(name).build().toBundle()
    )
}

fun Context.goSupplementPackage(
    medical: Medical,
    petId: String,
    name: String,
    result: Boolean = false,
) {
    ActivityNavigation.goContainerActivity(
        this, SupplementPackageDetailFragment::class.java,
        SupplementPackageDetailFragmentArgs.Builder().setMedical(medical).setPetId(petId)
            .setName(name).setResult(result).build().toBundle()
    )
}

fun Context.getSubscribeDetail(orderId: String, name: String) {
    ActivityNavigation.goContainerActivity(
        this, SubscriptionDetailFragment::class.java,
        SubscriptionDetailFragmentArgs.Builder().setId(orderId).setName(name).build().toBundle()
    )
}


fun Context.goMore(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, MoreFragment::class.java
    )
}

fun Context.goSearch(keyword: String? = "" , type: Int = 0) {
    ActivityNavigation.goContainerActivity(
        this, SearchFragment::class.java,
        SearchFragmentArgs.Builder().setKeyword(keyword).setType(type).build()
            .toBundle()
    )
}

fun Context.goMypage(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, MyPageFragment::class.java
    )
}

fun Context.goSetting(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, SettingFragment::class.java
    )
}

fun Context.goSettingAlram(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, AlramFragment::class.java
    )
}

fun Context.goPayment(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, PaymentManagementFragment::class.java
    )
}

fun Context.goShippingManagement(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, ShippingManagementFragment::class.java
    )
}

fun Context.goOrderHistory(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, OrderHistoryFragment::class.java
    )
}


fun Context.goShippingInsert(singleTop: Boolean = false) {
    ActivityNavigation.goContainerActivity(
        this, ShippingAddressRegistrationFragment::class.java
    )
}

fun Context.goShippingEdit(address: ListAddress) {
    ActivityNavigation.goContainerActivity(
        this, ShippingAddressRegistrationFragment::class.java,
        ShippingAddressRegistrationFragmentArgs.Builder().setAddress(address).build().toBundle()
    )
}


fun Context.goSubscriptionOrderApplication(medical: Medical, name: String, petId: String) {
    ActivityNavigation.goContainerActivity(
        this,
        SubscriptionOrderApplicationFragment::class.java,
        SubscriptionOrderApplicationFragmentArgs.Builder().setMedical(medical).setPetId(petId)
            .setName(name).build()
            .toBundle()
    )
}

fun Context.goExaminationResult(name: String, id: String, petId: String, result: Boolean) {
    ActivityNavigation.goContainerActivity(
        this, ExaminationResultFragment::class.java,
        ExaminationResultFragmentArgs.Builder().setName(name).setId(id).setPetId(petId)
            .setResult(result).build().toBundle()
    )
}


fun Context.goSubscribe(medical: Medical, product: List<Product>?, petId: String, petName: String) {

    product?.let {
        ActivityNavigation.goContainerActivity(
            this, SubscribeFragment::class.java,
            SubscribeFragmentArgs.Builder()
                .setMedical(medical)
                .setProducts(it.toTypedArray())
                .setName(petName)
                .build().toBundle()
        )
    } ?: run {
        ActivityNavigation.goContainerActivity(
            this, SubscribeFragment::class.java,
            SubscribeFragmentArgs.Builder().setMedical(medical).setPetId(petId).setName(petName)
                .build().toBundle()
        )
    }
}

fun Context.goSubscribeDetail(id: String) {
    ActivityNavigation.goContainerActivity(
        this, SubscribeDetailFragment::class.java,
        SubscribeDetailFragmentArgs.Builder().setId(id).build().toBundle()
    )
}


fun Context.goBookmark(petId: String) {
    ActivityNavigation.goContainerActivity(
        this, BookmarkFragment::class.java,
        BookmarkFragmentArgs.Builder().setPetId(petId).build().toBundle()
    )
}

fun Context.goIntakeCheck() {
    ActivityNavigation.goContainerActivity(
        this, IntakeCheckFragment::class.java
    )
}

fun Context.goOrderDetail(orderId: String) {
    ActivityNavigation.goContainerActivity(
        this, OrderDetailFragment::class.java,
        OrderDetailFragmentArgs.Builder().setId(orderId).build().toBundle()
    )
}

fun Context.goServiceCenter() {
    ActivityNavigation.goContainerActivity(
        this, CustomerCenterFragment::class.java
    )
}

fun Context.goPolicy() {
    ActivityNavigation.goContainerActivity(
        this, TermsAndConditionSettingFragment::class.java
    )
}

fun Context.goCompanyInfo() {
    ActivityNavigation.goContainerActivity(
        this, CompanyInfoFragment::class.java
    )
}

fun Context.goWebInfo(title: String, url: String) {
    ActivityNavigation.goContainerActivity(
        this, InfoWebFragment::class.java,
        InfoWebFragmentArgs.Builder().setUrl(url).setTitle(title).build().toBundle()
    )
}

fun Context.goBookmarkDetail(bookmark: Bookmark, url: String) {
    ActivityNavigation.goContainerActivity(
        this, BookmarkDetailFragment::class.java,
        BookmarkDetailFragmentArgs.Builder().setUrl(url).setBookmark(bookmark).build().toBundle()
    )
}

fun Context.goSearchDetail(searchArticle: SearchArticle, url: String) {
    ActivityNavigation.goContainerActivity(
        this, SearchDetailFragment::class.java,
        SearchDetailFragmentArgs.Builder().setUrl(url).setSearchArticle(searchArticle).build()
            .toBundle()
    )
}

fun Context.goSupplementComponent(product: medicalProduct) {
    ActivityNavigation.goContainerActivity(
        this, SupplementComponentFragment::class.java,
        SupplementComponentFragmentArgs.Builder().setProduct(product).build().toBundle()
    )
}

fun Context.goSearchProductDetail(searchProduct: SearchProduct) {
    ActivityNavigation.goContainerActivity(
        this, SearchSupplementDetailFragment::class.java,
        SearchSupplementDetailFragmentArgs.Builder().setSearchProduct(searchProduct).build()
            .toBundle()
    )
}

fun Context.goSearchSupplementComponent(searchProduct: SearchProduct) {
    ActivityNavigation.goContainerActivity(
        this, SearchSupplementComponentFragment::class.java,
        SearchSupplementComponentFragmentArgs.Builder().setSearchProduct(searchProduct).build()
            .toBundle()
    )
}

fun Context.goHomeDetail(mainCard: MainCard, url: String, options: ActivityOptions) {
    ActivityNavigation.goContainerActivity(
        this, HomeDetailFragment::class.java,
        HomeDetailFragmentArgs.Builder().setMainCard(mainCard).setUrl(url).build().toBundle(),
        options
    )
}


fun Context.goTracker(trackerId: String, carrier: String, petName: String, name: String) {
    ActivityNavigation.goContainerActivity(
        this, DeliveryInformationFragment::class.java,
        DeliveryInformationFragmentArgs.Builder().setTrackerid(trackerId).setCarrier(carrier)
            .setPetName(petName).setName(name).build().toBundle()
    )
}

fun Context.goInbox() {
    ActivityNavigation.goContainerActivity(
        this, NotificationFragment::class.java
    )
}

fun Context.goAlramList() {
    ActivityNavigation.goContainerActivity(
        this, IntakeCheckAlarmListFragment::class.java
    )
}





















