package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.adapter.ItemAdapter;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ItemModel;

import java.util.ArrayList;

/**
 * Created by LJTat on 1/3/2018.
 */

public class ChableeFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private String mCategory;

    // adapter
    private LinearLayoutManager mLayoutManager;
    private ItemAdapter mItemAdapter;
    private RecyclerView rvItems;
    private ArrayList<ItemModel> alItems;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_chablee, container, false);

        // instantiate views
        initializeViews();
        initializeListeners();
        initializeHandlers();
        getBundle();

        return mRootView;
    }

    /**
     * Method is used to instantiate views
     */
    private void initializeViews() {
        mContext = getActivity();
        alItems = new ArrayList<>();

        // initialize views
        rvItems = mRootView.findViewById(R.id.rv_items);

        // initialize adapter
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItems.setLayoutManager(mLayoutManager);
        mItemAdapter = new ItemAdapter(mContext, alItems,
                com.blog.ljtatum.ubuyismile.enums.Enum.AdapterType.CHABLEE);
        rvItems.setAdapter(mItemAdapter);

    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {

    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {

    }

    /**
     * Method is used to retrieve bundle information
     */
    private void getBundle() {
        Bundle args = getArguments();
        if (!FrameworkUtils.checkIfNull(args)) {
            mCategory = args.getString(Constants.KEY_CATEGORY, "");
            if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                alItems = ChableeData.getCrowns();
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                alItems = ChableeData.getRings();
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                alItems = ChableeData.getNecklaces();
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                alItems = ChableeData.getRocks();
            }
        }

        // set adapter
        mItemAdapter.updateData(alItems);
    }

    @Override
    public void onClick(View view) {

    }
}
