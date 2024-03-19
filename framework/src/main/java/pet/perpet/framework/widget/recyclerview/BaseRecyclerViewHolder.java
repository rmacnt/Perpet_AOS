package pet.perpet.framework.widget.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.jetbrains.annotations.NotNull;

import pet.perpet.framework.util.Logger;
import pet.perpet.framework.widget.recyclerview.holder.SelectorViewHolder;

public abstract class BaseRecyclerViewHolder<Item> extends SelectorViewHolder<Item> {

    //======================================================================
    // Variables
    //======================================================================

    private final static Bundle EMPTY = new Bundle();

    private OnViewHolderEventListener holderEventListener;

    private OnSelfRemoveViewHolderListener selfRemoveViewHolderListener;

    //======================================================================
    // Constructor
    //======================================================================

    public BaseRecyclerViewHolder(ViewGroup viewGroup, @LayoutRes int itemVieId) {
        super(viewGroup, itemVieId);
    }

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    public void onBindArguments(@NonNull Bundle arguments) {
        super.onBindArguments(arguments);
    }

    @Override
    public void release() {
        super.release();
        /*releaseInnerRecyclerView(getItemView());*/
        holderEventListener = null;
        selfRemoveViewHolderListener = null;
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public boolean onSelfRemoveViewHolder() {
        return false;
    }

    public void setSelfRemoveViewHolderListener(OnSelfRemoveViewHolderListener selfRemoveViewHolderListener) {
        this.selfRemoveViewHolderListener = selfRemoveViewHolderListener;
    }

    public void setHolderEventListener(OnViewHolderEventListener holderEventListener) {
        this.holderEventListener = holderEventListener;
    }

    public void sendEvent(int id, @NotNull Bundle bundle) {
        OnViewHolderEventListener l = getOnViewHolderEventListener();
        if (l != null) {
            l.onRecyclerViewHolderEvent(this, id, bundle);
        }
    }

    public void sendEvent(BaseRecyclerViewHolder holder) {
        OnViewHolderEventListener l = getOnViewHolderEventListener();
        if (l != null) {
            l.onRecyclerViewHolderEvent(holder, -1, EMPTY);
        }
    }

    public void sendEvent(int id) {
        OnViewHolderEventListener l = getOnViewHolderEventListener();
        if (l != null) {
            l.onRecyclerViewHolderEvent(this, id, EMPTY);
        }
    }

    public Activity getActivity() {
        if (getContext() instanceof Activity) {
            return (Activity) getContext();
        }
        return null;
    }

    private RecyclerView getRecyclerView() {
        if (itemView.getParent() instanceof RecyclerView) {
            return (RecyclerView) itemView.getParent();
        }
        return null;
    }

    public boolean selfRemove() {
        if (selfRemoveViewHolderListener != null) {
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.stopScroll();
                recyclerView.setNestedScrollingEnabled(false);
                int result = selfRemoveViewHolderListener.onSelfRemoveViewHolder(this);
                Logger.d("HOLDER", "selfRemove before " + result);
                if (result != -1) {
                    notifyItemRemoved(recyclerView, result);
                }
                recyclerView.setNestedScrollingEnabled(true);
                Logger.d("HOLDER", "selfRemove after " + result);
                return result >= 0;
            }
        }
        return false;
    }

    private void notifyItemRemoved(RecyclerView view, int position) {
        AdapterUtil.notifyItemRemoved(view, position);
    }

    //======================================================================
    // Private Methods
    //======================================================================

    public void dispatchOnViewHolderEvent(BaseRecyclerViewHolder holder, int id, Bundle bundle) {
        OnViewHolderEventListener l = getOnViewHolderEventListener();
        if (l != null) {
            l.onRecyclerViewHolderEvent(holder, id, bundle);
        }
    }

    protected void dispatchOnViewHolderEvent(BaseRecyclerViewHolder holder, int id) {
        OnViewHolderEventListener l = getOnViewHolderEventListener();
        if (l != null) {
            l.onRecyclerViewHolderEvent(holder, id, EMPTY);
        }
    }

    protected OnViewHolderEventListener getOnViewHolderEventListener() {
        if (holderEventListener != null) {
            return holderEventListener;
        }

        Activity activity = getActivity();
        if (activity != null && activity instanceof OnViewHolderEventListener) {
            return (OnViewHolderEventListener) activity;
        }

        return null;
    }

    private void releaseInnerRecyclerView(View rootView) {
        if (rootView == null) {
            return;
        }
        if (rootView instanceof RecyclerView) {
            RecyclerViewUtil.releaseRecyclerView((RecyclerView) rootView);
        } else if (rootView instanceof ViewGroup) {
            ViewGroup groupView = (ViewGroup) rootView;

            int childCount = groupView.getChildCount();
            for (int index = 0; index < childCount; index++) {
                releaseInnerRecyclerView(groupView.getChildAt(index));
            }
        }
    }

    private String getItemViewResName(@LayoutRes int itemVieId) {
        String[] nameSplit = getResources().getResourceEntryName(itemVieId).split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : nameSplit) {
            String trans = name.substring(0, 1);
            trans = trans.toUpperCase();
            trans += name.substring(1);
            stringBuilder.append(trans);
        }
        return stringBuilder.toString();
    }
}
