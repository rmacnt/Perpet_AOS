package pet.perpet.framework.fragment

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

abstract class OnTabSelectedBehavior : TabLayout.OnTabSelectedListener {

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun dependencyView(): ViewPager

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onTabSelected(tab: TabLayout.Tab) {
        dependencyView().currentItem = tab.position
        selectTabProcess(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        unSelectTabProcess(tab)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        // ScrollTop
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun selectTabProcess(tab: TabLayout.Tab) {
        if (tab.customView != null) {
            /*View view = tab.getCustomView().findViewById(R.id.textview);
            if (view instanceof TextView) {
                view.setSelected(true);
            }*/
        }
    }

    private fun unSelectTabProcess(tab: TabLayout.Tab) {
        if (tab.customView != null) {
            /*View view = tab.getCustomView().findViewById(R.id.textview);
            if (view instanceof TextView) {
                view.setSelected(false);
            }*/
        }
    }
}
