package com.blog.ljtatum.ubuyismile.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.app.framework.constants.Constants;
import com.app.framework.sharedpref.SharedPref;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.logger.Logger;

import java.util.Random;

/**
 * Created by LJTat on 2/14/2018.
 */

public class HappinessUtils {
    private static final String TAG = HappinessUtils.class.getSimpleName();

    public static final String HAPPINESS_SCORE = "key_happiness";
    private static SharedPref mSharedPref;

    public HappinessUtils(Context context) {
        mSharedPref = new SharedPref(context, Constants.PREF_FILE_NAME);
    }

    /**
     * Method is used to update Happiness Score
     *
     * @param isHappy True to increase score, otherwise decrease score
     */
    public static void trackHappiness(@Nullable Boolean isHappy) {
        if (FrameworkUtils.checkIfNull(mSharedPref)) {
            Logger.e(TAG, "ERROR - HappinessUtils not initialized");
            return;
        }

        Random rand = new Random();
        int points;
        int score = mSharedPref.getIntPref(HAPPINESS_SCORE, -1);

        if (FrameworkUtils.checkIfNull(isHappy)) {
            if (score == -1) {
                // default score
                mSharedPref.setPref(HAPPINESS_SCORE, 90);
            }
        } else {
            if (isHappy) {
                points = rand.nextInt(1) + 1;
                int updatedScore = (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) + points) > 100 ? 100 :
                        (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) + points);
                mSharedPref.setPref(HAPPINESS_SCORE, updatedScore);
            } else {
                points = rand.nextInt(1) + 2;
                int updatedScore = (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) - points) <= 0 ? 0 :
                        (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) - points);
                mSharedPref.setPref(HAPPINESS_SCORE, updatedScore);
            }
        }

        // print results
        Logger.i(TAG, "Happiness Score - " + mSharedPref.getIntPref(HAPPINESS_SCORE, -1));
    }
}
