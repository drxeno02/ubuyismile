package com.blog.ljtatum.ubuyismile.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.app.framework.constants.Constants;
import com.app.framework.sharedpref.SharedPref;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * Created by LJTat on 2/14/2018.
 */

public class HappinessUtils {
    private static final String TAG = HappinessUtils.class.getSimpleName();

    public static final String EVENT_APP_LAUNCH = "key_event_app_launch"; // can be negative Happiness
    public static final String EVENT_CONTENT_COUNTER = "key_event_content_counter";
    public static final String EVENT_CONTENT_ITEM_DETAIL_COUNTER = "key_event_content_item_detail_counter";
    public static final String EVENT_ABOUT_COUNTER = "key_event_about_counter";
    public static final String EVENT_ABOUT_SHARE_COUNTER = "key_event_share_counter";
    public static final String EVENT_SEARH_COUNTER = "key_event_search_counter";
    public static final String EVENT_ITEM_FEEDBACK_COUNTER = "key_event_item_feedback_counter";

    private static final String HAPPINESS_SCORE = "key_happiness";
    private static final String APP_LAUNCH_TIMESTAMP = "key_app_launch_timestamp";

    private static final int MAX_NUM_DAYS_LOGGED_OUT = 5; // days
    private static final int CONTENT_VISIT_THRESHOLD = 50; // threshold for visiting content
    private static final int ITEM_DETAIL_VISIT_THRESHOLD = 25; // threshold for visiting item details
    private static final int ABOUT_VISIT_THRESHOLD = 10; // threshold for visiting About
    private static final int ABOUT_SHARE_THRESHOLD = 1; // threshold for sharing from About
    private static final int SEARCH_THRESHOLD = 25; // threshold for searching items
    private static final int FEEDBACK_VISIT_THRESHOLD = 10; // threshold for visiting feedback


    private static SharedPref mSharedPref;

    public HappinessUtils(Context context) {
        mSharedPref = new SharedPref(context, Constants.PREF_FILE_NAME);
    }

    /**
     * Method is used to update Happiness Score
     * @param event
     */
    public static void trackHappiness(@NonNull String event) {
        if (FrameworkUtils.checkIfNull(mSharedPref)) {
            Logger.e(TAG, "ERROR - HappinessUtils not initialized");
            return;
        }

        Random rand = new Random();
        boolean isHappinessScoreChanged = false;
        int points;
        int score = mSharedPref.getIntPref(HAPPINESS_SCORE, -1);

        if (event.equalsIgnoreCase(EVENT_APP_LAUNCH)) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH);
            String timestamp = formatter.format(calendar.getTime());

