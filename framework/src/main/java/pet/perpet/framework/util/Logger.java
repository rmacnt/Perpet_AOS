package pet.perpet.framework.util;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import pet.perpet.framework.fragment.StringUtil;

/*
 * @author lsh
 * @since 2016. 2. 25.
 */
public class Logger {

    //======================================================================
    // Public Methods
    //======================================================================

    public static int v(String tag, String message) {
        return internal(Level.VERBOSE, tag, message);
    }

    public static int d(String tag, String message) {
        return internal(Level.DEBUG, tag, message);
    }

    public static int i(String tag, String message) {
        return internal(Level.INFO, tag, message);
    }

    public static int w(String tag, String message) {
        return internal(Level.WARN, tag, message);
    }

    public static int e(String tag, String message) {
        return internal(Level.ERROR, tag, message);
    }

    public static int println(Level level, String tag, String message) {
        return internal(level, tag, message);
    }

    public static void printStackTrace(@NotNull Exception e) {
        if (Config.isLogEnable()) {
            e.printStackTrace();
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private static int internal(Level level, String tag, String message) {
        if (StringUtil.isEmpty(message) == true) {
            return 0;
        }
        switch (level) {
            case VERBOSE:
                return Log.v(tag, message);
            case DEBUG:
                return Log.d(tag, message);
            case INFO:
                return Log.i(tag, message);
            case WARN:
                return Log.w(tag, message);
            case ERROR:
                return Log.e(tag, message);
        }
        return 0;
    }
    //======================================================================
    // Level
    //======================================================================

    public enum Level {
        VERBOSE(Log.VERBOSE),
        DEBUG(Log.DEBUG),
        INFO(Log.INFO),
        WARN(Log.WARN),
        ERROR(Log.ERROR),
        ASSERT(Log.ASSERT);

        int level;

        Level(int level) {
            this.level = level;
        }
    }
}
