package pet.perpet.framework.activity.internal.helper;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import pet.perpet.framework.fragment.StringUtil;

/*
 * @author vkwofm
 * @since 2017. 3. 13.
 */
public class CacheInstanceHelper {

    //======================================================================
    // Public Methods
    //======================================================================

    @SuppressWarnings("ConstantConditions")
    public static String makeCacheAbsolutePath(@NonNull Context context) {
        try {
            return createCacheFile(context).getAbsolutePath() + "/" + UUID.randomUUID().toString().replace("_", "") + ".bin";
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("PointlessBooleanExpression")
    public static boolean isEnableCacheInstance() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    @SuppressWarnings({"ConstantConditions", "PointlessBooleanExpression"})
    public static boolean onSaveInstanceState(@NonNull Bundle bundle, @NonNull String path) {
        if (bundle == null || StringUtil.isEmpty(path) == true)
            return false;

        File storedBundleFile = new File(path);

        byte[] blob = marshall(bundle);
        OutputStream out = null;
        try {
            out = new GZIPOutputStream(new FileOutputStream(storedBundleFile));
            out.write(blob);
            out.flush();
            out.close();
        } catch (IOException e) {
            // ignore
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return true;
    }

    @SuppressWarnings({"PointlessBooleanExpression", "ResultOfMethodCallIgnored"})
    @Nullable
    public static Bundle onRestoredInstance(@NonNull String path) {
        if (StringUtil.isEmpty(path) == true)
            return null;

        File storedBundleFile = new File(path);

        byte[] blob = null;
        InputStream in = null;
        try {
            in = new GZIPInputStream(new FileInputStream(storedBundleFile));
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int n;
            byte[] buffer = new byte[1024];
            while ((n = in.read(buffer)) > -1) {
                bout.write(buffer, 0, n);   // Don't allow any extra bytes to creep in, final write
            }
            bout.close();
            blob = bout.toByteArray();
        } catch (IOException e) {
            // ignore
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        try {
            return (blob != null) ? (Bundle) unmarshall(blob) : null;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public static void clearAll(@NonNull Context context) {
        deleteFileOnScheduler(createCacheFile(context));
    }

    //======================================================================
    // Private Methods
    //======================================================================

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    private static File createCacheFile(@NonNull Context context) {
        try {
            String CACHE_FOLDER = "/cache";
            File cacheDir = new File(context.getExternalCacheDir().getAbsolutePath(), CACHE_FOLDER);
            if (cacheDir.isDirectory() == false) {
                cacheDir.mkdirs();
            }
            return cacheDir;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    private static <T extends Parcelable> byte[] marshall(@NonNull final T object) {
        try {
            android.os.Parcel p1 = android.os.Parcel.obtain();

            p1.writeValue(object);

            byte[] data = p1.marshall();
            p1.recycle();
            return data;
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    private static <T extends Parcelable> T unmarshall(@NonNull byte[] bytes) {
        android.os.Parcel p2 = android.os.Parcel.obtain();
        p2.unmarshall(bytes, 0, bytes.length);
        p2.setDataPosition(0);
        T result = (T) p2.readValue(CacheInstanceHelper.class.getClassLoader());
        p2.recycle();
        return result;
    }

    private static void deleteFileOnScheduler(final File file) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                deleteFile(file);
            }
        });
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "PointlessBooleanExpression"})
    private static void deleteFile(File file) {
        try {
            if (file.isDirectory() == true) {
                for (File child : file.listFiles()) {
                    deleteFile(child);
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
        }
    }
}
