package pet.perpet.equal.presentation.sign.viewholder

import android.text.InputFilter
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetNameBinding
import pet.perpet.equal.presentation.sign.model.PetName
import pet.perpet.equal.presentation.sign.viewmodel.PetNameViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import java.util.regex.Pattern

class PetNameViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetName>(
        viewGroup,
        R.layout.item_sign_pet_name
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetNameBinding =
        ItemSignPetNameBinding.bind(itemView)

    val viewmodel: PetNameViewModel = PetNameViewModel()

    init {
        var filterAlphaNumSpace = InputFilter { source, start, end, dest, dstart, dend ->
            val ps = Pattern.compile("^[ㄱ-ㅣ가-힣]+$")
            if (!ps.matcher(source).matches()) {
                ""
            } else source
        }

        binding.evName.filters = arrayOf(filterAlphaNumSpace)

        viewmodel.notify {
            item.error = false
            item.petName = it
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }
    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetName?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}