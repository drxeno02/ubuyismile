package com.blog.ljtatum.ubuyismile.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HolidayUtils {

    private Context mContext;
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
    private Calendar mCalHolidayH;

    public HolidayUtils(@NonNull Context context) {
        mContext = context;

        // formatter
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
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
        mCalHolidayH = Calendar.getInstance();

        try {
            // holiday calendar date
            // New Years
            mCalHolidayA.setTime(formatter.parse("01/01"));
            // Martin Luther King Day
            mCalHolidayB.setTime(formatter.parse("01/15"));
            // L's Birthday
            mCalHolidayC.setTime(formatter.parse("02/06"));
            // Valentine's Day
            mCalHolidayD.setTime(formatter.parse("02/14"));
            // Labor Day
            mCalHolidayE.setTime(formatter.parse("09/03"));
            // Black Friday
            mCalHolidayF.setTime(formatter.parse("11/23"));
            // Green Monday
            mCalHolidayG.setTime(formatter.parse("12/04"));
            // Christmas
            mCalHolidayH.setTime(formatter.parse("12/25"));
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
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayC.get(Calendar.DAY_OF_MONTH)) {
            // L's Birthday
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayD.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayD.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayD.get(Calendar.DAY_OF_MONTH) + 2) {
            // Valentine's Day
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayE.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayE.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayE.get(Calendar.DAY_OF_MONTH) + 2) {
            // Labor Day
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayF.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayF.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayF.get(Calendar.DAY_OF_MONTH) + 2) {
            // Black Friday
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayG.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayG.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayG.get(Calendar.DAY_OF_MONTH) + 2) {
            // Green Monday
            return true;
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayH.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayH.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayH.get(Calendar.DAY_OF_MONTH) + 2) {
            // Christmas
            return true;
        }
        return false;
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
                mCalCurrent.get(Calendar.DAY_OF_MONTH) == mCalHolidayC.get(Calendar.DAY_OF_MONTH)) {
            // L's Birthday
            return mContext.getResources().getString(R.string.leonard_birthday_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayD.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayD.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayD.get(Calendar.DAY_OF_MONTH) + 2) {
            // Valentine's Day
            return mContext.getResources().getString(R.string.valentine_day_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayE.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayE.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayE.get(Calendar.DAY_OF_MONTH) + 2) {
            // Labor Day
            return mContext.getResources().getString(R.string.labor_day_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayF.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayF.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayF.get(Calendar.DAY_OF_MONTH) + 2) {
            // Black Friday
            return mContext.getResources().getString(R.string.black_friday_title);
        } else if (mCalCurrent.get(Calendar.MONTH) == mCalHolidayG.get(Calendar.MONTH) &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) >= mCalHolidayG.get(Calendar.DAY_OF_MONTH) - 2 &&
                mCalCurrent.get(Calendar.DAY_OF_MONTH) <= mCalHolidayG.get(Calendar.DAY_OF_MONTH) + 2) {
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
        return "";
    }

}
