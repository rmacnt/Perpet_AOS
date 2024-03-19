package pet.perpet.framework.widget.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import pet.perpet.framework.util.CollectionValidator;
import pet.perpet.framework.widget.recyclerview.decoration.SpacingItemDecoration;

import java.util.ArrayList;

public class SwipeRefreshRecyclerView extends FrameLayout {

    //======================================================================
    // Variables
    //======================================================================

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AppItemDecoration mAppItemDecoration;

    private boolean mHasStableIds = true;

    //======================================================================
    // Constructor
    //======================================================================

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode() == true) {
            return;
        }
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //======================================================================
    // Override Methods
    //======================================================================

    @SuppressWarnings("unchecked")
    @Override
    public Parcelable onSaveInstanceState() {
        SaveState state = new SaveState(super.onSaveInstanceState());
        state.mLayoutManagerState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        return state;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SaveState) {
            SaveState ss = (SaveState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            Parcelable parcelable = ss.mLayoutManagerState;
            if (parcelable != null) {
                mRecyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        //Modify 15. 3. 19. lsh OS 버전이 낮은 단말에선 setPadding 먼저 호출 된다
        if (mRecyclerView != null) {
            mRecyclerView.setPadding(left, top, right, bottom);
        } else {
            super.setPadding(left, top, right, bottom);
        }
    }


    //======================================================================
    // Public Methods
    //======================================================================

    public void setSpacing(int verticalSpacing, int horizontalSpacing) {
        setSpacing(true, verticalSpacing, horizontalSpacing);
    }

    /**
     * {@link RecyclerView} 간격 설정
     *
     * @param horizontalFillSpacing 가로 영역 spacing 설정
     * @param verticalSpacing       세로 영역 간격
     * @param horizontalSpacing     가로 영역 간격
     */
    public void setSpacing(boolean horizontalFillSpacing, int verticalSpacing, int horizontalSpacing) {
        addItemDecoration(new SpacingItemDecoration(mRecyclerView, horizontalFillSpacing, verticalSpacing, horizontalSpacing));
    }

    public RecyclerViewAdapter getAdapter() {
        return (RecyclerViewAdapter) mRecyclerView.getAdapter();
    }

    public void setAdapter(RecyclerViewAdapter adapter) {
        if (adapter.hasObservers() == false)
            adapter.setHasStableIds(mHasStableIds);
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setHasStableIds(boolean hasStableIds) {
        mHasStableIds = hasStableIds;
    }

    public void clearItemDecoration() {
        mAppItemDecoration.clear();
    }

    public boolean isContainsItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        return mAppItemDecoration.isContains(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mAppItemDecoration.add(itemDecoration);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mAppItemDecoration.remove(itemDecoration);
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        mRecyclerView.addOnScrollListener(onScrollListener);
    }

    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public void setSwipeRefreshLayoutSetting(int schemeColorsRes, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mSwipeRefreshLayout.setColorSchemeColors(schemeColorsRes);
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        mRecyclerView.setNestedScrollingEnabled(enabled);
    }


    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @Nullable
    public RecyclerView.ViewHolder findViewHolderForAdapterPosition(int position) {
        return mRecyclerView.findViewHolderForAdapterPosition(position);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void invalidateItemDecorations() {
        mRecyclerView.invalidateItemDecorations();
    }

    public void setSwipeRefreshLayoutEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public boolean isAddItemDecoration() {
        return mAppItemDecoration.mItemList.size() > 0;
    }

    /**
     * {@link RecyclerView#setItemAnimator(RecyclerView.ItemAnimator)}
     *
     * @param itemAnimator {@link RecyclerView.ItemAnimator}
     */
    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        mRecyclerView.setItemAnimator(itemAnimator);
    }

    public void setPadding(boolean clipToPadding, int left, int top, int right, int bottom) {
        mRecyclerView.setClipToPadding(clipToPadding);
        mRecyclerView.setPadding(left, top, right, bottom);
        mRecyclerView.invalidateItemDecorations();
    }

    public void setRecyclerViewPaddingBottom(boolean clipToPadding, int padding) {
        int left = mRecyclerView.getPaddingLeft();
        int top = mRecyclerView.getPaddingTop();
        int right = mRecyclerView.getPaddingRight();
        int bottom = mRecyclerView.getPaddingBottom();

        mRecyclerView.setClipToPadding(clipToPadding);
        mRecyclerView.setPadding(left, top, right, padding);
        mRecyclerView.invalidateItemDecorations();
    }

    public void replaceRecyclerView(RecyclerView replace) {
        if (replace == null) {
            return;
        }

        if (mRecyclerView != replace) {
            removeRecyclerView();
            setUpRecyclerView(replace);
            addViewRecyclerView(replace);
            mRecyclerView = replace;
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private void init() {
        mAppItemDecoration = new AppItemDecoration();

        mSwipeRefreshLayout = new SwipeRefreshLayout(getContext());
        mSwipeRefreshLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mRecyclerView = new RecyclerView(getContext());
        setUpRecyclerView(mRecyclerView);
        addViewRecyclerView(mRecyclerView);
        addView(mSwipeRefreshLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setClipToPadding(false);
        recyclerView.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new AppDefaultItemAnimator());
        recyclerView.addItemDecoration(mAppItemDecoration);
    }

    private void addViewRecyclerView(RecyclerView recyclerView) {
        mSwipeRefreshLayout.addView(recyclerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void removeRecyclerView() {
        try {
            mSwipeRefreshLayout.removeView(mRecyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //======================================================================
    // SaveState
    //======================================================================

    public static class SaveState extends BaseSavedState {

        Parcelable mLayoutManagerState;

        @SuppressWarnings("unchecked")
        public SaveState(Parcel source) {
            super(source);
            mLayoutManagerState = source.readParcelable(RecyclerView.LayoutManager.class.getClassLoader());
        }

        public SaveState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(mLayoutManagerState, 0);
        }

        public static final Creator<SaveState> CREATOR = new Creator<SaveState>() {
            public SaveState createFromParcel(Parcel in) {
                return new SaveState(in);
            }

            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };
    }

    //======================================================================
    // AppItemAnimator
    //======================================================================

    private final static class AppDefaultItemAnimator extends DefaultItemAnimator {

        public AppDefaultItemAnimator() {
            setChangeDuration(0);
        }
    }

    //======================================================================
    // AppItemDecoration
    //======================================================================

    private final class AppItemDecoration extends RecyclerView.ItemDecoration {

        private ArrayList<RecyclerView.ItemDecoration> mItemList = new ArrayList<>();

        public void add(RecyclerView.ItemDecoration decoration) {
            mItemList.add(decoration);
            mRecyclerView.invalidateItemDecorations();
        }

        public void remove(RecyclerView.ItemDecoration decoration) {
            mItemList.remove(decoration);
            mRecyclerView.invalidateItemDecorations();
        }

        public boolean isContains(RecyclerView.ItemDecoration decoration) {
            return mItemList.contains(decoration);
        }

        public void clear() {
            mItemList.clear();
            mRecyclerView.invalidateItemDecorations();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (CollectionValidator.isValid(mItemList) == true) {
                for (RecyclerView.ItemDecoration decoration : mItemList) {
                    if (decoration != null) {
                        decoration.onDraw(c, parent, state);
                    }
                }
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            if (CollectionValidator.isValid(mItemList) == true) {
                for (RecyclerView.ItemDecoration decoration : mItemList) {
                    if (decoration != null) {
                        decoration.onDrawOver(c, parent, state);
                    }
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (CollectionValidator.isValid(mItemList) == true) {
                for (RecyclerView.ItemDecoration decoration : mItemList) {
                    if (decoration != null) {
                        decoration.getItemOffsets(outRect, view, parent, state);
                    }
                }
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {
            super.onDraw(c, parent);
            if (CollectionValidator.isValid(mItemList) == true) {
                for (RecyclerView.ItemDecoration decoration : mItemList) {
                    if (decoration != null) {
                        decoration.onDraw(c, parent);
                    }
                }
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent) {
            super.onDrawOver(c, parent);
            if (CollectionValidator.isValid(mItemList) == true) {
                for (RecyclerView.ItemDecoration decoration : mItemList) {
                    if (decoration != null) {
                        decoration.onDrawOver(c, parent);
                    }
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
            if (CollectionValidator.isValid(mItemList) == true) {
                for (RecyclerView.ItemDecoration decoration : mItemList) {
                    if (decoration != null) {
                        decoration.getItemOffsets(outRect, itemPosition, parent);
                    }
                }
            }
        }
    }
}
