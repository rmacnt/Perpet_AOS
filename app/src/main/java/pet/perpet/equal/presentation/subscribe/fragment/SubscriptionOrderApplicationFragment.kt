package pet.perpet.equal.presentation.subscribe.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.data.nonnull
import pet.perpet.domain.model.product.Product
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SubscribePackageFragmentBinding
import pet.perpet.equal.presentation.goSubscribe
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.observeBindingNotifyView
import pet.perpet.equal.presentation.subscribe.SubscribeChannel
import pet.perpet.equal.presentation.subscribe.viewmodel.SubscriptionOrderApplicationViewModel
import pet.perpet.framework.fragment.Fragment

class SubscriptionOrderApplicationFragment : Fragment<SubscriptionOrderApplicationViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    private var selectItem: ArrayList<Product> = ArrayList()
    private var totalCount = 0

    lateinit var binding: SubscribePackageFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
        viewmodel.observeBindingNotifyView {
            viewmodel.args.medical?.let {
                if(selectItem.size > 0) {
                    activity?.goSubscribe(it , selectItem , viewmodel.args.petId.nonnull() , viewmodel.args.name.nonnull())
                }else {
                    activity?.goSubscribe(it , null , viewmodel.args.petId.nonnull() ,  viewmodel.args.name.nonnull())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscribe_package_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SubscribePackageFragmentBinding.bind(view)
        binding.model = viewmodel
        viewmodel.getProductLoad()
        selectItem.clear()
        totalCount = 0
        SubscribeChannel.productClick.consumeEach {
            it?.let {
                totalCount = 0
                var savaItem = false
                selectItem.forEachIndexed { index, item->
                    if(item.id == it.id) {
                        savaItem = true
                        selectItem[index].setCount(it.getCount())
                    }
                    totalCount += (30 * it.count_per_unit.nonnull()) * selectItem[index].getCount()

                }
                if(savaItem.not()) {
                    selectItem.add(it)
                    totalCount += (30 * it.count_per_unit.nonnull()) * it.getCount()

                }
                if(it.getCount() == 0)
                    selectItem.remove(it)

                viewmodel.productTotalPrice.value = totalCount
                viewmodel.getAddPrice(totalCount)
                viewmodel.getTotalAddPrice(totalCount)
                binding.model = viewmodel
            }
        }

        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        selectItem.clear()
        totalCount = 0
    }
}