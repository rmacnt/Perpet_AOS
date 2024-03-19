package pet.perpet.equal.presentation.subscribe.viewmodel

import ProductItemListDiffer
import android.text.Spanned
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.product.ProductListUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.sign.differ.PackageItemDiffer
import pet.perpet.equal.presentation.sign.differ.PackageItemListDiffer
import pet.perpet.equal.presentation.subscribe.fragment.SubscriptionOrderApplicationFragmentArgs
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import java.text.DecimalFormat

class SubscriptionOrderApplicationViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        SubscriptionOrderApplicationFragmentArgs.fromBundle(arguments)
    }

    val title: String?
        get() = getString(R.string.subscribe_comment2)
            ?.let { String.format(it, args.name.nonnull()) }

    val name: String?
        get() = args.name

    val namePackage: String?
        get() = getString(R.string.subscribe_comment37)
            ?.let { String.format(it, args.name.nonnull()) }

    val price: MutableLiveData<Spanned> = MutableLiveData()
    val priceMonth: MutableLiveData<String> = MutableLiveData("")
    val priceTotalPrice: MutableLiveData<String> = MutableLiveData("")
    val productTotalPrice: MutableLiveData<Int> = MutableLiveData(0)

    val priceText: Spanned?
        get() = price.value

    val priceMonthText: String?
        get() = priceMonth.value

    val priceTotalPriceText: String?
        get() = priceTotalPrice.value

    val productTotalPriceText: String?
        get() = String.format(getString(R.string.price), productTotalPrice.value)

    val itemListDiffer by lazy {
        PackageItemDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    val itemProductListDiffer by lazy {
        ProductItemListDiffer()
    }

    val productMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemProductListDiffer.adapter
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
        priceMonth.value = String.format(
            getString(R.string.price),
            DecimalFormat("#,###").format((totalPrice * 30))
        )
    }

    fun getTotalPrice() {
        var totalPrice = 0
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.price.nonnull()
        }
        priceTotalPrice.value = String.format(
            getString(R.string.subscribe_payment_comment14),
            DecimalFormat("#,###").format((totalPrice * 30))
        )
    }

    fun getTotalAddPrice(price: Int) {
        var totalPrice = 0
        args.medical?.packages?.forEach {
            totalPrice += it.rxInfo?.price.nonnull()
        }
        priceTotalPrice.value = String.format(
            getString(R.string.subscribe_payment_comment14),
            DecimalFormat("#,###").format((totalPrice * 30) + price)
        )
    }

    fun getProductLoad() {
        viewModelScope.launch {
            ProductListUseCase().parameter2 { }.success { data ->
                data?.content?.let { data ->
                    val items = mutableListOf<Any>()
                    this.let {
                        items.addAll(data)
                    }
                    itemProductListDiffer.allList(items)
                }
                itemProductListDiffer.adapter.notifyDataSetChanged()
            }.fail {
            }.execute()
        }
    }

    fun onSubmitClick(view: View) {
        executeViewBindingView(view)
    }

    fun onHomeClick(view: View) {
        activity?.let {
            it.finish()
        }

    }


}