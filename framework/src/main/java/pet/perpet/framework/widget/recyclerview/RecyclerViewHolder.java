package pet.perpet.framework.widget.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerViewHolder<Item> extends RecyclerView.ViewHolder {

    //======================================================================
    // Variables
    //======================================================================

    private final View mItemView;

    private Item mItem;

    private boolean mItemChange;

    private boolean mAutoImageRelease = true;

    Bundle mArguments;

    private boolean mFirstLoad = true;

    private boolean mPayloadHolderChange = false;

    private boolean mBindArgumentsCall = false;

    //======================================================================
    // Constructor
    //======================================================================

    public RecyclerViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int itemVieId) {
        this(LayoutInflater.from(viewGroup.getContext()).inflate(itemVieId, viewGroup, false));
    }

    public RecyclerViewHolder(@NonNull SwipeRefreshRecyclerView viewGroup, @LayoutRes int itemVieId) {
        this(buildItemView(viewGroup.getRecyclerView(), itemVieId));
    }

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        onCreateView(mItemView);
    }

    //======================================================================
    // Abstract Methods
    //======================================================================

    public abstract void onRefresh(Item item);

    //======================================================================
    // Methods
    //======================================================================

    public void onBindingLifeCycleScope(@Nullable LifecycleOwner owner) {

    }

    /**
     * {@link RecyclerViewAdapter#onViewAttachedToWindow(RecyclerViewHolder)} 호출시 {@link #onViewAttachedToWindow()} 을 호출 한다.
     */
    public void onViewAttachedToWindow() {
        // Nothing
    }

    /**
     * {@link RecyclerViewAdapter#onViewDetachedFromWindow(RecyclerViewHolder)} 호출시 {@link #onViewDetachedFromWindow()} 을 호출 한다.
     */
    public void onViewDetachedFromWindow() {
        // Nothing
    }

    public boolean isPayloadHolderChange() {
        return mPayloadHolderChange;
    }

    public void setPayloadHolderChange(boolean mPayloadHolderChange) {
        this.mPayloadHolderChange = mPayloadHolderChange;
    }

    /**
     * {@link RecyclerViewAdapter#onBindViewHolder(RecyclerViewHolder, int)} 호출시 {@link #onBindViewHolder(Item)} 을 호출 한다.
     */
    void onPreBindViewHolder() {
        onBindArguments(getArgumentsInternal());
    }

    /**
     * 호출시 {@link #onPreBindViewHolder()} 을 호출 한다.
     *
     * @param item
     */
    public void onBindViewHolder(Item item) {
        if (mFirstLoad) {
            mFirstLoad = false;
        }
        if (mBindArgumentsCall == false) {
            onBindArguments(getArgumentsInternal());
        }
        if (mItem == null || mItem.equals(item) == false) {
            mItem = item;
            mItemChange = true;
        } else {
            mItemChange = false;
        }

        onBind();
        onRefresh(item);
    }

    /**
     * {@link RecyclerViewAdapter#onViewRecycled(RecyclerViewHolder)} 호출시 {@link #release()} 을 호출 한다.
     */
    final public void onViewRecycled() {
        release();
        mItem = null;
        mFirstLoad = true;
        mPayloadHolderChange = false;
        mBindArgumentsCall = false;
    }

    /**
     * {@link RecyclerViewAdapter#onCreateViewHolder(ViewGroup, int)} 호출
     *
     * @param viewType
     */
    final void onCreateViewHolder(int viewType) {

    }

    //======================================================================
    // Protected Methods
    //======================================================================

    /**
     * {@link RecyclerViewHolder} View 생성시 호출
     *
     * @param view
     * @see RecyclerViewAdapter#onCreateViewHolder(ViewGroup, int)
     */
    protected void onCreateView(@NonNull View view) {

    }

    /**
     * {@link #onBindViewHolder(Object)} 연결시 호출
     *
     * @see {@link RecyclerViewAdapter#onBindViewHolder(RecyclerViewHolder, int)}
     */
    protected void onBind() {
        // Override
    }

    /**
     * {@link RecyclerViewHolder} 연결시 전달할 인자값
     * <p>{@link #onBind()} 보다 먼저 호출된다.</p>
     *
     * @param arguments {@link Bundle 전달된 값}
     */
    public void onBindArguments(@NonNull Bundle arguments) {
        mBindArgumentsCall = true;
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public static View buildItemView(RecyclerView recyclerView, int itemViewId) {
        return LayoutInflater.from(recyclerView.getContext()).inflate(itemViewId, recyclerView, false);
    }

    public final Item getItem() {
        return mItem;
    }

    public boolean isItemChange() {
        return mItemChange;
    }

    public boolean isAutoImageRelease() {
        return mAutoImageRelease;
    }

    public void setAutoImageRelease(boolean autoImageRelease) {
        mAutoImageRelease = autoImageRelease;
    }

    public final Resources getResources() {
        return getContext().getResources();
    }

    public final Context getContext() {
        return mItemView.getContext();
    }

    public final View getItemView() {
        return mItemView;
    }

    public final View findViewById(int id) {
        return mItemView.findViewById(id);
    }

    @NonNull
    @Deprecated
    public final Bundle getArguments() {
        return getArgumentsInternal();
    }

    /**
     * 리소스 해제
     */
    public void release() {
        release(mItemView);
        mBindArgumentsCall = false;
    }

    //======================================================================
    // Private Methods
    //======================================================================

    @NonNull
    public Bundle getArgumentsInternal() {
        if (mArguments == null) {
            mArguments = new Bundle();
        }
        return mArguments;
    }

    private void release(View rootView) {
        if (rootView == null) {
            return;
        }

        if (rootView instanceof ViewGroup) {
            ViewGroup groupView = (ViewGroup) rootView;

            int childCount = groupView.getChildCount();
            for (int index = 0; index < childCount; index++) {
                release(groupView.getChildAt(index));
            }
        }
    }
}
