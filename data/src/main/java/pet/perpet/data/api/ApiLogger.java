package pet.perpet.data.api;

import android.util.Log;

import pet.perpet.data.DataConfig;


public final class ApiLogger {

    public static void i(String tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.e(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.w(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.v(tag, message);
        }
    }

    public static void i(Tag tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.i(tag.toString(), message);
        }
    }

    public static void d(Tag tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.d(tag.toString(), message);
        }
    }

    public static void e(Tag tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.e(tag.toString(), message);
        }
    }

    public static void w(Tag tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.w(tag.toString(), message);
        }
    }

    public static void v(Tag tag, String message) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            Log.v(tag.toString(), message);
        }
    }

    public static void printStackTrace(Exception e) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            e.printStackTrace();
        }
    }

    public static void printStackTrace(Throwable e) {
        if (DataConfig.INSTANCE.getProvider().getLogger()) {
            e.printStackTrace();
        }
    }

    public enum Tag {
        DATA_LOCAL,
        DATA_ERROR,
        DATA_REQUEST,
        DATA_RESPONSE,
    }
}
