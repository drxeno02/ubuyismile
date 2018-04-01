package com.blog.ljtatum.ubuyismile.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Durations;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.LifecycleCallback;

/**
 * Created by LJTat on 3/31/2018.
 */

public class InfoBarUtils {

    private boolean isDisplayed;
    private Crouton mCrouton;

    /**
     * Method is used to display/hide information bar
     *
     * @param activity An activity is a single, focused thing that the user can do
     */
    public void showInfoBar(@NonNull final Activity activity, final boolean isYesNo) {
        if (!isDisplayed) {
            try {
                View view = activity.getLayoutInflater().inflate(R.layout.info_layout, null, false);
                final Configuration CONFIGURATION_INFINITE = new Configuration.Builder().setDuration(Durations.DELAY_INTERVAL_MS_10000).build();
                mCrouton = Crouton.make(activity, view, R.id.iv_error_close, CONFIGURATION_INFINITE);
                // set listener for crouton
                mCrouton.setLifecycleCallback(new LifecycleCallback() {
                    @Override
                    public void onDisplayed() {
                        // set flag
                        isDisplayed = true;
                    }

                    @Override
                    public void onRemoved() {
                        // reset
                        isDisplayed = false;
                    }
                });

                if (isYesNo) {
                    // instantiate views
                    LinearLayout llInfoWrapper = view.findViewById(R.id.ll_info_wrapper);
                    TextView tvPositiveInfo = view.findViewById(R.id.tv_positive_info);
                    TextView tvNegativeInfo = view.findViewById(R.id.tv_negative_info);

                    // set visibility
                    FrameworkUtils.setViewVisible(llInfoWrapper);

                    // set listeners
                    tvPositiveInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // close crouton
                            mCrouton.hide();
                        }
                    });
                    tvNegativeInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // close crouton
                            mCrouton.hide();
                        }
                    });
                }

                // instantiate views
                TextView tvMessageInfo = view.findViewById(R.id.tv_message_info);
                ImageView civEmoteInfo = view.findViewById(R.id.civ_emote_info);
                ImageView ivCloseInfo = view.findViewById(R.id.iv_close_info);
                // set text
                tvMessageInfo.setText(HappinessUtils.retrieveDescription());
                civEmoteInfo.setImageDrawable(HappinessUtils.retrieveDrawable());
                // set listeners
                ivCloseInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // close crouton
                        mCrouton.hide();
                    }
                });
                // show error message
                mCrouton.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method is used to dismiss error message
     */
    public void dismiss() {
        if (!FrameworkUtils.checkIfNull(mCrouton) && isDisplayed) {
            mCrouton.hide();
        }
    }

    /**
     * Method is used to check if message is showing
     *
     * @return True if message is showing, otherwise false
     */
    public boolean isShowing() {
        return isDisplayed;
    }
}
