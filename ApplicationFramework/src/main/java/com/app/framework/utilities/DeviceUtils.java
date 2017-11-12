/*
 * Copyright (c) 2014-present, ZTRIP. All rights reserved.
 */

package com.app.framework.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import com.app.framework.constants.Constants;

/**
 * Created by leonard on 9/29/2015.
 * Utility class used to perform device operations. These include hiding keyboards, retrieving network
 * status, checking if location services are enabled, and converting pixels to Density pixels.
 */
public class DeviceUtils {

    /**
     * Method is used to show virtual keyboard
     *
     * @param context Interface to global information about an application environment
     */
    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Method is used to hide virtual keyboard
     *
     * @param context Interface to global information about an application environment
     * @param binder  Base interface for a remotable object, the core part of a lightweight remote
     *                procedure call mechanism designed for high performance when performing
     *                in-process and cross-process calls
     */
    public static void hideKeyboard(Context context, IBinder binder) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }

    /**
     * Method is used to check if device has location services enabled
     *
     * @param context Interface to global information about an application environment
     * @return True if location services enable
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isLocationServiceEnabled(Context context) {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !FrameworkUtils.isStringEmpty(locationProviders);
        }
    }

    /**
     * Method is used to convert dp to px
     *
     * @param px      The pixel value to convert to dp
     * @param context Interface to global information about an application environment
     * @return Converted dp value
     */
    public static float convertPixelToDp(Context context, final float px) {
        return !FrameworkUtils.checkIfNull(px / context.getResources().getDisplayMetrics().density) ?
                (px / context.getResources().getDisplayMetrics().density) : 0f;
    }

    /**
     * Method is used to convert pixels to dp
     *
     * @param dp      The dp value to convert to pixel
     * @param context Interface to global information about an application environment
     * @return Converted pixel value
     */
    public static float convertDpToPixels(Context context, final float dp) {
        return !FrameworkUtils.checkIfNull(dp * context.getResources().getDisplayMetrics().density) ?
                (dp * context.getResources().getDisplayMetrics().density) : 0f;
    }

    /**
     * Method is used to get the device width in pixels
     *
     * @return Return the current display metrics (Width) that are in effect for this resource object
     */
    public static int getDeviceWidthPx() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * Method is used to get the device height in pixels
     *
     * @return Return the current display metrics (Height) that are in effect for this resource object
     */
    public static int getDeviceHeightPx() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * Method is used to detect is screen size is extra large size or not. Extra
     * large screen size is defined as any device above 5.5 inches
     *
     * @param activity An activity is a single, focused thing that the user can do
     * @return True if device screen size larger than 5.5 inches
     */
    public static boolean isXLargeScreen(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        double x = Math.pow(displaymetrics.widthPixels / displaymetrics.xdpi, 2);
        double y = Math.pow(displaymetrics.heightPixels / displaymetrics.ydpi, 2);

        // calculate screen size in inches
        double screenInches = Math.sqrt(x + y);
        return (screenInches >= Constants.XLARGE_SCREEN_SIZE_DEFINITIION);
    }
}
