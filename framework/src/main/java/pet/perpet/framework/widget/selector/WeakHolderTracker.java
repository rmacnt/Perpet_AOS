package pet.perpet.framework.widget.selector;

import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import pet.perpet.framework.widget.selector.SelectorHolder;


final class WeakHolderTracker {

    //======================================================================
    // Variables
    //======================================================================

    private SparseArray<WeakReference<SelectorHolder>> mHoldersByPosition = new SparseArray<>();

    //======================================================================
    // Public Methods
    //======================================================================

    public SelectorHolder getHolder(int id) {
        WeakReference<SelectorHolder> holderRef = mHoldersByPosition.get(id);
        if (holderRef == null) {
            return null;
        }

        SelectorHolder holder = holderRef.get();
        if (holder == null || holder.getId() != id) {
            mHoldersByPosition.remove(id);
            return null;
        }

        return holder;
    }

    public void bindHolder(SelectorHolder holder, int id) {
        mHoldersByPosition.put(id, new WeakReference<>(holder));
    }

    public List<SelectorHolder> getTrackedHolders() {
        List<SelectorHolder> holders = new ArrayList<>();

        for (int i = 0; i < mHoldersByPosition.size(); i++) {
            int key = mHoldersByPosition.keyAt(i);
            SelectorHolder holder = getHolder(key);

            if (holder != null) {
                holders.add(holder);
            }
        }

        return holders;
    }
}
