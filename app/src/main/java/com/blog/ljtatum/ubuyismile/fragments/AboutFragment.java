package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.share.ShareUtils;
import com.blog.ljtatum.ubuyismile.BuildConfig;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.activity.MainActivity;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;

import java.util.Calendar;

/**
 * Created by leonard on 10/23/2017.
 */

public class AboutFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private TextView tvFragmentHeader, tvAppVersion, tvCopyright, tvFeedbackEmail;
    private ImageView ivFb, ivTwitter, ivLinkedin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_about, container, false);

        // instantiate views
        initializeViews();
        initializeHandlers();

        return mRootView;
    }

    /**
     * Method is used to instantiate views
     */
    private void initializeViews() {
        mContext = getActivity();
        tvFragmentHeader = mRootView.findViewById(R.id.tv_fragment_header);
        tvAppVersion = mRootView.findViewById(R.id.tv_app_version);
        tvCopyright = mRootView.findViewById(R.id.tv_copyright);
        tvFeedbackEmail = mRootView.findViewById(R.id.tv_feedback_email);
        ivFb = mRootView.findViewById(R.id.iv_fb);
        ivTwitter = mRootView.findViewById(R.id.iv_twitter);
        ivLinkedin = mRootView.findViewById(R.id.iv_linkedin);

        // track Happiness
        HappinessUtils.trackHappiness(HappinessUtils.EVENT_ABOUT_COUNTER);

        // set fragment header
        tvFragmentHeader.setText(getResources().getString(R.string.menu_about));
        // set app version
        tvAppVersion.setText(BuildConfig.VERSION_NAME);
        // set copyright year
        tvCopyright.setText(getActivity().getResources().getString(R.string.copyright_year,
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
        // set email link
        final SpannableString email = new SpannableString(
                getActivity().getResources().getString(R.string.feedback_email));
        Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
        tvFeedbackEmail.setText(email);
        tvFeedbackEmail.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        ivFb.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        ivLinkedin.setOnClickListener(this);
        tvFragmentHeader.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (view.getId()) {
            case R.id.tv_fragment_header:
                remove();
                popBackStack();
                break;
            case R.id.iv_fb:
                // track Happiness
                HappinessUtils.trackHappiness(HappinessUtils.EVENT_ABOUT_SHARE_COUNTER);
                ShareUtils.openSocialMediaViaIntent(mContext, Enum.SocialMedia.FB, true);
                break;
            case R.id.iv_twitter:
                // track Happiness
                HappinessUtils.trackHappiness(HappinessUtils.EVENT_ABOUT_SHARE_COUNTER);
                ShareUtils.openSocialMediaViaIntent(mContext, Enum.SocialMedia.TWITTER, true);
                break;
            case R.id.iv_linkedin:
                // track Happiness
                HappinessUtils.trackHappiness(HappinessUtils.EVENT_ABOUT_SHARE_COUNTER);
                ShareUtils.openSocialMediaViaIntent(mContext, Enum.SocialMedia.LINKEDIN, true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // disable drawer
        ((MainActivity) mContext).toggleDrawerState(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // enable drawer
        ((MainActivity) mContext).toggleDrawerState(true);
    }
}