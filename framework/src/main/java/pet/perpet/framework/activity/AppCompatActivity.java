package pet.perpet.framework.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;

import pet.perpet.framework.channel.ChannelStore;
import pet.perpet.framework.util.PermissionChecker;

public class AppCompatActivity extends androidx.appcompat.app.AppCompatActivity {

    //======================================================================
    // Variables
    //======================================================================

    private final AppCompatActivityDelegate mDelegate = new AppCompatActivityDelegate(this);

    private boolean mForegroundActivity;

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mDelegate.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mDelegate.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDelegate.onStart();
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDelegate.onResume();
        mForegroundActivity = true;
    }

    @Override
    protected void onPostResume() {
        try {
            super.onPostResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDelegate.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mForegroundActivity = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDelegate.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        mDelegate.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDelegate.log("onConfigurationChanged : "+newConfig);
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public void log(String message){
        mDelegate.log(message);
    }

    public ChannelStore getStore(){
        return mDelegate.getStore();
    }

    public final boolean isForegroundActivity() {
        return mForegroundActivity;
    }

    public final PermissionChecker getPermissionChecker() {
        return mDelegate.getPermissionChecker();
    }
}
