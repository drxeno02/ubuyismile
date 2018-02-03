package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.listeners.OnFirebaseValueListener;
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.adapter.ItemAdapter;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.ErrorUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by LJTat on 1/3/2018.
 */

public class ChableeFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private ErrorUtils mErrorUtils;
    private View mRootView;
    private String mCategory;

    // adapter
    private LinearLayoutManager mLayoutManager;
    private ItemAdapter mItemAdapter;
    private RecyclerView rvItems;
    private ArrayList<ItemModel> alItems;

    // refresh layout
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_chablee, container, false);

        // instantiate views
        initializeViews();
        initializeListeners();
        // retrieve bundle info
        getBundle();
        initializeHandlers();

        return mRootView;
    }

    /**
     * Method is used to instantiate views
     */
    private void initializeViews() {
        mContext = getActivity();
        mErrorUtils = new ErrorUtils();
        alItems = new ArrayList<>();

        // initialize views
        rvItems = mRootView.findViewById(R.id.rv_items);
        swipeRefreshLayout = mRootView.findViewById(R.id.sr_layout);

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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // retrieve data (CHABLEE)
                FirebaseUtils.retrieveItemsChablee(mCategory);
            }
        });

        // OnFirebaseValueListener
        FirebaseUtils.onFirebaseValueListener(new OnFirebaseValueListener() {
            @Override
            public void onUpdateDataChange(DataSnapshot dataSnapshot) {
                // do nothing
            }

            @Override
            public void onUpdateDatabaseError(DatabaseError databaseError) {
                // do nothing
            }

            @Override
            public void onRetrieveDataChange(DataSnapshot dataSnapshot) {
                if (!FrameworkUtils.checkIfNull(dataSnapshot)) {
                    populateDataLists(dataSnapshot);
                }
            }

            @Override
            public void onRetrieveDataError(DatabaseError databaseError) {
                // display error dialog
                mErrorUtils.showError(getActivity(),
                        mContext.getResources().getString(R.string.default_error_message), "");
            }
        });
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

    /**
     * Method is used to populate models with data
     *
     * @param dataSnapshot data retrieved from firebase
     */
    private void populateDataLists(DataSnapshot dataSnapshot) {
        ArrayList<ItemModel> alData = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            ItemModel chableeModel = snapshot.getValue(ItemModel.class);
            if (!FrameworkUtils.checkIfNull(snapshot.getValue()) &&
                    !FrameworkUtils.checkIfNull(chableeModel)) {
                chableeModel.category = mCategory;
                chableeModel.label = com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.NEW.toString();
                chableeModel.timestamp = FrameworkUtils.getCurrentDateTime();
                chableeModel.isBrowseItem = Utils.isBrowseItem();
                alData.add(chableeModel);
            }
        }
        // clear list
        alItems.clear();
        if (alData.size() > 0 && mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
            // set crown list
            ChableeData.setCrowns(alData);
            // update item list
            alItems = ChableeData.getCrowns();
        } else if (alData.size() > 0 && mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
            // set rings list
            ChableeData.setRings(alData);
            // update item list
            alItems = ChableeData.getRings();
        } else if (alData.size() > 0 && mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
            // set necklaces list
            ChableeData.setNecklaces(alData);
            // update item list
            alItems = ChableeData.getNecklaces();
        } else if (alData.size() > 0 && mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
            // set rocks list
            ChableeData.setRocks(alData);
            // update item list
            alItems = ChableeData.getRocks();
        }
        // set adapter
        mItemAdapter.updateData(alItems);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {

    }
}
