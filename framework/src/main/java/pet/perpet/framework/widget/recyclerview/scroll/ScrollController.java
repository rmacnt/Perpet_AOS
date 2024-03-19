package pet.perpet.framework.widget.recyclerview.scroll;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import pet.perpet.framework.util.Logger;

public abstract class ScrollController extends RecyclerView.OnScrollListener {

    //======================================================================
    // Variables
    //======================================================================

    private ScrollPosition mScrollPosition;

    //======================================================================
    // Abstract Methods
    //======================================================================

    public abstract void onScrollState(State state);

    //======================================================================
    // Override Methods
    //======================================================================


    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        final int count = adapter.getItemCount();

        if (mScrollPosition == null) {
            mScrollPosition = new ScrollPosition() {
                @Override
                RecyclerView.LayoutManager getLayoutManager() {
                    return recyclerView.getLayoutManager();
                }
            };
        }

        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_DRAGGING:
            case RecyclerView.SCROLL_STATE_SETTLING:
            case RecyclerView.SCROLL_STATE_IDLE:
                /*if (count <= 0) {
                    onScrollState(State.DRAGGING);
                    return;
                }*/

                final int lastItemPosition = mScrollPosition.getLastCompletelyVisibleItemPosition();
                final int firstItemPosition = mScrollPosition.getFirstCompletelyVisibleItemPosition();
                final int firstVisiblePostion = mScrollPosition.getFirstVisibleItemPosition();

                Logger.d("ScrollController", "firstItemPosition : " + firstItemPosition + " lastItemPosition : " + lastItemPosition + " firstVisiblePos : " + firstVisiblePostion);

                if (lastItemPosition != -1 && lastItemPosition >= count - 1) {
                    onScrollState(State.FULL_DOWN_SCROLL);
                } else if (firstVisiblePostion != -1 && firstVisiblePostion <= 0) {
                    onScrollState(State.OVER_SCROLL);
                } else {
                    onScrollState(State.DRAGGING);
                }
                break;
        }
    }

    //======================================================================
    // State
    //======================================================================

    public enum State {
        FULL_DOWN_SCROLL,
        OVER_SCROLL,
        DRAGGING,
    }

    //======================================================================
    // ScrollPosition
    //======================================================================

    static abstract class ScrollPosition {

        abstract RecyclerView.LayoutManager getLayoutManager();

        int getLastCompletelyVisibleItemPosition() {
            if (getLayoutManager() instanceof GridLayoutManager) {
                int c = ((GridLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int v = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                return Math.max(c, v);
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                int arrays[] = ((StaggeredGridLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPositions(null);
                int position = 0;
                for (int array : arrays) {
                    position = Math.max(array, position);
                }
                return position;
            } else if (getLayoutManager() instanceof LinearLayoutManager) {
                int c = ((LinearLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int v = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                return Math.max(c, v);
            }
            return 0;
        }

        int getFirstCompletelyVisibleItemPosition() {
            if (getLayoutManager() instanceof GridLayoutManager) {
                return ((GridLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                int arrays[] = ((StaggeredGridLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPositions(null);
                int position = 0;
                for (int array : arrays) {
                    position = Math.min(array, position);
                }
                return position;
            } else if (getLayoutManager() instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            }
            return 0;
        }

        int getFirstVisibleItemPosition() {
            if (getLayoutManager() instanceof GridLayoutManager) {
                return ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                int arrays[] = ((StaggeredGridLayoutManager) getLayoutManager()).findFirstVisibleItemPositions(null);
                int position = 0;
                for (int array : arrays) {
                    position = Math.min(array, position);
                }
                return position;
            } else if (getLayoutManager() instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            }
            return 0;
        }
    }
}
