package pet.perpet.framework.fragment.v2

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
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

abstract class PagerRecyclerViewPresenter(fragment: Fragment) : UseViewModel(fragment),
    OnSelfRemoveViewHolderListener {

    //======================================================================
    // Variables
    //======================================================================

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
    // Private Variables
    //======================================================================

    private var cache: PagedList<Any>? = null

    private val pagerProvider: PagerProvider by lazy {
        onCreateProvider()
    }

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

    fun execute(startStatus: PagerStartStatus = PagerStartStatus.FULL) {
        launcherPager(startStatus)
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun launcherPager(startStatus: PagerStartStatus = PagerStartStatus.FULL) {
        fun createDataSourceFactory(): DataSource.Factory<Int, Any> {
            return object : DataSource.Factory<Int, Any>() {
                val l = onCreateDataSource()
                override fun create(): DataSource<Int, Any> {
                    return l
                }
            }
        }

        fun createLivePaged(
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

        val pagedFlow: Flow<PagedList<Any>> = createDataSourceFactory().run {
            createLivePaged(3, this)
        }.asFlow()

        val minTime = 4000 * 2
        var lastTime: Long = 0
        var job: Job? = null

        suspend fun pageComplete(result: PagedList<Any>, delay: Long): Job =
            viewModelScope.launch(Dispatchers.Main) {
                while (isActive) {
                    log("execute observe pageComplete $delay")
                    lastTime = System.currentTimeMillis()
                    if (result.size > 0) {
                        adapterProvider?.submitList(null)
                        adapterProvider?.submitList(result)
                        executePageCompleteNotify(true)
                        cancel()
                    } else {
                        delay(delay)
                        adapterProvider?.submitList(null)
                        executePageCompleteNotify(true)
                        cancel()
                    }
                }
            }



        viewModelScope.launch {

            log("execute observe start $startStatus")
            pageStartNotify?.postValue(startStatus)
            try {
                validPageExecute()
            } catch (e: Exception) {
                log("validPageExecute exception message: ${e.message}")
                adapterProvider?.submitList(null)
                executePageCompleteNotify(false)
                return@launch
            }

            pagedFlow
                .distinctUntilChanged()
                .runCatching {
                    this
                }
                .mapCatching {
                    it
                }
                .onFailure {
                    log("pagedFlow onFailure message: ${it.message}")
                    adapterProvider?.submitList(null)
                    executePageCompleteNotify(false)
                }
                .onSuccess {
                    it.collectLatest { result ->
                        log("pagedFlow execute observe compete > ${result.size}")
                        cache = result

                        val interval = minTime - (lastTime.run {
                            if (this <= 0) {
                                0
                            } else {
                                val current = System.currentTimeMillis() - this
                                current
                            }
                        })
                        job?.cancel()
                        job = pageComplete(result, interval)
                    }
                }

        }
    }

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
        val fragmentname = this.fragment?.run {
            this::class.java.simpleName
        }
        Logger.w("PAGERV2", "[$fragmentname] " + message)
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
