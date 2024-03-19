package pet.perpet.framework.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


abstract class DialogFragmentDelegate<P extends UseViewModel> extends FragmentDelegate<P> {

    //======================================================================
    // Variables
    //======================================================================

    DialogFragment.OnDismissCallback mDismissCallback;

    DialogFragment.OnDismissCallback2 mDismissCallback2;

    DialogFragment.OnDismissCallback3 mDismissCallback3;

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDismissCallback = null;
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public void dismiss(@NonNull Bundle result) {
        Fragment fragment = getFragment();
        if (fragment != null && fragment instanceof androidx.fragment.app.DialogFragment) {
            ((androidx.fragment.app.DialogFragment) fragment).dismiss();
        }
        if (mDismissCallback != null) {
            mDismissCallback.onDismiss(result);
        }
    }

    public void dismissResultViewModel() {
        Fragment fragment = getFragment();
        if (fragment != null && fragment instanceof androidx.fragment.app.DialogFragment) {
            ((androidx.fragment.app.DialogFragment) fragment).dismiss();
        }
        if (mDismissCallback2 != null) {
            mDismissCallback2.onDismiss(getViewModel());
        }
    }

    public <R> void dismissResult(R result) {
        Fragment fragment = getFragment();
        if (fragment != null && fragment instanceof androidx.fragment.app.DialogFragment) {
            ((androidx.fragment.app.DialogFragment) fragment).dismiss();
        }
        if (mDismissCallback3 != null) {
            mDismissCallback3.onDismiss(result);
        }
    }

    public <R> void dismissResult(R result, boolean dismiss) {
        Fragment fragment = getFragment();
        if (dismiss == true && fragment != null && fragment instanceof androidx.fragment.app.DialogFragment) {
            ((DialogFragment) fragment).dismiss();
        }
        if (mDismissCallback3 != null) {
            mDismissCallback3.onDismiss(result);
        }
    }


    public void setDismissCallback(DialogFragment.OnDismissCallback dismissCallback) {
        mDismissCallback = dismissCallback;
    }

    public void setDismissCallback2(pet.perpet.framework.fragment.DialogFragment.OnDismissCallback2 mDismissCallback2) {
        this.mDismissCallback2 = mDismissCallback2;
    }

    public void setmDismissCallback3(DialogFragment.OnDismissCallback3 mDismissCallback3) {
        this.mDismissCallback3 = mDismissCallback3;
    }
}
