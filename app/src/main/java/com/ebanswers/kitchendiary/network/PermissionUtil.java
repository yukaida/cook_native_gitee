package com.ebanswers.kitchendiary.network;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

import com.ebanswers.kitchendiary.common.CommonApplication;


/**
 * @author caixd
 * @date 2019/8/14
 * PS:
 */
public class PermissionUtil {

    public static boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(CommonApplication.getInstance(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(CommonApplication.getInstance(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(CommonApplication.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
