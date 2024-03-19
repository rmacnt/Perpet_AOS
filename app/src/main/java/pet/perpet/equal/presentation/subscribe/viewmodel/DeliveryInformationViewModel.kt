package pet.perpet.equal.presentation.subscribe.viewmodel

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import pet.perpet.data.nonnull
import pet.perpet.domain.model.tracker.TrackerProgresse
import pet.perpet.domain.usecase.tracker.TrackerGetUseCase
import pet.perpet.equal.presentation.safetyCallback
import pet.perpet.equal.presentation.subscribe.fragment.DeliveryInformationFragmentArgs
import pet.perpet.equal.presentation.subscribe.viewholder.delivery.ItemDeliveryEndViewHolder
import pet.perpet.equal.presentation.subscribe.viewholder.delivery.ItemDeliveryMiddleStartViewHolder
import pet.perpet.equal.presentation.subscribe.viewholder.delivery.ItemDeliveryMiddleViewHolder
import pet.perpet.equal.presentation.subscribe.viewholder.delivery.ItemDeliveryStartOneViewHolder
import pet.perpet.equal.presentation.subscribe.viewholder.delivery.ItemDeliveryStartViewHolder
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date

class DeliveryInformationViewModel(fragment: Fragment) : RecyclerViewPresenter(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    private val args by lazy {
        DeliveryInformationFragmentArgs.fromBundle(arguments)
    }

    val carrierNameText: String?
        get() = carrierName.value

    val carrierNumberText: String?
        get() = carrierNumber.value

    val productNameText: String?
        get() = args.petName + "맞춤 영양제"

    val deliveryToText: String?
        get() = args.name + "님"

    val deliveryFromText: String?
        get() = deliveryFrom.value


    val items: ArrayList<TrackerProgresse> = arrayListOf()


    val carrierName: MutableLiveData<String> = MutableLiveData("")
    val carrierNumber: MutableLiveData<String> = MutableLiveData("")
    val deliveryFrom: MutableLiveData<String> = MutableLiveData("")


    //======================================================================
    // Override Methods
    //======================================================================


    override fun onCreateProvider(): BaseRecyclerViewAdapter.Provider {
        return object : BaseRecyclerViewAdapter.Provider {
            override fun getItemHeaderCount(): Int {
                return 0
            }

            override fun getSupportItemCount(): Int {
                return items.size
            }

            override fun getSupportItemViewType(position: Int): Int {
                return viewHolderSet.asViewType(position)
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return items[position]
            }
        }
    }

    override fun onCreateViewHolderSet(): ViewHolderSet? {
        return object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(
                    ItemDeliveryStartOneViewHolder::class.java,
                    ItemDeliveryStartViewHolder::class.java,
                    ItemDeliveryMiddleViewHolder::class.java,
                    ItemDeliveryMiddleStartViewHolder::class.java,
                    ItemDeliveryEndViewHolder::class.java
                )

            override fun asViewType(position: Int): Int {
                if (items.size == 1) {
                    return ItemDeliveryStartOneViewHolder::class.java.hashCode()
                } else {

                    return if (position == 0) {
                        ItemDeliveryEndViewHolder::class.java.hashCode()
                    } else {
                        if (items.size - 1 == position) {
                            ItemDeliveryStartViewHolder::class.java.hashCode()
                        } else {
                            val item = items[position]
                            if(item.rootType) {
                                ItemDeliveryMiddleStartViewHolder::class.java.hashCode()
                            }else {
                                ItemDeliveryMiddleViewHolder::class.java.hashCode()
                            }
                        }
                    }
                }
            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    @SuppressLint("SimpleDateFormat")
    fun load(callback: () -> Unit) {
        fun safetyCall() {
            safetyCallback {
                callback()
            }
        }
        TrackerGetUseCase()
            .parameter2 {
                this.carrier = args.carrier.nonnull()
                this.trackerid = args.trackerid.nonnull()
            }
            .success {
                it?.let {
                    items.clear()
                    carrierName.value = it.carrier?.name
                    carrierNumber.value = it.state?.id
                    deliveryFrom.value = it.from?.name

                    it.progresses?.let {
                        items.addAll(it)
                        Collections.sort(items, Collections.reverseOrder())

                        val formatter = SimpleDateFormat("MM.dd")
                        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

                        items.forEachIndexed { index, trackerProgresse ->
                            if (index > 0 && index < items.size - 1) {
                                trackerProgresse.rootType = formatter.format(dateFormatter.parse(items[index - 1].time.toString()) as Date) != formatter.format(dateFormatter.parse(items.get(index).time.toString()) as Date)
                            }
                        }
                        items.sort()
                    }
                    safetyCall()
                    executeViewBinding()
                }
            }.fail {
                safetyCall()
            }.execute()

    }

    fun onHomeClick(view: View) {
        activity?.let {
            it.finish()
        }
    }
}