package pet.perpet.equal.presentation.base.binding

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import pet.perpet.equal.R
import pet.perpet.framework.widget.BaseEditText
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

object BaseBinding {

    @BindingAdapter("buttonAble")
    @JvmStatic
    fun setButton(button: AppCompatButton, check: Boolean?) {
        check?.let {
            if(it) {
                button.setBackgroundResource(R.drawable.black_radius_8)
            }else {
                button.setBackgroundResource(R.drawable.ddd_radius_8)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("recyclerview.layoutMediator")
    fun setLayoutMediator(rv: BaseRecyclerView, value: BaseRecyclerView.LayoutMediator?) {
        value?.let {
            rv.setLayoutMediator(it)
        }

    }
}