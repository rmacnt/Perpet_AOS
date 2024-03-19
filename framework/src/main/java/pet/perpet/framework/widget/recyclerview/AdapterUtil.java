package pet.perpet.framework.widget.recyclerview;

import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class AdapterUtil {

    public static void releaseRecyclerViewHolder(SwipeRefreshRecyclerView recyclerView) {
        if (recyclerView != null && recyclerView.getRecyclerView() != null) {
            releaseRecyclerViewHolder(recyclerView.getRecyclerView());
        }
    }

    public static void releaseRecyclerViewHolder(@NonNull RecyclerView recyclerView) {
        final int viewCount = recyclerView.getChildCount();
        for (int i = 0; i < viewCount; i++) {
            final View view = recyclerView.getChildAt(i);
            final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
            final RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
            viewHolder.onViewRecycled();
        }
    }

    public static void releaseAdapter(SwipeRefreshRecyclerView recyclerView) {
        if (recyclerView != null && recyclerView.getRecyclerView() != null) {
            RecyclerViewAdapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                adapter.onDetachedFromRecyclerView(recyclerView.getRecyclerView());
            }
        }
    }

    public static void notifySupportDataSetChanged(RecyclerViewAdapter adapter, boolean notifyDataSetChanged) {
        if (adapter == null) {
            return;
        }

        adapter.notifySupportRemoveFooter();
        adapter.notifySupportRemoveEmptyView();

        if (adapter.getItemCount() > 0) {
            adapter.insertEmptyView();
            adapter.notifyDataSetChanged();
        } else {
            if (adapter.insertEmptyView() == true) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public static void notifySupportDataSetChanged(RecyclerViewAdapter adapter, int start, int end) {
        if (adapter == null) {
            return;
        }

        adapter.notifySupportRemoveFooter();
        adapter.notifySupportRemoveEmptyView();

        if (adapter.getItemCount() > 0) {
            adapter.insertEmptyView();
            adapter.notifyItemRangeChanged(start, end);
        } else {
            if (adapter.insertEmptyView() == true) {
                adapter.notifyItemRangeChanged(start, end);
            }
        }
    }

    public static void notifySupportEmptyFooterChange(RecyclerViewAdapter adapter) {
        if (adapter == null) {
            return;
        }

        adapter.notifySupportRemoveFooter();
        adapter.notifySupportRemoveEmptyView();
        adapter.insertEmptyView();
    }


    public static void notifySupportFooterAdd(RecyclerViewAdapter adapter) {
        if (adapter == null) {
            return;
        }

        adapter.notifySupportRemoveFooter();
        adapter.notifySupportInsertFooter();
    }


    public static void notifyItemRemoved(SwipeRefreshRecyclerView view, int position) {
        swipeNotifyItemRemoved(view, position);


    }

    public static void notifyItemChanged(SwipeRefreshRecyclerView view, int position) {
        try {
            view.setSwipeRefreshLayoutEnable(false);
            view.getRecyclerView().getAdapter().notifyItemRangeChanged(position,1);
            view.getRecyclerView().getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            view.getRecyclerView().getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            if(view.getRecyclerView().getLayoutManager().canScrollVertically())
                                view.setSwipeRefreshLayoutEnable(true);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void notifyItemChanged(RecyclerView view, int position) {
        try {
            view.getAdapter().notifyItemChanged(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void notifyItemRemoved(RecyclerView view, int position) {
        RecyclerView.Adapter adapter = view.getAdapter();
        if (adapter == null || position == -1) {
            return;
        }

        try {
            view.setNestedScrollingEnabled(false);
            adapter.notifyItemRemoved(position);

            int itemCount = adapter.getItemCount();
            if (itemCount > 0) {
                adapter.notifyItemRangeChanged(position, itemCount);
            } else {
                notifySupportDataSetChanged((RecyclerViewAdapter) adapter, true);
            }
            view.setNestedScrollingEnabled(true);
        } catch (Exception e) {
            //Nothing
        }
    }

    public static void swipeNotifyItemRemoved(SwipeRefreshRecyclerView view, int position) {
        RecyclerView.Adapter adapter = view.getRecyclerView().getAdapter();
        if (adapter == null || position == -1) {
            return;
        }
        try {
            view.setSwipeRefreshLayoutEnable(false);
            adapter.notifyItemRemoved(position);

            int itemCount = adapter.getItemCount();
            if (itemCount > 0) {
                adapter.notifyItemRangeChanged(position, itemCount);
            } else {
                notifySupportDataSetChanged((RecyclerViewAdapter) adapter, true);
            }

            view.getRecyclerView().getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            view.getRecyclerView().getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            if(view.getRecyclerView().getLayoutManager().canScrollVertically())
                                view.setSwipeRefreshLayoutEnable(true);
                        }
                    });
        } catch (Exception e) {
            //Nothing
        }
    }

}