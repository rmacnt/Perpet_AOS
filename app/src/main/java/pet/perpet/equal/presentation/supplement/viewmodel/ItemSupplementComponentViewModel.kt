package pet.perpet.equal.presentation.supplement.viewmodel

import android.text.Spanned
import de.stocard.markdown_to_spanned.Markdown
import pet.perpet.domain.model.medical.Ingredient

class ItemSupplementComponentViewModel(var model: Ingredient? = null) {

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