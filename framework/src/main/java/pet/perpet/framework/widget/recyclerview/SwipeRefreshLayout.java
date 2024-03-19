package pet.perpet.framework.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SwipeRefreshLayout extends androidx.swiperefreshlayout.widget.SwipeRefreshLayout {

    //======================================================================
    // Variables
    //======================================================================

    private View mTarget;

    //======================================================================
    // Constructor
    //======================================================================

    public SwipeRefreshLayout(Context context) {
        super(context);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //======================================================================
    // Override Methods
    //======================================================================

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean canChildScrollUp() {
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child instanceof RecyclerView) {
                    mTarget = child;
                    break;
                }
            }
        }

        if (mTarget != null && mTarget instanceof RecyclerView) {
            final RecyclerView recyclerView = ((RecyclerView) mTarget);
            if (recyclerView.getChildCount() > 0) {
                final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                final View child = recyclerView.getChildAt(0);
                final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(child);
                return !(manager.getItemCount() > 0 && holder.getAdapterPosition() == 0 && child.getTop() >= recyclerView.getPaddingTop());
            }
        }
        return super.canChildScrollUp();
    }
}
