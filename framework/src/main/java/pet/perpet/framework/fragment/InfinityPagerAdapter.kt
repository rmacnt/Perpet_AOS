package pet.perpet.framework.fragment

import android.database.DataSetObserver
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class InfinityPagerAdapter(private val adapter: PagerAdapter) : PagerAdapter() {
    override fun getCount(): Int {
        return adapter.count
    }

    val realCount: Int
        get() = adapter.count

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val virtualPosition = position % realCount
        debugPrint("instantiateItem: real position: $position")
        debugPrint("instantiateItem: virtual position: $virtualPosition")

        // only expose virtual position to the inner adapter
        return adapter.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val virtualPosition = position % realCount
        debugPrint("destroyItem: real position: $position")
        debugPrint("destroyItem: virtual position: $virtualPosition")

        adapter.destroyItem(container, position, `object`)
    }

    override fun finishUpdate(container: ViewGroup) {
        adapter.finishUpdate(container)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return adapter.isViewFromObject(view, `object`)
    }

    override fun restoreState(bundle: Parcelable?, classLoader: ClassLoader?) {
        adapter.restoreState(bundle, classLoader)
    }

    override fun saveState(): Parcelable? {
        return adapter.saveState()
    }

    override fun startUpdate(container: ViewGroup) {
        adapter.startUpdate(container)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val virtualPosition = position % realCount
        return adapter.getPageTitle(position)
    }

    override fun getPageWidth(position: Int): Float {
        return adapter.getPageWidth(position)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        adapter.setPrimaryItem(container, position, `object`)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        adapter.unregisterDataSetObserver(observer)
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {
        adapter.registerDataSetObserver(observer)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return adapter.getItemPosition(`object`)
    }

    private fun debugPrint(message: String) {
        if (isDebug) {
            Log.d(TAG, message)
        }
    }

    companion object {
        private const val TAG = "InfinityPagerAdapter"
        private const val isDebug = false
    }
}