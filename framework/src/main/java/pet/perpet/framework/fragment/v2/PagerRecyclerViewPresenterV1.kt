package pet.perpet.framework.fragment.v2

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import pet.perpet.framework.channel.ChannelLiveData
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.fragment.ViewHolderFactory
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.nonnull
import pet.perpet.framework.util.Logger
import pet.perpet.framework.widget.recyclerview.AsyncPagedProvider
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnSelfRemoveViewHolderListener
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import java.io.IOException

abstract class PagerRecyclerViewPresenterV1(fragment: Fragment) : UseViewModel(fragment),
    OnSelfRemoveViewHolderListener {

    //======================================================================
    // Variables
    //======================================================================

    private var cache: PagedList<Any>? = null

    val pageCompleteNotify: ChannelLiveData<Any>?
        get() = channelProvider?.get("pageCompleteNotify")

    val pageStartNotify: ChannelLiveData<PagerStartStatus>?
        get() = channelProvider?.get("pageStartNotify")

    val notifyItemChange: ChannelLiveData<Boolean>?
        get() = channelProvider?.get("notifyItemChange")

    val config = Config()

    val adapter: BaseRecyclerViewAdapter by lazy {
        onCreateAdapter()
    }

    val isEmptyViewHolder: Boolean
        get() = adapter.supportItemCount <= 0

    private val pagerProvider: PagerProvider by lazy {
        onCreateProvider()
    }

    val adapterProvider: AsyncPagedProvider<Any>? by lazy {
        createAsyncPageProvider()
    }

    var viewHolderSet: ViewHolderSet =
        ViewHolderSet.EMPTY
        get() {
            if (field == ViewHolderSet.EMPTY) {
                val temp = onCreateViewHolderSet()
                if (temp != null) {
                    viewHolderSet = temp
                }
            }
            return field
        }
        private set

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun onCreateViewHolderSet(): ViewHolderSet?

    abstract fun onCreateProvider(): PagerProvider

    abstract fun onCreateDataSource(): PagerDataSource

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onSelfRemoveViewHolder(holder: BaseRecyclerViewHolder<*>): Int {
        return -1
    }

    override fun onDestroy() {
        super.onDestroy()
        cache = null
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun isRangeFooter(position: Int): Boolean {
        val pos =
            adapterProvider?.pagedItemCount.nonnull() + adapterProvider?.itemHeaderCount.nonnull()
        log("isRangeFooter > $pos, footer $position")
        return position >= pos
    }

    fun executePageCompleteNotify(value: Boolean) {
        pageCompleteNotify?.postValue(value)
    }

    @Throws(IOException::class)
    open fun validPageExecute() {
    }

    fun clearList() {
        adapterProvider?.submitList(null)
    }

    fun execute(startStatus: PagerStartStatus = PagerStartStatus.FULL) {
        try {
            log("execute observe start $startStatus")
            pageStartNotify?.postValue(startStatus)
            validPageExecute()
            val factory = createDataSourceFactory()
            val liveData = createLivePaged(3, factory)
            fragment?.viewLifecycleOwner?.let {
                liveData.observe(it,
                    Observer { result ->
                        log("execute observe > ${result?.size}")
                        cache = result
                        adapterProvider?.submitList(null)
                        adapterProvider?.submitList(result)
                        executePageCompleteNotify(true)
                    })
            }
        } catch (e: Exception) {
            adapterProvider?.submitList(null)
            executePageCompleteNotify(false)
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun onCreateAdapter(): BaseRecyclerViewAdapter {
        val a = object : BaseRecyclerViewAdapter() {
            override fun onCreateItemViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerViewHolder<*>? {
                try {
                    val itemClass = viewHolderSet.findViewHolderClass(viewType)
                    return ViewHolderFactory.create(
                        itemClass,
                        parent,
                        null
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }
        a.setHasStableIds(true)
        a.provider = adapterProvider
        return a
    }

    private fun createDataSourceFactory(): DataSource.Factory<Int, Any> {
        return object : DataSource.Factory<Int, Any>() {
            override fun create(): DataSource<Int, Any> {
                return onCreateDataSource()
            }
        }
    }

    private fun createLivePaged(
        pageSize: Int,
        factory: DataSource.Factory<Int, Any>
    ): LiveData<PagedList<Any>> {
        return LivePagedListBuilder(
            factory, PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setMaxSize(Int.MAX_VALUE)
                .setInitialLoadSizeHint(pageSize * config.initialLoadSizeHintMultiple) // default: page size * 3
                .setPrefetchDistance(pageSize * config.prefetchDistanceMultiple) // default: page size
                .setEnablePlaceholders(config.placeHolder) // default: true
                .build()
        ).build()
    }

    private fun createAsyncPageProvider(): AsyncPagedProvider<Any> {
        return object : AsyncPagedProvider<Any>() {

            override fun onCreateItemCallback(): DiffUtil.ItemCallback<Any> {
                return pagerProvider
            }

            override fun onCreateListUpdateCallback(): ListUpdateCallback {
                return object : ListUpdateCallback {
                    override fun onChanged(position: Int, count: Int, payload: Any?) {
                        log("onChanged > position : ${position}, count : $count")
                        adapter.notifyItemRangeChanged(position, count, payload)
                    }

                    override fun onMoved(fromPosition: Int, toPosition: Int) {
                        log(
                            "onMoved > fromPosition : ${fromPosition}, toPosition : $toPosition"
                        )
                        adapter.notifyItemMoved(fromPosition, toPosition)
                    }

                    override fun onInserted(position: Int, count: Int) {
                        log(
                            "onInserted > fromPosition : ${position}, toPosition : $count"
                        )
                        if (position <= adapterProvider?.itemHeaderCount.nonnull()) {
                            adapter.notifyDataSetChanged()
                            /*firstItemsResponse = true*/
                        } else {
                            adapter.notifyItemRangeInserted(position, count)
                        }
                        notifyItemChange?.postValue(true)
                    }

                    override fun onRemoved(position: Int, count: Int) {
                        log(
                            "onRemoved > fromPosition : ${position}, toPosition : $count"
                        )
                        notifyItemChange?.postValue(false)
                        adapter.notifyItemRangeRemoved(position, count)
                    }
                }
            }


            override fun getSupportItemViewType(position: Int): Int {
                return viewHolderSet.asViewType(position)
            }

            override fun getSupportItemCount(): Int {
                val item = pagedItemCount
                val footer = pagerProvider.getFooterCount()
                if (item > 0) {
                    return item + footer
                }
                return item
            }

            open fun getHeaderItem(viewType: Int, position: Int): Any? {
                return pagerProvider.getHeaderItem(viewType, position)
            }

            open fun getFooterItem(viewType: Int, position: Int): Any? {
                return pagerProvider.getFooterItem(viewType, position)
            }

            override fun onBindArguments(viewType: Int, arguments: Bundle?) {
                pagerProvider.onBindArguments(viewType, arguments)
            }

            override fun getItemHeaderCount(): Int {
                return pagerProvider.getItemHeaderCount()
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                if (position < itemHeaderCount) {
                    return getHeaderItem(viewType, position)
                }
                val rawItemCount = pagedItemCount
                val rawPosition = position - itemHeaderCount
                if (rawPosition < rawItemCount) {
                    return getRawItem(rawPosition)
                }

                val footerPosition = position - itemHeaderCount - pagedItemCount
                val footerCount = pagerProvider.getFooterCount()
                if (footerPosition > -1 && footerPosition < footerCount) {
                    return getFooterItem(viewType, footerPosition)
                }
                return null
            }
        }
    }

    private fun log(message: String) {
        Logger.w("PAGERV2", message)
    }

    //======================================================================
    // DataSource
    //======================================================================

    abstract class PagerDataSource :
        PageKeyedDataSource<Int, Any>()


    //======================================================================
    // PagerProvider
    //======================================================================

    abstract class PagerProvider :
        DiffUtil.ItemCallback<Any>() {

        open fun getHeaderItem(viewType: Int, position: Int): Any? {
            return null
        }

        open fun getItemHeaderCount(): Int {
            return 0
        }

        open fun getFooterItem(viewType: Int, position: Int): Any? {
            return null
        }

        open fun getFooterCount(): Int {
            return 0
        }

        open fun onBindArguments(viewType: Int, arguments: Bundle?) {
        }
    }

    //======================================================================
    // Option
    //======================================================================

    data class Config(
        var nextPageRequest: Boolean = true,
        var firstStatPageRequest: Boolean = true,
        var totalCount: Int = Int.MAX_VALUE,
        var nextPageRequestTop: Boolean = false,
        var festRequest: Boolean = false,
        var initialLoadSizeHintMultiple: Int = 1,
        var prefetchDistanceMultiple: Int = 1,
        var placeHolder: Boolean = true
    )

    //======================================================================
    // PagerStartStatus
    //======================================================================

    enum class PagerStartStatus {
        TOP,
        FULL
    }

}
