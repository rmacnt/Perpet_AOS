package pet.perpet.framework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;

class AppCompatActivityDelegate extends ActivityDelegate {

    //======================================================================
    // Constructor
    //======================================================================

    public AppCompatActivityDelegate(@NonNull Activity activity) {
        super(activity);
    }

    //======================================================================
    // Override Methods
    //======================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
