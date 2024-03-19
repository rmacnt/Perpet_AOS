package pet.perpet.framework.widget.recyclerview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.AsyncPagedListDiffer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AsyncPagedProvider<T> implements BaseRecyclerViewAdapter.ArgumentsProvider {

    private final AsyncPagedListDiffer.PagedListListener<T> mListener =
            (previousList, currentList) -> {
                AsyncPagedProvider.this.onCurrentListChanged(currentList);
                AsyncPagedProvider.this.onCurrentListChanged(previousList, currentList);
            };
    final AsyncPagedListDiffer<T> mDiffer;

    /**
     * Creates a PagedListAdapter with default threading and
     * {@link ListUpdateCallback}.
     * <p>
     * Convenience for , which uses default threading
     * behavior.
     *
     * @param diffCallback The {@link DiffUtil.ItemCallback DiffUtil.ItemCallback} instance to
     *                     compare items in the list.
     */
    public AsyncPagedProvider(RecyclerView.Adapter adapter, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        mDiffer = new AsyncPagedListDiffer<>(adapter, diffCallback);
        mDiffer.addPagedListListener(mListener);
    }

    public AsyncPagedProvider(AsyncPagedListDiffer differ) {
        mDiffer = differ;
    }

    public AsyncPagedProvider() {
        mDiffer = new AsyncPagedListDiffer<T>(onCreateListUpdateCallback(), new AsyncDifferConfig.Builder<>(onCreateItemCallback()).build());
        mDiffer.addPagedListListener(mListener);
    }

    public DiffUtil.ItemCallback<T> onCreateItemCallback() {
        return null;
    }

    public ListUpdateCallback onCreateListUpdateCallback() {
        return null;
    }


    /**
     * Called when the current PagedList is updated.
     * <p>
     * This may be dispatched as part of {@link #submitList(PagedList)} if a background diff isn't
     * needed (such as when the first list is passed, or the list is cleared). In either case,
     * PagedListAdapter will simply call
     * {@link #notifyItemRangeInserted(int, int) notifyItemRangeInserted/Removed(0, mPreviousSize)}.
     * <p>
     * This method will <em>not</em>be called when the Adapter switches from presenting a PagedList
     * to a snapshot version of the PagedList during a diff. This means you cannot observe each
     * PagedList via this method.
     *
     * @param currentList new PagedList being displayed, may be null.
     * @see #getCurrentList()
     * @deprecated Use the two argument variant instead:
     * {@link #onCurrentListChanged(PagedList, PagedList)}
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public void onCurrentListChanged(@Nullable PagedList<T> currentList) {
    }

    /**
     * Called when the current PagedList is updated.
     * <p>
     * This may be dispatched as part of {@link #submitList(PagedList)} if a background diff isn't
     * needed (such as when the first list is passed, or the list is cleared). In either case,
     * PagedListAdapter will simply call
     * {@link #notifyItemRangeInserted(int, int) notifyItemRangeInserted/Removed(0, mPreviousSize)}.
     * <p>
     * This method will <em>not</em>be called when the Adapter switches from presenting a PagedList
     * to a snapshot version of the PagedList during a diff. This means you cannot observe each
     * PagedList via this method.
     *
     * @param previousList PagedList that was previously displayed, may be null.
     * @param currentList  new PagedList being displayed, may be null.
     * @see #getCurrentList()
     */
    public void onCurrentListChanged(
            @Nullable PagedList<T> previousList, @Nullable PagedList<T> currentList) {
    }

    @Override
    public int getItemHeaderCount() {
        return 0;
    }

    @Override
    public int getSupportItemCount() {
        return getPagedItemCount();
    }

    @Override
    public int getSupportItemViewType(int position) {
        return 0;
    }

    @Override
    public Object getSupportItem(int viewType, int position) {
        if (getItemHeaderCount() > 0) {
            return mDiffer.getItem(position - getItemHeaderCount());
        }
        return null;
    }

    @Override
    public void onBindArguments(int viewType, Bundle arguments) {

    }

    public T getRawItem(int position) {
        try {
            return mDiffer.getItem(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> getCurrentList() {
        try {
            return mDiffer.getCurrentList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void invalidate() {
        try {
            mDiffer.getCurrentList().getDataSource().invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPagedItemCount() {
        return mDiffer.getItemCount();
    }

    public void remove(int position) {
        try {
            mDiffer.getCurrentList().remove(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(Object value) {
        try {
            mDiffer.getCurrentList().remove(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the new list to be displayed.
     * <p>
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the tutee_main thread.
     *
     * @param pagedList The new list to be displayed.
     */
    public void submitList(@Nullable PagedList<T> pagedList) {
        mDiffer.submitList(pagedList);
    }

    public void selRemove() {
        PagedList<T> p = mDiffer.getCurrentList();

    }

    /**
     * Set the new list to be displayed.
     * <p>
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the tutee_main thread.
     * <p>
     * The commit callback can be used to know when the PagedList is committed, but note that it
     * may not be executed. If PagedList B is submitted immediately after PagedList A, and is
     * committed directly, the callback associated with PagedList A will not be run.
     *
     * @param pagedList      The new list to be displayed.
     * @param commitCallback Optional runnable that is executed when the PagedList is committed, if
     *                       it is committed.
     */
    public void submitList(@Nullable PagedList<T> pagedList,
                           @Nullable final Runnable commitCallback) {
        mDiffer.submitList(pagedList, commitCallback);
    }

    public final class Factory {

        public <T> AsyncPagedProvider<T> create(AsyncPagedListDiffer differ) {
            return new AsyncPagedProvider<>(differ);
        }

    }
}
