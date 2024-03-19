package pet.perpet.framework.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.TypedValue;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pet.perpet.framework.R;


public class Utils {

    public static String toNowYYYYMMDD() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
        } catch (Exception e) {
        }
        return null;
    }

    public static Date getYYYYMMDDNowDate() {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
            return new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static String toMMDDEEEFormat(String yyyymmdd) {
        try {
            Date raw = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(yyyymmdd);
            return new SimpleDateFormat("MM.dd(EEE)", Locale.KOREA).format(raw);
        } catch (Exception e) {
            // Nothing
        }
        return "";
    }

    public static String toMMDDFormat(String yyyymmdd) {
        try {
            Date raw = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(yyyymmdd);
            return new SimpleDateFormat("MM.dd", Locale.KOREA).format(raw);
        } catch (Exception e) {
            // Nothing
        }
        return yyyymmdd;
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getDialogPreferredPadding(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.dialogPreferredPadding, value, true);
        return context.getResources().getDimensionPixelSize(value.resourceId);
    }


    public static String parseNumberDecimalFormat(int number) {
        DecimalFormat decimalFormat = new DecimalFormat();
        return decimalFormat.format(number);
    }

    public static int randInt(int min, int max) {
        return (int) (Math.random() * (max - min));
    }

    public static int intervalDays(Date start, Date end) {
        try {
            long diff = start.getTime() - end.getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            return Math.round(days);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 0 ~ 25
     *
     * @param context
     * @param src
     * @param radius
     * @return
     */
    public static Bitmap transform(Context context, Bitmap src, float radius) {
        try {
            try {
                //Modify 15. 6. 15. lsh SM-N90L Crash bug 발생
                Bitmap bitmap = src.copy(src.getConfig() != null ? src.getConfig() : Bitmap.Config.ARGB_8888, true);
                RenderScript rs = RenderScript.create(context);
                Allocation input = Allocation.createFromBitmap(rs, src, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
                Allocation output = Allocation.createTyped(rs, input.getType());
                ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                script.setRadius(radius);
                script.setInput(input);
                script.forEach(output);
                output.copyTo(bitmap);
                script.destroy();
                output.destroy();
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return src;
    }

    /**
     * JSON 파일 읽어와서 파싱
     *
     * @param context
     * @param res
     * @return
     */
    public static String readRawText(@NonNull Context context, @RawRes int res) {
        InputStream inputStream = context.getResources().openRawResource(res);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder text = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    public static int upLoop(int current, int max) {
        if (current < max) {
            current++;
            return current;
        } else {
            return 0;
        }
    }

    public static int downLoop(int current, int defaultValue) {
        if (current > 0) {
            current--;
            return current;
        } else {
            return defaultValue;
        }
    }

    public static ColorMatrix createColorMatrix(int brightness) {
        return new ColorMatrix(new float[]{
                1, 0, 0, 0, brightness,
                0, 1, 0, 0, brightness,
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0});
    }

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }

    public static final Uri getUriToRaw(@NonNull Context context,
                                             @RawRes int raw) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(raw)
                + '/' + context.getResources().getResourceTypeName(raw)
                + '/' + context.getResources().getResourceEntryName(raw));
        return imageUri;
    }

    public static Bitmap getBitmap(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        connection.disconnect();
        return bitmap;
    }
}