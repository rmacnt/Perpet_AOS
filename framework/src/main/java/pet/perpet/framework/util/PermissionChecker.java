package pet.perpet.framework.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

public final class PermissionChecker implements ActivityCompat.OnRequestPermissionsResultCallback {

    //======================================================================
    // Variables
    //======================================================================

    private Activity mActivity;

    private OnSupportRequestPermissionsResultCallback mCallback;

    //======================================================================
    // Constructor
    //======================================================================

    public PermissionChecker(@NonNull Activity activity) {
        mActivity = activity;
    }

    //======================================================================
    // Public Methods
    //======================================================================

    /**
     * 권한 요청 함수
     * @param permissionList {@link Manifest.permission} , {@link Manifest.permission} 에서 정의된 값으로 ArrayList 정의
     * @param callback {@link OnSupportRequestPermissionsResultCallback}
     */
    public void makeRequestPermissions(ArrayList<String> permissionList, OnSupportRequestPermissionsResultCallback callback) {
        ArrayList<String> requestList = new ArrayList<>();
        for (String permission : permissionList) {
            if (isPermissionGranted(mActivity, permission) == false) {
                requestList.add(permission);
            }
        }

        requestPermissions(requestList.size() > 0 ? requestList.toArray(new String[requestList.size()]) : null
                , 1, callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mCallback != null) {
            mCallback.onRequestPermissionsResult(mActivity, requestCode, permissions, grantResults);
        }
    }

    public void release() {
        mCallback = null;
        mActivity = null;
    }

    //======================================================================
    // Private Methods
    //======================================================================

    /**
     * 권환획득 여부 체크
     *
     * @param activity   {@link Activity}
     * @param permission {@link Manifest.permission} , {@link Manifest.permission} 에서 정의된 값 사용 권장
     *
     * @return 권환획득이면 true 아니면 false
     */
    private boolean isPermissionGranted(@NonNull Activity activity, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * {@link ActivityCompat#requestPermissions(Activity, String[], int) 참조} 요청하면 즉시 {@link ActivityCompat.OnRequestPermissionsResultCallback 응답} 받을수 있게 처리
     *
     * @param permissions {@link ActivityCompat#requestPermissions(Activity, String[], int)}
     * @param requestCode {@link ActivityCompat#requestPermissions(Activity, String[], int)}
     * @param callback    {@link OnSupportRequestPermissionsResultCallback}
     */
    private void requestPermissions(@NonNull String[] permissions, int requestCode, @NonNull OnSupportRequestPermissionsResultCallback callback) {
        mCallback = callback;
        mCallback.mRequestCode = requestCode;

        if (permissions != null) {
            ActivityCompat.requestPermissions(mActivity, permissions, requestCode);
        } else {
            if (mCallback != null) {
                mCallback.onResult(new boolean[]{true}, new boolean[]{true});
            }
        }
    }

    //======================================================================
    // OnSupportRequestPermissionsResultCallback
    //======================================================================

    public static abstract class OnSupportRequestPermissionsResultCallback {

        int mRequestCode;

        void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            try {
                if (mRequestCode != requestCode) {
                    throw new MissingFormatArgumentException("request code not match");
                }

                boolean grants[] = new boolean[permissions.length];
                boolean showRequestPermissions[] = new boolean[permissions.length];

                for (int i = 0; i < grantResults.length; i++) {
                    grants[i] = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    showRequestPermissions[i] = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                }

                onResult(grants, showRequestPermissions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 권환 획득 여부
         *
         * @param grants                 권한을 획득하면 true 아니면 false
         * @param showRequestPermissions 팝업 표시 여부
         */
        public abstract void onResult(boolean[] grants, boolean[] showRequestPermissions);
    }
}
