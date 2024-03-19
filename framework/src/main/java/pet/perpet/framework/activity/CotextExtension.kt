package com.taling.android.app.activity

import android.content.Context
import pet.perpet.framework.activity.AppCompatActivity
import pet.perpet.framework.util.PermissionChecker
import java.util.*


fun Context?.supportCheckPermission(
    permission: ArrayList<String>,
    callback: (
        grants: BooleanArray?,
        showRequestPermissions: BooleanArray?
    ) -> Unit
) {
    if (this is AppCompatActivity) {
        permissionChecker.makeRequestPermissions(
            permission,
            object : PermissionChecker.OnSupportRequestPermissionsResultCallback() {
                override fun onResult(
                    grants: BooleanArray?,
                    showRequestPermissions: BooleanArray?
                ) {
                    callback(grants, showRequestPermissions)
                }
            })
    }
}