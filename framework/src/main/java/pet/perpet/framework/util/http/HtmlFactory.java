package pet.perpet.framework.util.http;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public final class HtmlFactory {

    public static Spanned fromHtml(@NonNull Context context, @StringRes int formatRes, Object... objects) {
        final String value = context.getString(formatRes, objects);
        return Html.fromHtml(value);
    }

    public static Spanned fromHtml(@NonNull Context context, @StringRes int text) {
        return Html.fromHtml(context.getString(text));
    }

    public static Spanned fromHtml(String source) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            // noinspection deprecation
            return Html.fromHtml(source);
        }
        return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
    }
}
