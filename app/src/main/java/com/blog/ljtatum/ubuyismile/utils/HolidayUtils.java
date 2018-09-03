package com.blog.ljtatum.ubuyismile.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blog.ljtatum.ubuyismile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class HolidayUtils {

    private Context mContext;
    private SimpleDateFormat mFormatter;

    // create calendar objects
    private Calendar mCalCurrent;

    // holidays
    private Calendar mCalHolidayA;
    private Calendar mCalHolidayB;
    private Calendar mCalHolidayC;
    private Calendar mCalHolidayD;
    private Calendar mCalHolidayE;
    private Calendar mCalHolidayF;
    private Calendar mCalHolidayG;

    public HolidayUtils(@NonNull Context context) {
        mContext = context;

        // formatter
        mFormatter = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
        // current calendar
        mCalCurrent = Calendar.getInstance();
        // holiday calendars
        mCalHolidayA = Calendar.getInstance();
        mCalHolidayB = Calendar.getInstance();
        mCalHolidayC = Calendar.getInstance();
        mCalHolidayD = Calendar.getInstance();
        mCalHolidayE = Calendar.getInstance();
        mCalHolidayF = Calendar.getInstance();
        mCalHolidayG = Calendar.getInstance();

        try {
            // holiday calendar date
            // New Years
            mCalHolidayA.setTime(mFormatter.parse("01/01"));
            // Martin Luther King Day
            mCalHolidayB.setTime(mFormatter.parse("01/15"));
            // Valentine's Day
            mCalHolidayC.setTime(mFormatter.parse("02/14"));
            // Labor Day
            mCalHolidayD.setTime(mFormatter.parse("09/03"));
            // Black Friday
            mCalHolidayE.setTime(mFormatter.parse("11/23"));
            // Green Monday
            mCalHolidayF.setTime(mFormatter.parse("12/04"));
            // Christmas
            mCalHolidayG.setTime(mFormatter.parse("12/25"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is used to check if day is a holiday
     *
     * @return True if day is holiday or close to a major shopping holiday, otherwise false
     */
    public boolean isHoliday() {
        if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayA.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayA.get(Calendar.DAY_OF_MONTH)) {
            // New Years
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayB.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayB.get(Calendar.DAY_OF_MONTH)) {
            // Martin Luther King Day
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayC.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayC.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayC.get(Calendar.DAY_OF_MONTH) + 2) {
            // Valentine's Day
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayD.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayD.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayD.get(Calendar.DAY_OF_MONTH) + 2) {
            // Labor Day
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayE.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayE.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayE.get(Calendar.DAY_OF_MONTH) + 2) {
            // Black Friday
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayF.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayF.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayF.get(Calendar.DAY_OF_MONTH) + 2) {
            // Green Monday
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayG.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayG.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayG.get(Calendar.DAY_OF_MONTH) + 2) {
            // Christmas
            return true;
        }
        return false;
    }

    /**
     * Method is used to retrieve holiday date range for UI updates
     * @return Holiday date range for UI updates
     */
    public String getHolidayDateRange() {
        Calendar calPast = Calendar.getInstance();
        Calendar calFuture = Calendar.getInstance();

        calPast.add(Calendar.DATE, -2);
        calFuture.add(Calendar.DATE, 2);

        if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayA.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayA.get(Calendar.DAY_OF_MONTH)) {
            // New Years
            return mFormatter.format(mCalHolidayA.getTime());
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayB.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayB.get(Calendar.DAY_OF_MONTH)) {
            // Martin Luther King Day
            return mFormatter.format(mCalHolidayB.getTime());
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayC.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayC.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayC.get(Calendar.DAY_OF_MONTH) + 2) {
            // Valentine's Day
            return mContext.getResources().getString(R.string.holiday_date_range,
                    mFormatter.format(calPast.getTime()), mFormatter.format(calFuture.getTime()));
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayD.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayD.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayD.get(Calendar.DAY_OF_MONTH) + 2) {
            // Labor Day
            return mContext.getResources().getString(R.string.holiday_date_range,
                    mFormatter.format(calPast.getTime()), mFormatter.format(calFuture.getTime()));
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayE.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayE.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayE.get(Calendar.DAY_OF_MONTH) + 2) {
            // Black Friday
            return mContext.getResources().getString(R.string.holiday_date_range,
                    mFormatter.format(calPast.getTime()), mFormatter.format(calFuture.getTime()));
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayF.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayF.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayF.get(Calendar.DAY_OF_MONTH) + 2) {
            // Green Monday
            return mContext.getResources().getString(R.string.holiday_date_range,
                    mFormatter.format(calPast.getTime()), mFormatter.format(calFuture.getTime()));
        } else {
            // Christmas
            return mFormatter.format(mCalHolidayG.getTime());
        }
    }


    /**
     * Method is used to retrieve holiday title for UI updates
     * @return Holiday title for UI updates
     */
    public String getHolidayTitle() {
        if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayA.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayA.get(Calendar.DAY_OF_MONTH)) {
            // New Years
            return mContext.getResources().getString(R.string.new_year_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayB.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayB.get(Calendar.DAY_OF_MONTH)) {
            // Martin Luther King Day
            return mContext.getResources().getString(R.string.martin_luther_king_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayC.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayC.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayC.get(Calendar.DAY_OF_MONTH) + 2) {
            // Valentine's Day
            return mContext.getResources().getString(R.string.valentine_day_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayD.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayD.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayD.get(Calendar.DAY_OF_MONTH) + 2) {
            // Labor Day
            return mContext.getResources().getString(R.string.labor_day_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayE.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayE.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayE.get(Calendar.DAY_OF_MONTH) + 2) {
            // Black Friday
            return mContext.getResources().getString(R.string.black_friday_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayF.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayF.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayF.get(Calendar.DAY_OF_MONTH) + 2) {
            // Green Monday
            return mContext.getResources().getString(R.string.green_monday_title);
        } else {
            // Christmas
            return mContext.getResources().getString(R.string.christmas_title);
        }
    }

    /**
     * Method is used to retrieve holiday message for UI updates
     * @return Holiday message for UI updates
     */
    public String getHolidayMessage() {
        Random rand = new Random();
        int randValue = rand.nextInt(2);

        if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayA.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayA.get(Calendar.DAY_OF_MONTH)) {
            // New Years
            return randValue == 0 ? mContext.getResources().getString(R.string.new_year_message_a) :
                    mContext.getResources().getString(R.string.new_year_message_b);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayB.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayB.get(Calendar.DAY_OF_MONTH)) {
            // Martin Luther King Day
            return randValue == 0 ? mContext.getResources().getString(R.string.martin_luther_king_message_a) :
                    mContext.getResources().getString(R.string.martin_luther_king_message_b);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayC.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayC.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayC.get(Calendar.DAY_OF_MONTH) + 2) {
            // Valentine's Day
            return randValue == 0 ? mContext.getResources().getString(R.string.valentine_day_message_a) :
                    mContext.getResources().getString(R.string.valentine_day_message_b);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayD.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayD.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayD.get(Calendar.DAY_OF_MONTH) + 2) {
            // Labor Day
            return randValue == 0 ? mContext.getResources().getString(R.string.labor_day_message_a) :
                    mContext.getResources().getString(R.string.labor_day_message_b);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayE.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayE.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayE.get(Calendar.DAY_OF_MONTH) + 2) {
            // Black Friday
            return randValue == 0 ? mContext.getResources().getString(R.string.black_friday_message_a) :
                    mContext.getResources().getString(R.string.black_friday_message_b);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayF.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayF.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayF.get(Calendar.DAY_OF_MONTH) + 2) {
            // Green Monday
            return randValue == 0 ? mContext.getResources().getString(R.string.green_monday_message_a) :
                    mContext.getResources().getString(R.string.green_monday_message_b);
        } else {
            // Christmas
            return randValue == 0 ? mContext.getResources().getString(R.string.christmas_message_a) :
                    mContext.getResources().getString(R.string.christmas_message_b);
        }
    }

    /**
     * Method is used to retrieve holiday background for UI updates
     * @return Holiday background for UI updates
     */
    public int getHolidayDrawable() {
        if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayA.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayA.get(Calendar.DAY_OF_MONTH)) {
            // New Years
            return R.drawable.holiday_bg_e;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayB.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayB.get(Calendar.DAY_OF_MONTH)) {
            // Martin Luther King Day
            return R.drawable.holiday_bg_d;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayC.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayC.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayC.get(Calendar.DAY_OF_MONTH) + 2) {
            // Valentine's Day
            return R.drawable.holiday_bg_a;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayD.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayD.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayD.get(Calendar.DAY_OF_MONTH) + 2) {
            // Labor Day
            return R.drawable.holiday_bg_b;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayE.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayE.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayE.get(Calendar.DAY_OF_MONTH) + 2) {
            // Black Friday
            return R.drawable.holiday_bg_e;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayF.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayF.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayF.get(Calendar.DAY_OF_MONTH) + 2) {
            // Green Monday
            return R.drawable.holiday_bg_e;
        } else {
            // Christmas
            return R.drawable.holiday_bg_c;
        }
    }

}
