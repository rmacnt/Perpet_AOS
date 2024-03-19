package pet.perpet.framework.util;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
public final class ViewUtil {

    /**
     * @param dp dp
     *
     * @return dp -> px size 변환
     */
    public static int dpToPx(float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
        return Math.round(px);
    }

    public static float dimenToDp(Context context, @DimenRes int dimen) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(dimen), context.getResources().getDisplayMetrics());
        return px;
    }

    public static float dimenToSp(Context context, @DimenRes int dimen) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, context.getResources().getDimension(dimen), context.getResources().getDisplayMetrics());
        return px;
    }

    public static void removeGlobalLayout(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    public static void invisible(View view) {
        if (view != null && view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void visibility(View view, boolean visible) {
        if (visible == true) {
            show(view);
        } else {
            hide(view);
        }
    }

    public static void show(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hide(View view) {
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    public static void inVisible(View view) {
        if (view != null && view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void setVisibility(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    public static boolean isVisible(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }

    public static boolean isGone(View view) {
        return view != null && view.getVisibility() == View.GONE;
    }

    public static boolean isInvisible(View view) {
        return view != null && view.getVisibility() == View.INVISIBLE;
    }

    public static void setBackgroundDrawable(View view, @DrawableRes int drawable) {
        setBackgroundDrawable(view, ContextCompat.getDrawable(view.getContext(), drawable));
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (view != null && drawable != null) {
            if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }

    public static void setOnClickListener(View view, View.OnClickListener listener) {
        if (view != null && listener != null) {
            view.setOnClickListener(listener);
        }
    }

    public static void disableHardwareAccelerateICS(@NonNull View view) {
        disableHardwareAccelerate(view, VERSION_CODES.JELLY_BEAN);
    }

    public static void disableHardwareAccelerate(@NonNull View view, int targetOSVersion) {
        if (VERSION.SDK_INT <= targetOSVersion) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public static int getMeasureSpecHeight(float ratio, int widthMeasureSpec) {
        int cx = View.MeasureSpec.getSize(widthMeasureSpec);
        int cy = Math.round(cx / ratio);
        return View.MeasureSpec.makeMeasureSpec(cy, View.MeasureSpec.EXACTLY);
    }

    public static int getMeasureSpecWidth(float ratio, int heightMeasureSpec) {
        final int cy = View.MeasureSpec.getSize(heightMeasureSpec);
        final int cx = Math.round(cy * ratio);
        return View.MeasureSpec.makeMeasureSpec(cx, View.MeasureSpec.EXACTLY);
    }
}
