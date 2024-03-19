package pet.perpet.framework.activity.internal;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

/*
 * @author lsh
 * @since 2017. 4. 5.
*/
public interface ActivityCycle {

    void onCreate(Bundle savedInstanceState);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void onCreate(Bundle savedInstanceState, PersistableBundle persistentState);

    void onSaveInstanceState(Bundle outState);

    void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPostResume();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
