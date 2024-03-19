package pet.perpet.equal.presentation.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import pet.perpet.domain.model.main.MainCard
import pet.perpet.equal.presentation.home.viewholder.ItemCardViewHolder

class MainCardPagerAdapter(
    private val listData: ArrayList<MainCard>,
    private val activity: FragmentActivity,
) : PagerAdapter() {


    override fun getCount(): Int {
        return listData.size
    }

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        var holder = ItemCardViewHolder.from(parent)
        holder.bind(listData[position], activity)
        parent.addView(holder.getView())
        return holder.getView()
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj as View
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }
}
