package com.blog.ljtatum.ubuyismile.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;

import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.LifecycleCallback;

/**
 * Created by LJTat on 12/31/2017.
 */

public class ErrorUtils {
    // how long to display Crouton before it dismisses
    private static final int DURATION = 7000;
    private boolean isDisplayed;
    private Crouton mCrouton;

    /**
     * @param activity      An activity is a single, focused thing that the user can do
     * @param frontendError The mapped error to track for analytics
     * @param backendError  The backend error to track for analytics
     */
    public void showError(@NonNull final Activity activity, @NonNull final String frontendError, @NonNull final String backendError) {
        if (!isDisplayed) {
            try {
                View view = activity.getLayoutInflater().inflate(R.layout.error_layout, null, false);
                final Configuration CONFIGURATION_INFINITE = new Configuration.Builder().setDuration(DURATION).build();
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
                // instantiate views
                TextView tvErrorMessage = view.findViewById(R.id.tv_error);
                ImageView ivClose = view.findViewById(R.id.iv_error_close);
                // set text
                tvErrorMessage.setText(!FrameworkUtils.isStringEmpty(frontendError) ? frontendError : "");
                // set listener for close button
                ivClose.setOnClickListener(new View.OnClickListener() {
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
     * @return True if message is showing, otherwise false
     */
    public boolean isShowing() {
        return isDisplayed;
    }
}
