package com.blog.ljtatum.ubuyismile.utils;

import java.util.Random;

/**
 * Created by LJTat on 2/14/2018.
 */

public class ScoreUtils {
    private static final String TAG = ScoreUtils.class.getSimpleName();

    /**
     * Method is used to update Happiness Score
     *
     * @param isHappy True to increase score, otherwise decrease score
     */
    public static void updateHappinessScore(boolean isHappy) {
        Random rand = new Random();
        int points;
        if (isHappy) {
            points = rand.nextInt(1) + 1;
//            SharedPref sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
//            sharedPref.setPref(Constants.NOTIFICATION_ENDPOINT_ARN, response.result.pushNotificationToken);
//            String token = sharedPref.getStringPref(Constants.NOTIFICATION_GCM_TOKEN, "");
        } else {
            points = rand.nextInt(1) + 2;
        }
    }
}
