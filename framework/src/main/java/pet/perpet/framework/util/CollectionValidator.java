package pet.perpet.framework.util;

import java.util.ArrayList;
import java.util.List;

public final class CollectionValidator {

    //======================================================================
    // Constants
    //======================================================================

    private static final int NO_POSITION = -1;

    //======================================================================
    // Public Methods
    //======================================================================

    public static boolean isValid(ArrayList<?> list1, ArrayList<?> list2) {
        boolean use = false;
        if (isValidInternal(list1, 0) && isValidInternal(list2, 0) && list1.size() == list2.size()) {
            use = true;
        }
        return use;
    }

    public static boolean isValid(ArrayList<?> list) {
        return isValidInternal(list, 0);
    }

    public static boolean isValid(ArrayList<?> list, int position) {
        return isValidInternal(list, position);
    }

    public static boolean isValid(List<?> list1, List<?> list2) {
        boolean use = false;
        if (isValidInternal(list1, 0) && isValidInternal(list2, 0) && list1.size() == list2.size()) {
            use = true;
        }
        return use;
    }

    public static boolean isValid(List<?> list) {
        return isValidInternal(list, 0);
    }

    public static boolean isValid(List<?> list, int position) {
        return isValidInternal(list, position);
    }

    public static int getSize(ArrayList<?> list) {
        return list != null ? list.size() : 0;
    }

    public static int getSize(List<?> list) {
        return list != null ? list.size() : 0;
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private static boolean isValidInternal(List<?> list, int position) {
        boolean use = false;
        if (position > NO_POSITION) {
            if (list != null) {
                if (list.size() > position) {
                    use = true;
                }
            }
        }
        return use;
    }

    private static boolean isValidInternal(ArrayList<?> list, int position) {
        boolean use = false;
        if (position > NO_POSITION) {
            if (list != null) {
                if (list.size() > position) {
                    use = true;
                }
            }
        }
        return use;
    }
}
