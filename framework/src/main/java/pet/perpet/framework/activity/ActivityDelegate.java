package pet.perpet.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


import java.util.ArrayList;
import java.util.Arrays;

import pet.perpet.framework.activity.internal.ActivityCycle;
import pet.perpet.framework.channel.ChannelStore;
import pet.perpet.framework.util.PermissionChecker;

/*
 * @author lsh
 * @since 2016. 4. 14.
 */
class ActivityDelegate implements ActivityCycle, ActivityCompat.OnRequestPermissionsResultCallback {

    //======================================================================
    // Constants
    //======================================================================

    private final static String LOG_FORMAT = "[%s]: %s";

    //======================================================================
    // Variables
    //======================================================================

    private static ArrayList<Activity> sActivities = new ArrayList<>();

    Activity mActivity;

    PermissionChecker mPermissionChecker;

    private ChannelStore mStore = new ChannelStore();

    //======================================================================
    // Constructor
    //======================================================================

    public ActivityDelegate(@NonNull Activity activity) {
        mActivity = activity;
    }

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sActivities.add(mActivity);
        mPermissionChecker = new PermissionChecker(mActivity);
        cycle("onCreate -> savedInstanceState : " + savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        cycle("onCreate -> savedInstanceState : " + savedInstanceState + " persistentState : " + persistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        cycle("onSaveInstanceState -> outState : " + outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        cycle("onSaveInstanceState -> outState : " + outState + " persistentState : " + outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        cycle("onRestoreInstanceState -> savedInstanceState : " + savedInstanceState);
    }

    @Override
    public void onStart() {
        cycle("onStart");
    }

    @Override
    public void onResume() {
        cycle("onResume");
    }

    @Override
    public void onPostResume() {
        cycle("onPostResume");
    }

    @Override
    public void onStop() {
        cycle("onStop");
    }

    @Override
    public void onDestroy() {
        cycle("onDestroy");
        sActivities.remove(mActivity);
        mActivity = null;
        if (mPermissionChecker != null) {
            mPermissionChecker.release();
            mPermissionChecker = null;
        }
        mStore.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cycle("onRequestPermissionsResult -> requestCode : " + requestCode + " requestCode : " + requestCode + " grantResults : " + Arrays.toString(grantResults));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        cycle("onActivityResult -> requestCode : " + requestCode + " requestCode : " + requestCode + " data : " + data);
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public void log(String message){
        cycle(message);
    }

    public ChannelStore getStore() {
        return mStore;
    }

    public PermissionChecker getPermissionChecker() {
        return mPermissionChecker;
    }

    public static ArrayList<Activity> getActivities() {
        return sActivities;
    }

    //======================================================================
    // Methods
    //======================================================================

    void cycle(String message) {
        if (mActivity == null) {
            return;
        }
    }

    Activity getActivity() {
        return mActivity;
    }
}
