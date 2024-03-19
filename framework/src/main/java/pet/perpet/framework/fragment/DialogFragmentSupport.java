package pet.perpet.framework.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;


public interface DialogFragmentSupport<P extends UseViewModel> extends FragmentSupport<P> {
    void dismiss(@NonNull Bundle result);

    void dismissToResultViewModel();

    <R> void dismissToResult(R r);

    <R> void dismissToResult(R r, boolean dismiss);

    void setDismissCallback(pet.perpet.framework.fragment.DialogFragment.OnDismissCallback dismissCallback);

    void setDismissCallback(pet.perpet.framework.fragment.DialogFragment.OnDismissCallback2 dismissCallback);

    void setDismissCallback(pet.perpet.framework.fragment.DialogFragment.OnDismissCallback3 dismissCallback);
}
