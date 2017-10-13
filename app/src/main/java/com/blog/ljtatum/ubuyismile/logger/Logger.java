package com.blog.ljtatum.ubuyismile.logger;

import android.util.Log;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.constants.Constants;

/**
 * Created by leonard on 9/22/2017.
 */

public class Logger {

    /**
     * Helper method for logging e-verbose
     *
     * @param tag The tag of the fragment you want to log
     * @param msg The message to log
     */
    public static void e(String tag, String msg) {
        if (!FrameworkUtils.checkIfNull(msg)) {
            if (Constants.DEBUG) {
                Log.e(tag, msg);
            }
        }
    }

    /**
     * Helper method for logging d-verbose
     *
     * @param tag The tag of the fragment you want to log
     * @param msg The message to log
     */
    public static void d(String tag, String msg) {
        if (!FrameworkUtils.checkIfNull(msg)) {
            if (Constants.DEBUG) {
                Log.d(tag, msg);
            }
        }
    }

    /**
     * Helper method for logging i-verbose
     *
     * @param tag The tag of the fragment you want to log
     * @param msg The message to log
     */
    public static void i(String tag, String msg) {
        if (!FrameworkUtils.checkIfNull(msg)) {
            if (Constants.DEBUG) {
                Log.i(tag, msg);
            }
        }
    }

    /**
     * Helper method for logging v-verbose
     *
     * @param tag The tag of the fragment you want to log
     * @param msg The message to log
     */
    public static void v(String tag, String msg) {
        if (!FrameworkUtils.checkIfNull(msg)) {
            if (Constants.DEBUG) {
                Log.v(tag, msg);
            }
        }
    }

    /**
     * Helper method for logging wtf-verbose
     *
     * @param tag The tag of the fragment you want to log
     * @param msg The message to log
     */
    public static void wtf(String tag, String msg) {
        if (!FrameworkUtils.checkIfNull(msg)) {
            if (Constants.DEBUG) {
                Log.wtf(tag, msg);
            }
        }
    }
}
