package pet.perpet.equal.presentation.search.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.bookmark.BookmarkDeleteUseCase
import pet.perpet.domain.usecase.bookmark.BookmarkSetUseCase
import pet.perpet.equal.presentation.search.differ.SearchTagListDiffer
import pet.perpet.equal.presentation.search.fragment.SearchDetailFragmentArgs
import pet.perpet.equal.support.navigation.showFeed
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView

class SearchDetailViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        SearchDetailFragmentArgs.fromBundle(arguments)
    }
    var isShowToolbar: Boolean = true


    private var bookmarkId : Int?  = -1

    val coverThumbnailUrl: String?
        get() = args.searchArticle?.image.nonnull()

    val top: String?
        get() = args.searchArticle?.top

    val summary: String?
        get() = args.searchArticle?.summary

    val url: String?
        get() = args.url.nonnull()

    val bookmark: Boolean?
        get() = bookmarkResult.value

    val bookmarkResult: MutableLiveData<Boolean> = MutableLiveData(args.searchArticle?.bookmark)

    val itemPetListDiffer by lazy {
        SearchTagListDiffer()
    }

    val tagMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemPetListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return FlexboxLayoutManager(pet.perpet.equal.presentation.getContext()).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                    alignItems = AlignItems.CENTER
                }
            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onFeedClick(view: View) {
        activity?.let {activity->
            args.searchArticle?.bookmark_id?.let {
                activity.showFeed(it)
            }

        }
    }

    fun onShareClick(view: View) {
        val send = Intent(Intent.ACTION_SEND_MULTIPLE)
        send.type = "text/plain"

        val blogUrl = args.url.nonnull().replace("ViewApp", "View")
        activity?.let {
            val clipboardManager = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("", blogUrl))
        }
        send.putExtra(Intent.EXTRA_TEXT,blogUrl)

        val chooserTitle = "공유하기"
        view.context.startActivity(Intent.createChooser(send, chooserTitle))
    }

    fun onBookMarkClick(view: View) {
        viewModelScope.launch {
            bookmark?.let {
                if (it) {
                    BookmarkDeleteUseCase().parameter2 {
                        this.id = if(args.searchArticle?.bookmark_id != null && args.searchArticle?.bookmark_id.nonnull() > 0) {
                            args.searchArticle?.bookmark_id.nonnull()
                        }else {
                            bookmarkId.nonnull()
                        }
                    }.success {
                        if(it as Boolean) {
                            if(it) {
                                bookmarkResult.value = bookmarkResult.value?.not()
                                Toast.makeText(activity, "북마크가 해제되었습니다.", Toast.LENGTH_SHORT).show()
                                executeViewBinding()
                            }
                        }
                    }.fail {
                    }.execute()

                } else {
                    BookmarkSetUseCase().parameter2 {
                        this.petId = UserStore.mainPet?.id.nonnull()
                        this.targetId = args.searchArticle?.id.nonnull()
                    }.success {
                        it?.let {
                            bookmarkId = it.id
                            bookmarkResult.value = bookmarkResult.value?.not()
                            Toast.makeText(activity, "북마크가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                            executeViewBinding()
                        }
                    }.fail {
                    }.execute()
                }
            }
        }

    }


    fun onFinishClick(view: View) {
        Log.d("CHECK", "여기안타냐??")
        activity?.let {
            it.finish()
        }
    }
}