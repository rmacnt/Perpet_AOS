package pet.perpet.framework.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public final class DialogFragmentNavigation {

    //======================================================================
    // Public Methods
    //======================================================================

    public static void showDialogFragment(@NonNull FragmentManager manager, @NonNull Class fragment, @Nullable Bundle arguments, @Nullable DialogFragment.OnDismissCallback onDismissCallbackListener) {
        showDialogFragmentInternal(manager, fragment, fromBundle(arguments), onDismissCallbackListener);
    }

    public static void showDialogFragment(@NonNull FragmentManager manager, @NonNull Class fragment, @Nullable Bundle arguments) {
        showDialogFragmentInternal(manager, fragment, fromBundle(arguments), null);
    }

    public static void showDialogFragment2(@NonNull FragmentManager manager, @NonNull Class fragment, @Nullable Bundle arguments, @Nullable DialogFragment.OnDismissCallback2 onDismissCallbackListener) {
        showDialogFragmentInternal2(manager, fragment, fromBundle(arguments), onDismissCallbackListener);
    }

    public static <R> void showDialogFragment3(@NonNull FragmentManager manager, @NonNull Class fragment, @Nullable Bundle arguments, @Nullable DialogFragment.OnDismissCallback3<R> onDismissCallbackListener) {
        showDialogFragmentInternal2(manager, fragment, fromBundle(arguments), onDismissCallbackListener);
    }

    public static <R> void dispatchDismissToResult(Fragment fragment, R r, boolean dismiss) {
        if (fragment instanceof DialogFragmentSupport) {
            ((DialogFragmentSupport) fragment).dismissToResult(r, dismiss);
        }
    }

    public static <R> void dispatchDismissToResult(Fragment fragment, R r) {
        if (fragment instanceof DialogFragmentSupport) {
            ((DialogFragmentSupport) fragment).dismissToResult(r);
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private static Bundle fromBundle(Bundle bundle) {
        if (bundle == null) {
            return new Bundle();
        }
        return bundle;
    }

    private static void showDialogFragmentInternal(@NonNull FragmentManager manager, @NonNull Class fragment, @Nullable Bundle arguments, @Nullable DialogFragment.OnDismissCallback onDismissCallbackListener) {
        try {
            String tag = fragment.getName();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment prev = manager.findFragmentByTag(tag);
            if (prev != null) {
                transaction.remove(prev).commit();
            }

            androidx.fragment.app.DialogFragment dialogFragment = (androidx.fragment.app.DialogFragment) fragment.newInstance();

            if (dialogFragment instanceof DialogFragmentSupport) {
                ((DialogFragmentSupport) dialogFragment).setDismissCallback(onDismissCallbackListener);
            }

            dialogFragment.setArguments(arguments);
            dialogFragment.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showDialogFragmentInternal2(@NonNull FragmentManager manager, @NonNull Class fragment, @Nullable Bundle arguments, @Nullable DialogFragment.OnDismissCallback2 onDismissCallbackListener) {
        try {
            String tag = fragment.getName();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment prev = manager.findFragmentByTag(tag);
            if (prev != null) {
                transaction.remove(prev).commit();
            }

            androidx.fragment.app.DialogFragment dialogFragment = (androidx.fragment.app.DialogFragment) fragment.newInstance();

            if (dialogFragment instanceof DialogFragmentSupport) {
                ((DialogFragmentSupport) dialogFragment).setDismissCallback(onDismissCallbackListener);
            }

            dialogFragment.setArguments(arguments);
            dialogFragment.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <R> void showDialogFragmentInternal2(@NonNull FragmentManager manager, @NonNull Class fragment, @Nullable Bundle arguments, @Nullable DialogFragment.OnDismissCallback3<R> onDismissCallbackListener) {
        try {
            String tag = fragment.getName();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment prev = manager.findFragmentByTag(tag);
            if (prev != null) {
                transaction.remove(prev).commit();
            }

            androidx.fragment.app.DialogFragment dialogFragment = (androidx.fragment.app.DialogFragment) fragment.newInstance();

            if (dialogFragment instanceof DialogFragmentSupport) {
                ((DialogFragmentSupport) dialogFragment).setDismissCallback(onDismissCallbackListener);
            }

            dialogFragment.setArguments(arguments);
            dialogFragment.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