            if (score == -1) {
                // default score
                mSharedPref.setPref(HAPPINESS_SCORE, 80);
            } else {
                // use timestamp to determine positive or negative Happiness score
                int daysBetweenDates = FrameworkUtils.getDaysBetweenDates(timestamp,
                        mSharedPref.getStringPref(APP_LAUNCH_TIMESTAMP, timestamp));
                Logger.i(TAG, "Days between dates: " + daysBetweenDates);
                // counter representing sets of maximum logged days
                int counter = 1;
                while ((daysBetweenDates / counter) / MAX_NUM_DAYS_LOGGED_OUT >= 1) {
                    counter++;
                }
                // prepare for negative score
                if (counter > 1) {
                    points = (rand.nextInt(1) + 1) * counter; // larger penalty
                    int updatedScore = (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) - points) <= 0 ? 0 :
                            (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) - points);
                    mSharedPref.setPref(HAPPINESS_SCORE, updatedScore);
                }
            }
            // time stamp app launch
            mSharedPref.setPref(APP_LAUNCH_TIMESTAMP, FrameworkUtils.getCurrentDateTime());
        } else if (event.equalsIgnoreCase(EVENT_CONTENT_COUNTER)) {
            // increment event
            mSharedPref.setPref(EVENT_CONTENT_COUNTER, (mSharedPref.getIntPref(EVENT_CONTENT_COUNTER, 0) + 1));
            if (mSharedPref.getIntPref(EVENT_CONTENT_COUNTER, 0) >= CONTENT_VISIT_THRESHOLD) {
                isHappinessScoreChanged = true;
                // reset value
                mSharedPref.setPref(EVENT_CONTENT_COUNTER, 0);
            }
        } else if (event.equalsIgnoreCase(EVENT_CONTENT_ITEM_DETAIL_COUNTER)) {
            // increment event
            mSharedPref.setPref(EVENT_CONTENT_ITEM_DETAIL_COUNTER, (mSharedPref.getIntPref(EVENT_CONTENT_ITEM_DETAIL_COUNTER, 0) + 1));
            if (mSharedPref.getIntPref(EVENT_CONTENT_ITEM_DETAIL_COUNTER, 0) >= ITEM_DETAIL_VISIT_THRESHOLD) {
                isHappinessScoreChanged = true;
                // reset value
                mSharedPref.setPref(EVENT_CONTENT_ITEM_DETAIL_COUNTER, 0);
            }
        } else if (event.equalsIgnoreCase(EVENT_ABOUT_COUNTER)) {
            // increment event
            mSharedPref.setPref(EVENT_ABOUT_COUNTER, (mSharedPref.getIntPref(EVENT_ABOUT_COUNTER, 0) + 1));
            if (mSharedPref.getIntPref(EVENT_ABOUT_COUNTER, 0) >= ABOUT_VISIT_THRESHOLD) {
                isHappinessScoreChanged = true;
                // reset value
                mSharedPref.setPref(EVENT_ABOUT_COUNTER, 0);
            }
        } else if (event.equalsIgnoreCase(EVENT_ABOUT_SHARE_COUNTER)) {
            // increment event
            mSharedPref.setPref(EVENT_ABOUT_SHARE_COUNTER, (mSharedPref.getIntPref(EVENT_ABOUT_SHARE_COUNTER, 0) + 1));
            if (mSharedPref.getIntPref(EVENT_ABOUT_SHARE_COUNTER, 0) >= ABOUT_SHARE_THRESHOLD) {
                isHappinessScoreChanged = true;
                // reset value
                mSharedPref.setPref(EVENT_ABOUT_SHARE_COUNTER, 0);
            }
        } else if (event.equalsIgnoreCase(EVENT_SEARH_COUNTER)) {
            // increment event
            mSharedPref.setPref(EVENT_SEARH_COUNTER, (mSharedPref.getIntPref(EVENT_SEARH_COUNTER, 0) + 1));
            if (mSharedPref.getIntPref(EVENT_SEARH_COUNTER, 0) >= SEARCH_THRESHOLD) {
                isHappinessScoreChanged = true;
                // reset value
                mSharedPref.setPref(EVENT_SEARH_COUNTER, 0);
            }
        } else if (event.equalsIgnoreCase(EVENT_ITEM_FEEDBACK_COUNTER)) {
            // increment event
            mSharedPref.setPref(EVENT_ITEM_FEEDBACK_COUNTER, (mSharedPref.getIntPref(EVENT_ITEM_FEEDBACK_COUNTER, 0) + 1));
            if (mSharedPref.getIntPref(EVENT_ITEM_FEEDBACK_COUNTER, 0) >= FEEDBACK_VISIT_THRESHOLD) {
                isHappinessScoreChanged = true;
                // reset value
                mSharedPref.setPref(EVENT_ITEM_FEEDBACK_COUNTER, 0);
            }
        }

        // update score
        if (isHappinessScoreChanged) {
            points = rand.nextInt(1) + 1;
            int updatedScore = (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) + points) > 100 ? 100 :
                    (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) + points);
            mSharedPref.setPref(HAPPINESS_SCORE, updatedScore);
        }

        // print results
        Logger.i(TAG, "Happiness Score - " + mSharedPref.getIntPref(HAPPINESS_SCORE, -1));
    }
}
