package com.blog.ljtatum.ubuyismile.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.app.framework.constants.Constants;
import com.app.framework.sharedpref.SharedPref;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * Created by LJTat on 2/14/2018.
 */

public class HappinessUtils {
    public static final String EVENT_APP_LAUNCH = "key_event_app_launch"; // can be negative Happiness
    public static final String EVENT_CONTENT_COUNTER = "key_event_content_counter";
    public static final String EVENT_CONTENT_ITEM_DETAIL_COUNTER = "key_event_content_item_detail_counter";
    public static final String EVENT_ABOUT_COUNTER = "key_event_about_counter";
    public static final String EVENT_ABOUT_SHARE_COUNTER = "key_event_share_counter";
    public static final String EVENT_SEARCH_COUNTER = "key_event_search_counter";
    public static final String EVENT_ITEM_FEEDBACK_COUNTER = "key_event_item_feedback_counter";
    private static final String TAG = HappinessUtils.class.getSimpleName();
    private static final String HAPPINESS_SCORE = "key_happiness";
    private static final String APP_LAUNCH_TIMESTAMP = "key_app_launch_timestamp";

    private static final int MAX_NUM_DAYS_LOGGED_OUT = 5; // days
    private static final int CONTENT_VISIT_THRESHOLD = 50; // threshold for visiting content
    private static final int ITEM_DETAIL_VISIT_THRESHOLD = 25; // threshold for visiting item details
    private static final int ABOUT_VISIT_THRESHOLD = 10; // threshold for visiting About
    private static final int ABOUT_SHARE_THRESHOLD = 1; // threshold for sharing from About
    private static final int SEARCH_THRESHOLD = 25; // threshold for searching items
    private static final int FEEDBACK_VISIT_THRESHOLD = 10; // threshold for visiting feedback

    private static String[] arryHappyMessages;
    private static String[] arryNeutralMessages;
    private static String[] arryUnHappyMessages;
    private static TypedArray typedArryDrawable90;
    private static TypedArray typedArryDrawable80;
    private static TypedArray typedArryDrawable70;
    private static TypedArray typedArryDrawable60;
    private static TypedArray typedArryDrawable50;

    private static SharedPref mSharedPref;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     */
    public HappinessUtils(@NonNull Context context) {
        mContext = context;
        mSharedPref = new SharedPref(context, Constants.PREF_FILE_NAME);

        // populate lists
        populateMessageLists();
        populateDrawableLists();
    }

    /**
     * Method is used to update Happiness Score
     *
     * @param event The event name that marks a milestone
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
                mSharedPref.setPref(HAPPINESS_SCORE, 81);
            } else {
                // use timestamp to determine positive or negative Happiness score
                int daysBetweenDates = FrameworkUtils.getDaysBetweenDates(timestamp,
                        mSharedPref.getStringPref(APP_LAUNCH_TIMESTAMP, timestamp));
                Logger.i(TAG, "Days Between Dates: " + daysBetweenDates);
                // counter representing sets of maximum logged days
                int counter = 1;
                while ((daysBetweenDates / counter) / MAX_NUM_DAYS_LOGGED_OUT >= 1) {
                    counter++;
                }
                // prepare for negative score
                if (counter > 1) {
                    points = (rand.nextInt(1) + 1) * counter; // larger penalty
                    Logger.i(TAG, "Total Points Lost: " + points);
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
        } else if (event.equalsIgnoreCase(EVENT_SEARCH_COUNTER)) {
            // increment event
            mSharedPref.setPref(EVENT_SEARCH_COUNTER, (mSharedPref.getIntPref(EVENT_SEARCH_COUNTER, 0) + 1));
            if (mSharedPref.getIntPref(EVENT_SEARCH_COUNTER, 0) >= SEARCH_THRESHOLD) {
                isHappinessScoreChanged = true;
                // reset value
                mSharedPref.setPref(EVENT_SEARCH_COUNTER, 0);
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
            Logger.i(TAG, "Total Points Increased: " + points);
            int updatedScore = (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) + points) > 100 ? 100 :
                    (mSharedPref.getIntPref(HAPPINESS_SCORE, -1) + points);
            mSharedPref.setPref(HAPPINESS_SCORE, updatedScore);
        }

        // print results
        Logger.i(TAG, "Happiness Score: " + mSharedPref.getIntPref(HAPPINESS_SCORE, -1));
    }

    /**
     * Method is used to retrieve description
     *
     * @return An immutable sequence of UTF-16 char
     */
    public static String retrieveDescription() {
        String description;
        Random rand = new Random();
        if (mSharedPref.getLongPref(com.app.framework.constants.Constants.KEY_APP_LAUNCH_COUNT, 0L) < 3) {
            description = mContext.getResources().getString(R.string.neutral_01);
        } else {
            if (mSharedPref.getIntPref(HAPPINESS_SCORE, 0) > 80) {
                // happy messages
                description = arryHappyMessages[rand.nextInt(arryHappyMessages.length - 1)];
            } else if (mSharedPref.getIntPref(HAPPINESS_SCORE, 0) > 60 &&
                    mSharedPref.getIntPref(HAPPINESS_SCORE, 0) <= 80) {
                // neutral messages
                description = arryNeutralMessages[rand.nextInt(arryNeutralMessages.length - 1)];
            } else {
                // unhappy messages
                description = arryUnHappyMessages[rand.nextInt(arryUnHappyMessages.length - 1)];
            }
        }
        return description;
    }

