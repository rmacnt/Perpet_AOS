package pet.perpet.equal.presentation.search.viewmodel

import android.text.Spanned
import de.stocard.markdown_to_spanned.Markdown
import pet.perpet.domain.model.search.Ingredient


class ItemSearchSupplementComponentViewModel (var model: Ingredient? = null) {

    val title: String?
        get() = model?.name

    val comment: String?
        get() = model?.description

    val effect: String?
        get() = model?.effect

    val caution: Spanned?
        get() =  Markdown.fromMarkdown(model?.caution?.trim())

    val cautionVisible: Boolean?
        get() = model?.caution?.isNotEmpty()


}