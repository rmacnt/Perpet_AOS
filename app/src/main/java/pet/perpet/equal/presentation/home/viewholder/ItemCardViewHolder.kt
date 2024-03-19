package pet.perpet.equal.presentation.home.viewholder

import android.app.ActivityOptions
import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import pet.perpet.data.nonnull
import pet.perpet.domain.model.main.MainCard
import pet.perpet.equal.MyApplication
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemCardBinding
import pet.perpet.equal.presentation.goHomeDetail
import pet.perpet.equal.presentation.home.viewmodel.ItemCardViewModel


class ItemCardViewHolder(
    private val binding: ItemCardBinding,
) {

    private val viewmodel: ItemCardViewModel = ItemCardViewModel()

    fun getView(): View {
        return binding.root
    }

    fun bind(item: MainCard?, context: Context) {
        viewmodel.model = item
        binding.model = viewmodel

        binding.tvTitle.setIconifiedText(
            "${item?.top}",
            R.drawable.ic_main_mark
        )

        if (item?.image != null) {
            if ((item.image as String).isNotEmpty()) {
                Glide.with(binding.itemImage)
                    .load(item.image)
                    .into(binding.itemImage)
            }
        } else {
            if (item?.imageDrawable != 0) {
                binding.itemImage.setImageResource(item?.imageDrawable.nonnull())
            }
        }

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

    fun TextView.setIconifiedText(text: String, @DrawableRes iconResId: Int) {
        SpannableStringBuilder("$text#").apply {
            setSpan(
                ImageSpan(context, iconResId, DynamicDrawableSpan.ALIGN_BASELINE),
                text.length,
                text.length + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }.let {
            setText(it)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemCardViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCardBinding.inflate(layoutInflater, parent, false)
            return ItemCardViewHolder(binding)
        }
    }
}