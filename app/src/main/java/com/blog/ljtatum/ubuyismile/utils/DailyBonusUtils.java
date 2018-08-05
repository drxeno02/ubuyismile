package com.blog.ljtatum.ubuyismile.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.framework.sharedpref.SharedPref;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;

public class DailyBonusUtils {

    private Context mContext;
    private SharedPref mSharedPref;

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     */
    public DailyBonusUtils(Context context) {
        mContext = context;
        recordAppLaunchDate();
    }

    /**
     * Method is used to record when app is first launched
     */
    private void recordAppLaunchDate() {
        mSharedPref = new SharedPref(mContext, com.app.framework.constants.Constants.PREF_FILE_NAME);

        // increment launch counter
        int launchCount = mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) + 1;
        mSharedPref.setPref(Constants.KEY_DAILY_BONUS, launchCount);

        // get date of first launch
        Long dateFirstLaunch = mSharedPref.getLongPref(Constants.KEY_DAILY_BONUS_DATE, 0L);
        if (dateFirstLaunch == 0) {
            dateFirstLaunch = System.currentTimeMillis();
            mSharedPref.setPref(Constants.KEY_DAILY_BONUS_DATE, dateFirstLaunch);
        }

        // wait at least n days before opening
        if (System.currentTimeMillis() >= dateFirstLaunch + (24 * 60 * 60 * 1000)) {
            // show rate dialog
            showDailyBonus();
        }
    }

    /**
     * Method is used to display doalog
     */
    @SuppressLint("CutPasteId")
    private void showDailyBonus() {
        // instantiate dialog builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View v = inflater.inflate(R.layout.daily_bonus_layout, null);
        dialogBuilder.setView(v);
        dialogBuilder.setCancelable(false);
        // instantiate alert dialog
        final AlertDialog alertDialog = dialogBuilder.create();
        if (!FrameworkUtils.checkIfNull(alertDialog.getWindow())) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // retrieve daily bonus assets
        final TypedArray arryDailyBonus = mContext.getResources().obtainTypedArray(R.array.daily_bonus);

        // instantiate views
        TextView tvCta = v.findViewById(R.id.tv_cta);
        RelativeLayout rlBonusInfo = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDayInfo = rlBonusInfo.findViewById(R.id.iv_day);
        TextView tvDayInfo = rlBonusInfo.findViewById(R.id.tv_day);
        tvDayInfo.setText(String.valueOf(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0)));

        // day 1
        RelativeLayout rlDayOne = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDayOne = rlDayOne.findViewById(R.id.iv_day);
        TextView tvDayOne = rlDayOne.findViewById(R.id.tv_day);

        // day 2
        RelativeLayout rlDayTwo = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDayTwo = rlDayTwo.findViewById(R.id.iv_day);
        TextView tvDayTwo = rlDayTwo.findViewById(R.id.tv_day);

        // day 3
        RelativeLayout rlDayThree = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDayThree = rlDayThree.findViewById(R.id.iv_day);
        TextView tvDayThree = rlDayThree.findViewById(R.id.tv_day);

        // day 4
        RelativeLayout rlDayFour = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDayFour = rlDayFour.findViewById(R.id.iv_day);
        TextView tvDayFour = rlDayFour.findViewById(R.id.tv_day);

        // day 5
        RelativeLayout rlDayFive = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDayFive = rlDayFive.findViewById(R.id.iv_day);
        TextView tvDayFive = rlDayFive.findViewById(R.id.tv_day);

        // day 6
        RelativeLayout rlDaySix = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDaySix = rlDaySix.findViewById(R.id.iv_day);
        TextView tvDaySix = rlDaySix.findViewById(R.id.tv_day);

        // day 7
        RelativeLayout rlDaySeven = v.findViewById(R.id.rl_daily_bonus_wrapper);
        ImageView ivDaySeven = rlDaySeven.findViewById(R.id.iv_day);
        TextView tvDaySeven = rlDaySeven.findViewById(R.id.tv_day);

        // set values
        tvDayOne.setText("1");
        tvDayTwo.setText("2");
        tvDayThree.setText("3");
        tvDayFour.setText("4");
        tvDayFive.setText("5");
        tvDaySix.setText("6");
        tvDaySeven.setText("7");

        // set image drawable
        ivDayInfo.setImageDrawable(ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) + 1, 0)));
        ivDayOne.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) == 1 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                        mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0), 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.daily_bonus_default));
        ivDayTwo.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) == 2 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                        mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0), 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.daily_bonus_default));
        ivDayThree.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) == 3 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                        mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0), 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.daily_bonus_default));
        ivDayFour.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) == 4 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                        mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0), 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.daily_bonus_default));
        ivDayFive.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) == 5 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                        mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0), 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.daily_bonus_default));
        ivDaySix.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) == 6 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                        mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0), 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.daily_bonus_default));
        ivDaySeven.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) == 7 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                        mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0), 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.daily_bonus_default));

        // set listener
        tvCta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // recycle
                arryDailyBonus.recycle();
                // dismiss dialog
                alertDialog.dismiss();
            }
        });
    }
}
