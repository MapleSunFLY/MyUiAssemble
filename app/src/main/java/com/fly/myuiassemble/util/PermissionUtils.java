package com.fly.myuiassemble.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 包    名 : com.fly.myuiassemble
 * 作    者 : FLY
 * 创建时间 : 2019/10/16
 * 描述:
 */
public class PermissionUtils {
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    public static boolean requestPhoneState(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }


    public static boolean requestLocation(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    public static boolean requestStorage(Activity activity) {
        if (afterM()) {
            boolean write;
            boolean read;
            int writePermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                write = false;
            } else {
                write = true;
            }

            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                read = false;
            } else {
                read = true;
            }

            return write && read;
        }
        return true;
    }

    public static boolean requestCamera(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    public static boolean afterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
