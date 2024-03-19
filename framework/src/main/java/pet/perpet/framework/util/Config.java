package pet.perpet.framework.util;

public final class Config {

    private static boolean sLogEnable;

    public static boolean isLogEnable() {
        return sLogEnable;
    }

    public static void setLogEnable(boolean logEnable) {
        sLogEnable = logEnable;
    }
}
