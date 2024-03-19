package pet.perpet.equal.presentation.supplement.viewmodel

import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.goSubscribe
import pet.perpet.equal.presentation.sign.differ.PackageItemListDiffer
import pet.perpet.equal.presentation.supplement.differ.PackageInfoItemListDiffer
import pet.perpet.equal.presentation.supplement.fragment.SupplementPackageFragmentArgs
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import java.text.DecimalFormat
import java.util.Collections

class SupplementPackageViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        SupplementPackageFragmentArgs.fromBundle(arguments)
    }

    val title: String?
        get() = getString(R.string.subscribe_comment2)
            ?.let { String.format(it, args.name.nonnull()) }

    val name: String?
        get() = args.name

    val result: Boolean?
        get() = args.result

    val namePackage: String?
        get() = getString(R.string.subscribe_comment37)
            ?.let { String.format(it, args.name.nonnull()) }

    val price: MutableLiveData<Spanned> = MutableLiveData()
    val priceMonth: MutableLiveData<String> = MutableLiveData("")
    val priceTotalPrice: MutableLiveData<String> = MutableLiveData("")

    val priceText: Spanned?
        get() = price.value

    val priceMonthText: String?
        get() = priceMonth.value

    val itemListDiffer by lazy {
        PackageInfoItemListDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    init {
        args.medical?.let {
            it.packages?.let { data ->
                val items = mutableListOf<Any>()
                this.let {
                    items.addAll(data)
                    Collections.sort(items, Collections.reverseOrder())
                }
                itemListDiffer.allList(items)
            }
            itemListDiffer.adapter.notifyDataSetChanged()
        }

        getPrice()
        getMonthPrice()
        getTotalPrice()


    }
    //======================================================================
    // Public Methods
    //======================================================================

    fun getPrice() {
        var totalPrice = 0
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.price.nonnull()
        }


        price.value = String.format(
            getString(R.string.subscribe_payment_comment15),
            DecimalFormat("#,###").format(totalPrice)
        ).run { HtmlFactory.fromHtml(this) }

    }

    fun getAddPrice(priceCall: Int) {
        var totalPrice = priceCall
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.price.nonnull()
        }
        price.value = String.format(
            getString(R.string.subscribe_payment_comment15),
            DecimalFormat("#,###").format(totalPrice)
        ).run { HtmlFactory.fromHtml(this) }
    }

    fun getMonthPrice() {
        var totalPrice = 0
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.daily_price.nonnull()
        }
        priceMonth.value = DecimalFormat("#,###").format((totalPrice * 30))
    }

    fun getTotalPrice() {
        var totalPrice = 0
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.daily_price.nonnull()
        }
        priceTotalPrice.value = String.format(
            getString(R.string.price),
            DecimalFormat("#,###").format((totalPrice * 30))
        )
    }

    fun getTotalAddPrice(price: Int) {
        var totalPrice = 0
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.price.nonnull()
        }
        priceTotalPrice.value = String.format(
            getString(R.string.price),
            DecimalFormat("#,###").format((totalPrice * 30) + price)
        )
    }

    fun onSubmitClick(view: View) {
        activity?.let {
            args.medical?.let {
                activity?.goSubscribe(it, null, args.petId.nonnull(), args.name.nonnull())
                activity?.finish()
            }
        }
    }

    fun onHomeClick(view: View) {
        activity?.let {
            it.finish()
        }

    }


}