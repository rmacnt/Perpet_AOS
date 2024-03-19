package pet.perpet.framework.widget.recyclerview.holder;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import pet.perpet.framework.widget.selector.MultiSelector;
import pet.perpet.framework.widget.selector.SelectorHolder;

import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder;

public class SelectorViewHolder<T> extends RecyclerViewHolder<T> implements SelectorHolder {

    //======================================================================
    // Variables
    //======================================================================

    private MultiSelector mMultiSelector;

    //======================================================================
    // Constructor
    //======================================================================

    public SelectorViewHolder(ViewGroup viewGroup, @LayoutRes int layoutRes) {
        super(viewGroup, layoutRes);
    }

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    public boolean getIgnore() {
        return false;
    }

    @Override
    public void onRefresh(T t) {
        if (getIgnore()){
            return;
        }
        if (mMultiSelector != null) {
            mMultiSelector.bindHolder(this, getId());
        }
    }

    @Override
    public void onBindArguments(@NonNull Bundle arguments) {
        super.onBindArguments(arguments);
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public MultiSelector getSelector() {
        return mMultiSelector;
    }

    public void setMultiSelector(MultiSelector multiSelector) {
        mMultiSelector = multiSelector;
    }

    public void onChecked(boolean checked) {
        // Override
    }

    public void setChecked(boolean checked) {
        if (getIgnore()){
            return;
        }
        if (mMultiSelector != null) {
            mMultiSelector.setSelected(this, checked);
        }
    }

    @Override
    public boolean isChecked() {
        return mMultiSelector.isSelected(getId());
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override
    public int getId() {
        return (int) getItemId();
    }

    @Override
    public boolean getActivated() {
        return itemView.isActivated();
    }

    @Override
    public void setActivated(boolean b) {
        itemView.setActivated(b);
    }
}
