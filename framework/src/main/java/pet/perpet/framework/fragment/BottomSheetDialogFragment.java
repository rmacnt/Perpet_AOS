package pet.perpet.framework.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pet.perpet.framework.channel.ChannelStore;


/*
 * @author lsh
 * @since 2017. 4. 7.
 */
public abstract class BottomSheetDialogFragment<P extends UseViewModel> extends com.google.android.material.bottomsheet.BottomSheetDialogFragment implements DialogFragmentSupport<P> {

    //======================================================================
    // Variables
    //======================================================================

    private final DialogFragmentDelegate<P> mDelegate = new DialogFragmentDelegate<P>() {
        @Override
        protected Fragment getFragment() {
            return BottomSheetDialogFragment.this;
        }
    };

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState, this::hashCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDelegate.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDelegate.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mDelegate.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mDelegate.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Modify 2016. 4. 18. lsh Fragment.onCreate() 보다 먼저 호출되는 경우 있음
        mDelegate.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDelegate.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDelegate.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
    }

    @Deprecated
    @Override
    public final P getPresenter() {
        return mDelegate.getViewModel();
    }


    @SuppressWarnings("unchecked")
    @Override
    public final P getViewmodel() {
        return mDelegate.getViewModel();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public final boolean isFragmentActivated() {
        return mDelegate.isFragmentActivated();
    }

    @Override
    public void popBackStackImmediate() {
        mDelegate.popBackStackImmediate();
    }

    @Override
    public void dismiss(@NonNull Bundle result) {
        mDelegate.dismiss(result);
    }


    @Override
    public void dismissToResultViewModel() {
        mDelegate.dismissResultViewModel();
    }

    @Override
    public void setDismissCallback(DialogFragment.OnDismissCallback2 dismissCallback) {
        mDelegate.setDismissCallback2(dismissCallback);
    }

    @Override
    public void setDismissCallback(DialogFragment.OnDismissCallback dismissCallback) {
        mDelegate.setDismissCallback(dismissCallback);
    }

    @Override
    public void setDismissCallback(DialogFragment.OnDismissCallback3 dismissCallback) {
        mDelegate.setmDismissCallback3(dismissCallback);
    }

    @Override
    public <R> void dismissToResult(R r) {
        mDelegate.dismissResult(r);
    }

    @Override
    public <R> void dismissToResult(R r, boolean dismiss) {
        mDelegate.dismissResult(r, dismiss);
    }

    @Override
    public ChannelStore getChannelStore() {
        return mDelegate.getStore();
    }
}

