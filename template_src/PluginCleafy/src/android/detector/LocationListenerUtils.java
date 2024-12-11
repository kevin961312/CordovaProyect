package com.cleafy.mobile.cordova;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.core.content.ContextCompat;
import com.cleafy.mobile.detection.agent.Cleafy;
import org.apache.cordova.CallbackContext;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

class LocationListenerUtils {

    @ChecksSdkIntAtLeast(codename = "S")
    private static boolean canUseFusedProvider() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    public static void registerLocationListener(
            Context context,
            String requestedProvider,
            long minTime,
            float minDistance,
            CallbackContext callbackContext
    ) {
        LocationManager locationManager = context.getSystemService(LocationManager.class);
        if (locationManager == null) {
            callbackContext.error("LocationManager not found.");
            return;
        }

        LocationListener locationListener = Cleafy.getLocationListener();
        if (locationListener == null) {
            callbackContext.error("Agent has not been initialized yet.");
            return;
        }

        if (ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            callbackContext.error("ACCESS_COARSE_LOCATION permission required.");
            return;
        }

        String provider = null;
        if (requestedProvider != null) {
            if (!locationManager.isProviderEnabled(requestedProvider)) {
                callbackContext.error("Requested provider is unavailable.");
                return;
            }
            provider = requestedProvider;
        } else {
            if (canUseFusedProvider() && locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER)) {
                provider = LocationManager.FUSED_PROVIDER;
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                provider = LocationManager.GPS_PROVIDER;
            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER;
            } else {
                callbackContext.error("No suitable Location Provider found.");
            }
        }

        //val minTime = arguments ?.get(Parameters.MIN_TIME) ?.toLongOrNull() ?:2000L
        //val minDistance = arguments ?.get(Parameters.MIN_DISTANCE) ?.toFloatOrNull() ?:500f

        locationManager.requestLocationUpdates(provider, minTime, minDistance, locationListener);

        callbackContext.success();
    }

    private LocationListenerUtils() {
    }
}
