package pet.perpet.framework.widget.recyclerview;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    //======================================================================
    // Constants
    //======================================================================

    public final static int TYPE_EMPTY = Integer.MIN_VALUE + 1;

    public final static int TYPE_FOOTER = Integer.MIN_VALUE + 2;

    private final static int TYPE_ITEM = Integer.MIN_VALUE + 4;

    private final static int HOLDER_ID = Integer.MIN_VALUE + 5;

    private final static int COUNT_EMPTY = 1;

    private final static int COUNT_FOOTER = 1;

    //======================================================================
    // Variables
    //======================================================================

    private ViewHolderHandler mEmptyViewHolderHandler;

    private ViewHolderHandler mFooterViewHolderHandler;

    private boolean mNonItemHeaderRemove;

    //======================================================================
    // Constructor
    //======================================================================

    public RecyclerViewAdapter() {
        getFooterViewHolderHandler().updateActive();
    }

    //======================================================================
    // Abstract Methods
    //======================================================================

    public abstract int getItemHeaderCount();

    public abstract int getSupportItemCount();

    public abstract Object getSupportItem(int viewType, int position);

    public abstract RecyclerViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    public final RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder;

        switch (viewType) {
            case TYPE_EMPTY:
                holder = onCreateEmptyViewHolder(parent);
                break;
            case TYPE_FOOTER:
                holder = onCreateFooterViewHolder(parent);
                break;
            default:
                holder = onCreateItemViewHolder(parent, viewType);
                break;
        }
        if (holder != null) {
            holder.onCreateViewHolder(viewType);
        }
        return holder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.onPreBindViewHolder();
        onPreBindViewHolder(holder, position);
        onBindArguments(holder.getItemViewType(), holder.mArguments);
        Object item = getSupportItem(holder.getItemViewType(), position);
        holder.onBindViewHolder(item);
    }

    @Override
    public final void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public final void onViewDetachedFromWindow(RecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }

    @Override
    public final void onViewRecycled(RecyclerViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onViewRecycled();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        AdapterUtil.releaseRecyclerViewHolder(recyclerView);
        release();
    }

    @Override
    public final int getItemViewType(int position) {
        int itemCount = getItemCount();
        if (mNonItemHeaderRemove == false && position < getItemHeaderCount()) {
            return getSupportItemViewType(position);
        }

        ViewHolderHandler empty = getEmptyViewHolderHandler();
        if (empty.isEnable() == true && getItemEmptyCount() == COUNT_EMPTY && getSupportItemCount() <= 0) {
            return TYPE_EMPTY;
        }
        if (position < itemCount) {
            if (getItemFooterCount() == COUNT_FOOTER && position == itemCount - 1) {
                return TYPE_FOOTER;
            }
        }
        return getSupportItemViewType(position);
    }

    @Override
    public final int getItemCount() {
        int itemCount = getSupportItemCount();
        if (itemCount <= 0) {
            if (mNonItemHeaderRemove == true) {
                return getItemEmptyCount();
            }
            return getItemHeaderCount() + getItemEmptyCount();
        }
        return getItemHeaderCount() + getItemFooterCount() + getSupportItemCount();
    }

    @Override
    public long getItemId(int position) {
        return HOLDER_ID + position;
    }

    //======================================================================
    // Protected Methods
    //======================================================================

    /**
     * {@link #onBindViewHolder(RecyclerViewHolder, int)} (ViewGroup, int)} 인자값 전달.
     */
    protected void onBindArguments(int viewType, @NonNull Bundle arguments) {
        // Override
    }

    /**
     * {@link #onBindViewHolder(RecyclerViewHolder, int)} (ViewGroup, int)} 인자값 전달.
     */
    protected void onPreBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    protected RecyclerViewHolder onCreateEmptyViewHolder(ViewGroup parent) {
        return null;
    }

    protected RecyclerViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        return null;
    }

    protected final boolean insertEmptyView() {
        final ViewHolderHandler empty = getEmptyViewHolderHandler();
        if (getSupportItemCount() > 0 == false) {
            if (empty.isEnable() == true && empty.isAddViewHolder() == false) {
                empty.updateInsert();
                return true;
            }
        }
        return false;
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public final int getItemFooterCount() {
        ViewHolderHandler holderHandler = getFooterViewHolderHandler();
        return holderHandler.isAddViewHolder() == true ? COUNT_FOOTER : 0;
    }

    @SuppressWarnings("unused")
    public final int getSupportItemPosition(int position) {
        return position - getItemHeaderCount();
    }

    public void setEmptyViewHolderEnable(boolean enable) {
        if (enable == true) {
            getEmptyViewHolderHandler().updateActive();
        } else {
            notifySupportRemoveEmptyView();
        }
    }

    public int getSupportItemViewType(int position) {
        return TYPE_ITEM + position;
    }

    public final void notifySupportRemoveFooter() {
        final ViewHolderHandler footer = getFooterViewHolderHandler();
        if (footer.isAddViewHolder() == true) {
            final int position = getItemCount();
            footer.updateRemove();
            notifyItemRemoved(position);
        }
    }

    public final void notifySupportInsertFooter() {
        final ViewHolderHandler footer = getFooterViewHolderHandler();
        if (footer.isAddViewHolder() == false && getSupportItemCount() > 0) {
            final int position = getItemCount();
            footer.updateInsert();
            notifyItemInserted(position);
        }
    }

    @SuppressWarnings("unused")
    public final void notifySupportInsertEmptyView() {
        if (insertEmptyView() == true) {
            notifyDataSetChanged();
        }
    }

    public final void notifySupportRemoveEmptyView() {
        final ViewHolderHandler empty = getEmptyViewHolderHandler();
        if (empty.isAddViewHolder() == true) {
            final int position = getItemHeaderCount();
            empty.updateRemove();
            notifyItemRemoved(position);
        }
    }

    public void release() {
        mEmptyViewHolderHandler = null;
        mFooterViewHolderHandler = null;
    }

    @SuppressWarnings("unused")
    public boolean isNonItemHeaderRemove() {
        return mNonItemHeaderRemove;
    }

    public void setNonItemHeaderRemove(boolean nonItemHeaderRemove) {
        mNonItemHeaderRemove = nonItemHeaderRemove;
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private ViewHolderHandler getEmptyViewHolderHandler() {
        if (mEmptyViewHolderHandler == null) {
            mEmptyViewHolderHandler = new ViewHolderHandler();
        }
        return mEmptyViewHolderHandler;
    }

    private ViewHolderHandler getFooterViewHolderHandler() {
        if (mFooterViewHolderHandler == null) {
            mFooterViewHolderHandler = new ViewHolderHandler();
        }
        return mFooterViewHolderHandler;
    }

    private int getItemEmptyCount() {
        ViewHolderHandler holderHandler = getEmptyViewHolderHandler();
        if (holderHandler.isEnable() == true) {
            return holderHandler.isAddViewHolder() == true ? COUNT_EMPTY : 0;
        }
        return 0;
    }
}
