package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.BuildConfig;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.activity.MainActivity;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;

import java.util.Calendar;

/**
 * Created by leonard on 10/23/2017.
 */

public class PrivacyFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private TextView tvFragmentHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_privacy, container, false);

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
        TextView tvAppVersion = mRootView.findViewById(R.id.tv_app_version);
        TextView tvCopyright = mRootView.findViewById(R.id.tv_copyright);

        // set fragment header
        tvFragmentHeader.setText(getResources().getString(R.string.menu_privacy));

        // set app version
        tvAppVersion.setText(BuildConfig.VERSION_NAME);
        // set copyright year
        tvCopyright.setText(getActivity().getResources().getString(R.string.copyright_year,
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));

        // track Happiness
        HappinessUtils.trackHappiness(HappinessUtils.EVENT_CONTENT_COUNTER);
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
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
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // disable drawer
        ((MainActivity) mContext).setDrawerState(false);
    }

    @Override
    public void onDetach() {
        // enable drawer
        ((MainActivity) mContext).setDrawerState(true);
        super.onDetach();
    }
}