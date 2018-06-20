package com.blog.ljtatum.ubuyismile.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.device.DeviceUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.adapter.ItemAutoCompletedAdapter;
import com.blog.ljtatum.ubuyismile.adapter.ItemBrowseAdapter;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseFragment {

    private Context mContext;
    private Activity mActivity;
    private View mRootView;

    private String mSearchCategory;
    private AutoCompleteTextView acSearch;

    // adapter
    private ItemAutoCompletedAdapter itemAutoCompleteAdapter;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_search, container, false);

        // instantiate views
        initializeViews();
        // retrieve bundle info
        getBundle();

        return mRootView;
    }

    /**
     * Method is used to instantiate views
     */
    private void initializeViews() {
        mContext = getActivity();
        mActivity = getActivity();

        // track Happiness
        HappinessUtils.trackHappiness(HappinessUtils.EVENT_SEARCH_COUNTER);

        // instantiate SQLite database
        mItemProvider = new ItemProvider(mContext);
        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();

        // initialize adapter
        acSearch = mRootView.findViewById(R.id.ac_search);
        itemAutoCompleteAdapter = new ItemAutoCompletedAdapter(mContext, R.layout.item_auto_complete, alItemDb);
        acSearch.setAdapter(itemAutoCompleteAdapter);
    }

    /**
     * Method is used to retrieve bundle information
     */
    private void getBundle() {
        Bundle args = getArguments();
        if (!FrameworkUtils.checkIfNull(args)) {
            mSearchCategory = args.getString(Constants.KEY_SEARCH_CATEGORY, "");
        }




    }

    @Override
    public void onResume() {
        super.onResume();
        // show keyboard
        DeviceUtils.showKeyboard(mActivity);
    }

    @Override
    public void onPause() {
        super.onPause();
        // hide keyboard
        DeviceUtils.hideKeyboard(mActivity, mActivity.getWindow().getDecorView().getWindowToken());
    }
}
