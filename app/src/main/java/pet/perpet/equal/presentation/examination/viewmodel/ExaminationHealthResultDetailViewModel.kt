package pet.perpet.equal.presentation.examination.viewmodel

import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import de.stocard.markdown_to_spanned.Markdown
import pet.perpet.data.nonnull
import pet.perpet.equal.presentation.examination.fragment.ExaminationHealthResultDetailFragmentArgs
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory

class ExaminationHealthResultDetailViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        ExaminationHealthResultDetailFragmentArgs.fromBundle(arguments)
    }

    val title: String?
        get() = getWord(args.result?.diagnosis?.name_kor.nonnull(), "이란?" , "란?")

    val comment: Spanned?
        get() = Markdown.fromMarkdown(args.result?.healthInfo?.trim())

    val comment1: Spanned?
        get() = Markdown.fromMarkdown(args.result?.attentionDisease?.trim())

    val comment2: Spanned?
        get() = Markdown.fromMarkdown(args.result?.nutritionalNeeds?.trim())


    //======================================================================
    // Public Methods
    //======================================================================


    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }

    }

    fun getWord(name: String, firstValue: String, secondValue: String?): String {
        val lastName = name[name.length - 1]
        if (lastName.code < 0xAC00 || lastName.code > 0xD7A3) {
            return name
        }
        val seletedValue = if ((lastName.code - 0xAC00) % 28 > 0) firstValue else secondValue!!
        return name + seletedValue
    }
}