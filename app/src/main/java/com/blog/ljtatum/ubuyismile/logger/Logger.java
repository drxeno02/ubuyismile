/*
 * Copyright (c) 2014-present, ZTRIP. All rights reserved.
 */

package com.blog.ljtatum.ubuyismile.logger;

import android.util.Log;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.constants.Constants;


/**
 * Created by leonard on 11/13/2015.
 * Helper class to print logs to console
 */
public class Logger {
    /**
     * Helper method for logging e-verbose
     *
     * @param tag
     * @param msg
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
     * @param tag
     * @param msg
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
     * @param tag
     * @param msg
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
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (!FrameworkUtils.checkIfNull(msg)) {
            if (Constants.DEBUG) {
                Log.v(tag, msg);
            }
        }
    }
}
