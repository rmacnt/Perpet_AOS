package pet.perpet.equal.presentation.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;


public class SlideCloseLayout extends FrameLayout {
    private enum Direction {
        UP_DOWN,
        LEFT_RIGHT,
        NONE
    }

    private Direction direction = Direction.NONE;
    private int previousY;
    private int previousX;
    private boolean isScrollingUp;
    private boolean isLocked = false;
    private LayoutScrollListener mScrollListener;
    private Drawable mBackground;

    private int diffY;


    public SlideCloseLayout(Context context) {
        this(context, null);
    }

    public SlideCloseLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCloseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayoutScrollListener(LayoutScrollListener listener) {
        mScrollListener = listener;
    }
    public void setGradualBackground(Drawable drawable){
        this.mBackground = drawable;
    }

    public void lock() {
        isLocked = true;
    }

    public void unLock() {
        isLocked = false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isLocked) {
            return false;
        } else {
            final int y = (int) ev.getRawY();
            final int x = (int) ev.getRawX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    previousX = x;
                    previousY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int diffY = y - previousY;
                    int diffX = x - previousX;

                    if(diffY < 0)
                        return false;

                    if (Math.abs(diffX) + 50 < Math.abs(diffY)) {
                        return true;
                    }
                    break;
            }
            return false;
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (!isLocked) {
            final int y = (int) ev.getRawY();
            final int x = (int) ev.getRawX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    previousX = x;
                    previousY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    diffY = y - previousY;
                    int diffX = x - previousX;
                    //判断方向
                    if (direction == Direction.NONE) {
                        if (Math.abs(diffX) > Math.abs(diffY)) {
                            direction = Direction.LEFT_RIGHT;
                        } else if (Math.abs(diffX) < Math.abs(diffY)) {
                            direction = Direction.UP_DOWN;
                        } else {
                            direction = Direction.NONE;
                        }
                    }

                    if (direction == Direction.UP_DOWN) {
                        if (diffY > 0) {
                            this.setTranslationY(diffY);
                            if(diffY > 250) {
                                isScrollingUp = true;
                                if (mScrollListener != null) {
                                    mScrollListener.onLayoutClosed();
                                }
                            }
                        } else {
                            this.setTranslationY(0);
                        }
                        if (mBackground != null){
                            int alpha = (int) (255 * Math.abs(diffY * 1f)) / getHeight();
                            mBackground.setAlpha(255 - alpha);
                        }
                        return true;
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if(diffY > 0 && diffY < 250)
                        this.setTranslationY(0);

            }
            return true;
        }
        return false;
    }



    public interface LayoutScrollListener {
        void onLayoutClosed();
    }

}