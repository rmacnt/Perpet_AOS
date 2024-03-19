package pet.perpet.equal.presentation.home.viewholder

import android.app.ActivityOptions
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.Pair
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import pet.perpet.data.nonnull
import pet.perpet.domain.model.main.MainCard
import pet.perpet.equal.MyApplication
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemCardBinding
import pet.perpet.equal.presentation.dpToPx
import pet.perpet.equal.presentation.goHomeDetail
import pet.perpet.equal.presentation.home.viewmodel.ItemCardViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemHomeCardNewViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<MainCard>(viewGroup, R.layout.item_card) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemCardBinding = ItemCardBinding.bind(itemView)

    val viewmodel: ItemCardViewModel by lazy { ItemCardViewModel() }


    init {

        viewmodel.notify {
            if (it.cardType == 0) {

                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as FragmentActivity,
                    Pair.create(binding.tvTitle, "title_transform"),
                    Pair.create(binding.tvComment, "sub_title_transform"),
                    Pair.create(binding.itemImage, "image_transform")
                )
                it.tags
                MyApplication.contentDetail = true
                context.goHomeDetail(
                    it,
                    "https://equal.pet/content/ViewApp/${it.id}",
                    options
                )
            }
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: MainCard?) {
        super.onRefresh(t)
        viewmodel.model = t

        val span: Spannable = SpannableString(t?.top.nonnull()+"-")
        binding.tvTitle.text = addImageToEndOfTheString(span, R.drawable.ic_main_mark, context)


        if (t?.image != null) {
            if ((t.image as String).isNotEmpty()) {
                Glide.with(binding.itemImage)
                    .load(t.image)
                    .into(binding.itemImage)
            }
        } else {
            if (t?.imageDrawable != 0) {
                binding.itemImage.setImageResource(t?.imageDrawable.nonnull())
            }
        }


        binding.model = viewmodel
    }


    //======================================================================
    // Private Methods
    //======================================================================

    private fun addImageToEndOfTheString(text: Spannable, drawableResourceId : Int, context: Context) : SpannableStringBuilder {
        val drawable = ContextCompat.getDrawable(context, drawableResourceId)
        drawable?.setBounds(dpToPx(context , 3f),0, dpToPx(context , 32f), dpToPx(context , 25f))
        drawable?.let {
            val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_CENTER)
            val ssBuilder = SpannableStringBuilder(text)

            ssBuilder.setSpan(
                imageSpan,
                text.indexOf("-"),
                text.indexOf("-")+1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return ssBuilder
        } ?: run {
            return  SpannableStringBuilder(text)
        }
    }
}