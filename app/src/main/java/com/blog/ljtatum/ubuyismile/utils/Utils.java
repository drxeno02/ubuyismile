package com.blog.ljtatum.ubuyismile.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.BuildConfig;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.logger.Logger;

/**
 * Created by leonard on 9/22/2017.
 */

public class Utils {
    private static final String TAG_MEMORY = "debug-memory";
    private static final String TAG_INFO = "debug-info";

    // device attributes
    private static final String OS_VERSION = "os.version";
    private static final String CONNECTION_TYPE_WIFI = "WIFI";
    private static final String CONNECTION_TYPE_DATA = "MOBILE DATA";
    private static final String NO_CONNECTION = "No connection";

    /**
     * Method is used for printing the memory usage. This is used
     * only for verbosity mode
     *
     * @param name  fragment or class simple name
     */
    @SuppressWarnings({"ConstantConditions", "PointlessBooleanExpression"})
    public static void printMemory(@NonNull String name) {
        if (Constants.DEBUG && Constants.DEBUG_VERBOSE) {
            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long usedMemory = totalMemory - freeMemory;

            // note that you cannot divide a long by a long value, this
            // refers to (long/long - long) operation giving a long result of 0
            long percentFree = (long) ((float) freeMemory / totalMemory * 100);
            long percentUsed = (long) ((float) usedMemory / totalMemory * 100);

            if (percentFree <= 2) {
                Logger.e(TAG_MEMORY, "===== MEMORY WARNING :: Available memory is low! Please add " +
                        "'art-' to your regex tag to see that gc is freeing up more available memory =====");
            }

            // printing memory details
            Logger.d(TAG_MEMORY, "===== Memory recorded from " + name + " :: " +
                    "MAX_MEMORY:" + Runtime.getRuntime().maxMemory() +
                    "  // FREE_MEMORY:" + freeMemory + " (" + percentFree + "% free)" +
                    "  // TOTAL_MEMORY:" + totalMemory +
                    "  // USED_MEMORY:" + usedMemory + " (" + percentUsed + "% used) =====");
        }
    }

    /**
     * Method is used to print device and application information. This is
     * used only for verbosity mode
     *
     * @param context
     * @param activity
     */
    @SuppressWarnings("PointlessBooleanExpression")
    public static void printInfo(@NonNull Context context, @NonNull Activity activity) {
        if (Constants.DEBUG && Constants.DEBUG_VERBOSE) {
            // detect internet connection
            String connectionType = "";
            final ConnectivityManager connMgr = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
            if (!FrameworkUtils.checkIfNull(activeNetwork)) {
                // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    connectionType = CONNECTION_TYPE_WIFI;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    connectionType = CONNECTION_TYPE_DATA;
                }
            } else {
                // not connected to the internet
                connectionType = NO_CONNECTION;
            }

            // determine phone carrier
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String carrierName = manager.getNetworkOperatorName();

            // get display metrics
            DisplayMetrics displaymetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            try {
                Logger.i(TAG_INFO, "===== DEVICE INFORMATION =====" +
                        "\nManufacturer: " + Build.MANUFACTURER +
                        "\nRegistrationModel: " + Build.MODEL +
                        "\nDevice/Product Id: " + Build.PRODUCT +
                        "\nCarrier: " + carrierName +
                        "\nOS Version: " + System.getProperty(OS_VERSION) +
                        "\nAPI Level: " + String.valueOf(Build.VERSION.SDK_INT) +
                        "\nScreen size (width/height): " +
                        displaymetrics.widthPixels + "/" +
                        displaymetrics.heightPixels +
                        "\n===== APP INFORMATION =====" +
                        "\nApp Version: " + BuildConfig.VERSION_NAME +
                        "\nBuild Type: " + BuildConfig.BUILD_TYPE +
                        "\nVersion Code: " + BuildConfig.VERSION_CODE +
                        "\nPackage Name: " + context.getPackageName() +
                        "\nFlavor: " + BuildConfig.FLAVOR +
                        "\nGoogle Map API Version: " + context.getPackageManager()
                        .getPackageInfo("com.google.android.apps.maps", 0).versionName +
                        "\nInternet Connection: " + connectionType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
