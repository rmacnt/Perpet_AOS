package pet.perpet.equal.presentation.notification.viewmodel

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.domain.usecase.notification.PushListUseCase
import pet.perpet.equal.presentation.notification.viewholder.NotificationNoticeViewHolder
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.fragment.v2.PagerRecyclerViewPresenter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class NotificationViewModel (fragment: Fragment) : PagerRecyclerViewPresenter(fragment) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateProvider(): PagerProvider {
        return object : PagerProvider() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateDataSource(): PagerDataSource {
        return object : PagerDataSource() {
            override fun loadInitial(
                params: LoadInitialParams<Int>,
                callback: LoadInitialCallback<Int, Any>
            ) {
                PushListUseCase().parameter2 {

                    this.page = 0
                    this.limit = 50

                }.sync()?.content?.run {
                    if (this.size > 0) {
                        this
                    } else {
                        null
                    }
                    callback.onResult(this as List<Any>, null, 1)
                }
            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {
                PushListUseCase().parameter2 {
                    page = params.key
                    this.limit = 20

                }.sync()?.content?.run {
                    if (this.size > 0) {
                        this
                    } else {
                        null
                    }
                }?.run {
                    callback.onResult(this as List<Any>, params.key + 1)
                }
            }

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {

            }
        }
    }

    override fun onCreateViewHolderSet(): ViewHolderSet? {
        return object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(NotificationNoticeViewHolder::class.java)

            override fun asViewType(position: Int): Int {
                return NotificationNoticeViewHolder::class.java.hashCode()
            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }
}