    /**
     * Method is used to retrieve drawable
     *
     * @return A Drawable is a general abstraction for "something that can be drawn."
     */
    public static Drawable retrieveDrawable() {
        Drawable drawable;
        Random rand = new Random();
        if (mSharedPref.getLongPref(com.app.framework.constants.Constants.KEY_APP_LAUNCH_COUNT, 0L) < 3) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.happiness_80_04);
        } else {
            if (mSharedPref.getIntPref(HAPPINESS_SCORE, 0) > 90) {
                // happy messages
                drawable = ContextCompat.getDrawable(mContext, typedArryDrawable90.getResourceId(
                        rand.nextInt(typedArryDrawable90.length() - 1), 0));
            } else if (mSharedPref.getIntPref(HAPPINESS_SCORE, 0) > 80 &&
                    mSharedPref.getIntPref(HAPPINESS_SCORE, 0) <= 90) {
                // happy messages
                drawable = ContextCompat.getDrawable(mContext, typedArryDrawable80.getResourceId(
                        rand.nextInt(typedArryDrawable80.length() - 1), 0));
            } else if (mSharedPref.getIntPref(HAPPINESS_SCORE, 0) > 70 &&
                    mSharedPref.getIntPref(HAPPINESS_SCORE, 0) <= 80) {
                // neutral messages
                drawable = ContextCompat.getDrawable(mContext, typedArryDrawable70.getResourceId(
                        rand.nextInt(typedArryDrawable70.length() - 1), 0));
            } else if (mSharedPref.getIntPref(HAPPINESS_SCORE, 0) > 60 &&
                    mSharedPref.getIntPref(HAPPINESS_SCORE, 0) <= 70) {
                // neutral messages
                drawable = ContextCompat.getDrawable(mContext, typedArryDrawable60.getResourceId(
                        rand.nextInt(typedArryDrawable60.length() - 1), 0));
            } else {
                // unhappy messages
                drawable = ContextCompat.getDrawable(mContext, typedArryDrawable50.getResourceId(
                        rand.nextInt(typedArryDrawable50.length() - 1), 0));
            }
        }
        return drawable;
    }

    /**
     * Method is used to populate message lists
     */
    private void populateMessageLists() {
        // instantiate message lists
        arryHappyMessages = mContext.getResources().getStringArray(R.array.happy_messages);
        arryNeutralMessages = mContext.getResources().getStringArray(R.array.neutral_messages);
        arryUnHappyMessages = mContext.getResources().getStringArray(R.array.unhappy_messages);
    }

    /**
     * Method is used to populate drawable lists
     */
    private void populateDrawableLists() {
        // instantiate drawable lists
        typedArryDrawable90 = mContext.getResources().obtainTypedArray(R.array.drawable_90);
        typedArryDrawable80 = mContext.getResources().obtainTypedArray(R.array.drawable_80);
        typedArryDrawable70 = mContext.getResources().obtainTypedArray(R.array.drawable_70);
        typedArryDrawable60 = mContext.getResources().obtainTypedArray(R.array.drawable_60);
        typedArryDrawable50 = mContext.getResources().obtainTypedArray(R.array.drawable_50);
    }
}
