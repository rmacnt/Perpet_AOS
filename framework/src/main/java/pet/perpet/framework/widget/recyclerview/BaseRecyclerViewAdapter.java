package pet.perpet.framework.widget.recyclerview;

import android.os.Bundle;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

public abstract class BaseRecyclerViewAdapter extends RecyclerViewAdapter {

    //======================================================================
    // Variables
    //======================================================================

    Provider mProvider;

    EmptyViewHolderProcessor mEmptyViewHolderProcessor;

    FooterViewHolderProcessor mFooterViewHolderProcessor;

    OnViewHolderEventListener holderEventListener;

    OnSelfRemoveViewHolderListener selfRemoveViewHolderListener;

    private OnSelfRemoveViewHolderListener innerOnSelfRemoveViewHolderListener = new OnSelfRemoveViewHolderListener() {

        @Override
        public int onSelfRemoveViewHolder(@NotNull BaseRecyclerViewHolder<?> holder) {
            if (selfRemoveViewHolderListener != null) {
                return selfRemoveViewHolderListener.onSelfRemoveViewHolder(holder);
            }
            return -1;
        }

    };

    //======================================================================
    // Constructor
    //======================================================================

    public BaseRecyclerViewAdapter(Provider provider) {
        mProvider = provider;
    }

    public BaseRecyclerViewAdapter() {
        // Nothing
    }

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    protected RecyclerViewHolder onCreateEmptyViewHolder(ViewGroup parent) {
        if (mEmptyViewHolderProcessor != null) {
            return mEmptyViewHolderProcessor.onCreateEmptyViewHolder(parent);
        }
        return null;
    }

    @Override
    protected RecyclerViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        if (mFooterViewHolderProcessor != null) {
            return mFooterViewHolderProcessor.onCreateFooterViewHolder(parent);
        }
        return null;
    }

    @Override
    public final int getItemHeaderCount() {
        if (mProvider != null) {
            return mProvider.getItemHeaderCount();
        }
        return 0;
    }

    @Override
    public final Object getSupportItem(int viewType, int position) {
        switch (viewType) {
            case TYPE_EMPTY:
                if (mEmptyViewHolderProcessor != null) {
                    return mEmptyViewHolderProcessor.getItem();
                }
            case TYPE_FOOTER:
                if (mFooterViewHolderProcessor != null) {
                    return mFooterViewHolderProcessor.getItem();
                }
        }
        if (mProvider != null) {
            return mProvider.getSupportItem(viewType, position);
        }
        return null;
    }

    @Override
    public final int getSupportItemCount() {
        if (mProvider != null) {
            return mProvider.getSupportItemCount();
        }
        return 0;
    }

    @Override
    public final int getSupportItemViewType(int position) {
        if (mProvider != null) {
            return mProvider.getSupportItemViewType(position);
        }
        return super.getSupportItemViewType(position);
    }

    @Override
    protected void onPreBindViewHolder(RecyclerViewHolder holder, int position) {
        super.onPreBindViewHolder(holder, position);
        if (holder instanceof BaseRecyclerViewHolder) {
            if (mProvider != null && mProvider instanceof ArgumentsProvider) {
                ((ArgumentsProvider) mProvider).onBindArguments(holder.getItemViewType(), holder.getArguments());
            }
            ((BaseRecyclerViewHolder) holder).setSelfRemoveViewHolderListener(innerOnSelfRemoveViewHolderListener);
            ((BaseRecyclerViewHolder) holder).setHolderEventListener(holderEventListener);
            holder.onBindArguments(holder.getArguments());
        }
    }

    @Override
    public void release() {
        super.release();
        /*holderEventListener = null;*/
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public void setEmptyViewHolderProcessor(EmptyViewHolderProcessor emptyViewHolderProcessor) {
        mEmptyViewHolderProcessor = emptyViewHolderProcessor;
    }

    public void setFooterViewHolderProcessor(FooterViewHolderProcessor footerViewHolderProcessor) {
        mFooterViewHolderProcessor = footerViewHolderProcessor;
    }

    public Provider getProvider() {
        return mProvider;
    }

    public void setProvider(Provider provider) {
        mProvider = provider;
        if (mProvider instanceof OnAttachAdapter) {
            ((OnAttachAdapter) mProvider).onAttach(this);
        }
    }

    public void setHolderEventListener(OnViewHolderEventListener holderEventListener) {
        this.holderEventListener = holderEventListener;
    }

    public void setSelfRemoveViewHolderListener(OnSelfRemoveViewHolderListener selfRemoveViewHolderListener) {
        this.selfRemoveViewHolderListener = selfRemoveViewHolderListener;
    }

    //======================================================================
    // AttachAdapter
    //======================================================================

    interface OnAttachAdapter {
        void onAttach(BaseRecyclerViewAdapter adapter);
    }

    //======================================================================
    // Provider
    //======================================================================

    public interface Provider {

        int getItemHeaderCount();

        int getSupportItemCount();

        int getSupportItemViewType(int position);

        Object getSupportItem(int viewType, int position);
    }

    //======================================================================
    // ArgumentsProvider
    //======================================================================

    public interface ArgumentsProvider extends Provider {

        /**
         * {@link RecyclerViewAdapter#onBindArguments(int, Bundle)}
         */
        void onBindArguments(int viewType, Bundle arguments);
    }

    //======================================================================
    // EmptyViewHolderProcessor
    //======================================================================

    public interface EmptyViewHolderProcessor {

        RecyclerViewHolder onCreateEmptyViewHolder(ViewGroup parent);

        Object getItem();
    }

    //======================================================================
    // FooterViewHolderProcessor
    //======================================================================

    public interface FooterViewHolderProcessor {

        RecyclerViewHolder onCreateFooterViewHolder(ViewGroup parent);

        Object getItem();
    }
}
