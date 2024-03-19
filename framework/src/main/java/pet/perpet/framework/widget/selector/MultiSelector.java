package pet.perpet.framework.widget.selector;

import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

public class MultiSelector {

    //======================================================================
    // Variables
    //======================================================================

    private SparseBooleanArray mSelections = new SparseBooleanArray();

    private WeakHolderTracker mTracker = new WeakHolderTracker();

    //======================================================================
    // Public Methods
    //======================================================================}

    public void setSelected(SelectorHolder holder, boolean isSelected) {
        setSelected(holder.getId(), isSelected);
    }

    public void setSelected(int position, boolean isSelected) {
        mSelections.put(position, isSelected);
        refreshHolder(mTracker.getHolder(position));
    }

    public boolean isSelected(int position) {
        return mSelections.get(position);
    }

    public void clearSelections() {
        mSelections.clear();
        refreshAllHolders();
    }

    public List<Integer> getSelectedPositions() {
        List<Integer> positions = new ArrayList<Integer>();

        for (int i = 0; i < mSelections.size(); i++) {
            if (mSelections.valueAt(i)) {
                positions.add(mSelections.keyAt(i));
            }
        }

        return positions;
    }

    public void bindHolder(SelectorHolder holder, int id) {
        mTracker.bindHolder(holder, id);
        refreshHolder(holder);
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private void refreshAllHolders() {
        for (SelectorHolder holder : mTracker.getTrackedHolders()) {
            refreshHolder(holder);
        }
    }

    private void refreshHolder(SelectorHolder holder) {
        if (holder == null) {
            return;
        }

        boolean isActivated = mSelections.get(holder.getId());
        holder.setActivated(isActivated);
        holder.onChecked(isActivated);
    }
}
