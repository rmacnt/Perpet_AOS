package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetWeightBinding
import pet.perpet.equal.presentation.sign.model.PetWeight
import pet.perpet.equal.presentation.sign.viewmodel.PetWeightViewModel
import pet.perpet.framework.widget.Keyboard
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetWeightViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetWeight>(
        viewGroup,
        R.layout.item_sign_pet_weight
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetWeightBinding =
        ItemSignPetWeightBinding.bind(itemView)

    val viewmodel: PetWeightViewModel = PetWeightViewModel()

    init {
        viewmodel.notify {
            try  {
                item.error = false
                item.petWeight = it
                binding.evWeight.setSelection(binding.evWeight.length())
                viewmodel.model = item
                binding.model = viewmodel
                sendEvent(hashCode())
            }catch (e: Exception) {

            }

        }
        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }

        KeyboardVisibilityEvent.registerEventListener(activity) {
            if(it.not()) {
                if (binding.evWeight.text?.contains(".").nonnull().not()) {
                    binding.evWeight.setText(binding.evWeight.text.toString().toFloat().toString())
                } else {
                    if(binding.evWeight.text.toString()[binding.evWeight.text.toString().length-1].toString() == ".") {
                        binding.evWeight.setText(binding.evWeight.text.toString().toFloat().toString())
                    } else {
                        binding.evWeight.setText( String.format("%.1f"  , binding.evWeight.text.toString().toDouble() ))
                    }
                }
            }
        }


    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetWeight?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun loadTalentListOnKeyboardActionSearch() = binding.evWeight
        .setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Keyboard.hideKeyboard(binding.evWeight)
                hide()
                if (binding.evWeight.text?.contains(".").nonnull().not()) {
                    binding.evWeight.setText(binding.evWeight.text.toString().toFloat().toString())
                } else {
                    if(binding.evWeight.text.toString()[binding.evWeight.text.toString().length-1].toString() == ".") {
                        binding.evWeight.setText(binding.evWeight.text.toString().toFloat().toString())
                    } else {
                        binding.evWeight.setText( String.format("%.1f"  , binding.evWeight.text.toString().toDouble() ))
                    }
                }
            }
            false
        }

    private fun hide() {
        Keyboard.hideKeyboard(binding.evWeight)
    }
}