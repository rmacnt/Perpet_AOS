package pet.perpet.equal.presentation.sign.viewholder

import android.annotation.SuppressLint
import android.view.ViewGroup
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetBirthBinding
import pet.perpet.equal.presentation.asAppCompatActivity
import pet.perpet.equal.presentation.sign.model.PetBirth
import pet.perpet.equal.presentation.sign.viewmodel.PetBirthViewModel
import pet.perpet.equal.support.navigation.showBirth
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
class PetBirthViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetBirth>(
        viewGroup,
        R.layout.item_sign_pet_birth
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetBirthBinding =
        ItemSignPetBirthBinding.bind(itemView)

    val viewmodel: PetBirthViewModel = PetBirthViewModel()

    init {
        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }

        viewmodel.notifyDialog {
            activity.asAppCompatActivity()?.let {
                val currentTime: Date = Calendar.getInstance().time
                val format = SimpleDateFormat("yyyy-MM")
                val formatYear = SimpleDateFormat("yyyy")
                val formatMonth = SimpleDateFormat("MM")

                if(item.petBirthYear.isNullOrEmpty().not()) {
                    activity?.showBirth(item.petBirthYear.nonnull() , item.petBirthMonth.nonnull()) {
                        item.petBirthYear = it.split("-")[0]
                        item.petBirthMonth = "%02d".format(it.split("-")[1].toInt())
                        val s = "%02d".format(it.split("-")[1].toInt())
//                        item.petBirth = (getMonthsDifference(format.parse(it) , currentTime)-1).toString()
                        item.petBirth =  it.split("-")[0] +"-"+ s
                        binding.model = viewmodel
                        sendEvent(hashCode())
                    }
                } else {
                    activity?.showBirth(formatYear.format(currentTime) , (formatMonth.format(currentTime).toInt() -1).toString()) {
                        item.petBirthYear = it.split("-")[0]
                        item.petBirthMonth = "%02d".format(it.split("-")[1].toInt())
                        val s = "%02d".format(it.split("-")[1].toInt())
                        item.petBirth = it.split("-")[0] +"-"+ s

//                        item.petBirth = (getMonthsDifference(format.parse(it) , currentTime)-1).toString()
                        binding.model = viewmodel
                        sendEvent(hashCode())
                    }
                }


            }
        }

    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetBirth?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

    private fun getMonthsDifference(date1: Date, date2: Date): Int {

        val month1 = date1.year * 12 + date1.month
        val month2 = date2.year * 12 + date2.month
        return month2 - month1
    }
}