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
import android.widget.TextView;

import com.app.framework.sharedpref.SharedPref;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;

public class DailyBonusUtils {

    private static final int DAYS_UNTIL_PROMPT = 2;
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
        if (launchCount == 1 || System.currentTimeMillis() >=
                dateFirstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
            // show rate dialog
            showDailyBonus();
        }
    }

    /**
     * Method is used to display doalog
     */
    @SuppressLint({"CutPasteId", "ResourceType"})
    private void showDailyBonus() {
        // retrieve daily bonus assets
        final TypedArray arryDailyBonus = mContext.getResources().obtainTypedArray(R.array.daily_bonus);

        // instantiate dialog builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_signin_bonus, null);
        dialogBuilder.setView(v);
        dialogBuilder.setCancelable(false);
        // instantiate alert dialog
        final AlertDialog alertDialog = dialogBuilder.create();
        if (!FrameworkUtils.checkIfNull(alertDialog.getWindow())) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // instantiate views
        TextView tvCta = v.findViewById(R.id.tv_cta);
        TextView tvRewardEarned = v.findViewById(R.id.tv_reward_earned);

        // day info
        View itemBonusInfo = v.findViewById(R.id.item_bonus_info);
        ImageView ivDayInfo = itemBonusInfo.findViewById(R.id.iv_day);
        TextView tvDayInfo = itemBonusInfo.findViewById(R.id.tv_day);
        tvDayInfo.setText(String.valueOf(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0)));

        // day 1
        View itemDayOne = v.findViewById(R.id.item_day_one);
        ImageView ivDayOne = itemDayOne.findViewById(R.id.iv_day);
        TextView tvDayOne = itemDayOne.findViewById(R.id.tv_day);

        // day 2
        View itemDayTwo = v.findViewById(R.id.item_day_two);
        ImageView ivDayTwo = itemDayTwo.findViewById(R.id.iv_day);
        TextView tvDayTwo = itemDayTwo.findViewById(R.id.tv_day);

        // day 3
        View itemDayThree = v.findViewById(R.id.item_day_three);
        ImageView ivDayThree = itemDayThree.findViewById(R.id.iv_day);
        TextView tvDayThree = itemDayThree.findViewById(R.id.tv_day);

        // day 4
        View itemDayFour = v.findViewById(R.id.item_day_four);
        ImageView ivDayFour = itemDayFour.findViewById(R.id.iv_day);
        TextView tvDayFour = itemDayFour.findViewById(R.id.tv_day);

        // day 5
        View itemDayFive = v.findViewById(R.id.item_day_five);
        ImageView ivDayFive = itemDayFive.findViewById(R.id.iv_day);
        TextView tvDayFive = itemDayFive.findViewById(R.id.tv_day);

        // day 6
        View itemDaySix = v.findViewById(R.id.item_day_six);
        ImageView ivDaySix = itemDaySix.findViewById(R.id.iv_day);
        TextView tvDaySix = itemDaySix.findViewById(R.id.tv_day);

        // day 7
        View itemDaySeven = v.findViewById(R.id.item_day_seven);
        ImageView ivDaySeven = itemDaySeven.findViewById(R.id.iv_day);
        TextView tvDaySeven = itemDaySeven.findViewById(R.id.tv_day);

        // set values
        tvRewardEarned.setText(mContext.getResources().getString(R.string.reward_earned,
                String.valueOf(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0))));
        tvDayOne.setText("1");
        tvDayTwo.setText("2");
        tvDayThree.setText("3");
        tvDayFour.setText("4");
        tvDayFive.setText("5");
        tvDaySix.setText("6");
        tvDaySeven.setText("7");

        // set image drawable
        ivDayInfo.setImageDrawable(ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(
                mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) - 1, 0)));
        ivDayOne.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) >= 1 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(0, 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.signin_bonus_default));
        ivDayTwo.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) >= 2 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(1, 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.signin_bonus_default));
        ivDayThree.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) >= 3 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(3, 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.signin_bonus_default));
        ivDayFour.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) >= 4 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(4, 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.signin_bonus_default));
        ivDayFive.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) >= 5 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(5, 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.signin_bonus_default));
        ivDaySix.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) >= 6 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(6, 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.signin_bonus_default));
        ivDaySeven.setImageDrawable(mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) >= 7 ?
                ContextCompat.getDrawable(mContext, arryDailyBonus.getResourceId(7, 0)) :
                ContextCompat.getDrawable(mContext, R.drawable.signin_bonus_default));

        // set listener
        tvCta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // increase happiness
                HappinessUtils.incrementHappiness(1);
                // update launch date
                mSharedPref.setPref(Constants.KEY_DAILY_BONUS_DATE, System.currentTimeMillis());
                // recycle
                arryDailyBonus.recycle();
                // dismiss dialog
                alertDialog.dismiss();
            }
        });

        // display alert dialog
        alertDialog.show();
    }
}