package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.listeners.OnFirebaseValueListener;
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.dialog.DialogUtils;
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.activity.MainActivity;
import com.blog.ljtatum.ubuyismile.adapter.ItemAdapter;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.ErrorUtils;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJTat on 1/3/2018.
 */

public class ChableeFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private ErrorUtils mErrorUtils;
    private View mRootView;
    private String mCategory;
    private TextView tvFragmentHeader, tvNoItems;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    // adapter
    private ItemAdapter mItemAdapter;

    // refresh layout
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_chablee, container, false);

        // instantiate views
        initializeViews();
        initializeListeners();
        initializeHandlers();
        // retrieve bundle info
        getBundle();

        return mRootView;
    }

    /**
     * Method is used to instantiate views
     */
    private void initializeViews() {
        mContext = getActivity();
        mErrorUtils = new ErrorUtils();

        // track Happiness
        HappinessUtils.trackHappiness(HappinessUtils.EVENT_CONTENT_COUNTER);

        // instantiate SQLite database
        mItemProvider = new ItemProvider(mContext);
        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();

        // initialize views
        RecyclerView rvItems = mRootView.findViewById(R.id.rv_items);
        swipeRefreshLayout = mRootView.findViewById(R.id.sr_layout);
        tvFragmentHeader = mRootView.findViewById(R.id.tv_fragment_header);
        tvNoItems = mRootView.findViewById(R.id.tv_no_items);

        // initialize adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItems.setLayoutManager(layoutManager);
        mItemAdapter = new ItemAdapter(mContext, new ArrayList<ItemDatabaseModel>(),
                com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE);
        rvItems.setAdapter(mItemAdapter);

    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {
        tvFragmentHeader.setOnClickListener(this);
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        tvFragmentHeader.setOnClickListener(this);

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
            public void onUpdateDataChange(@NonNull DataSnapshot dataSnapshot) {
                // do nothing
            }

            @Override
            public void onUpdateDatabaseError(@NonNull DatabaseError databaseError) {
                // do nothing
            }

            @Override
            public void onRetrieveDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate lists
                populateDataLists(dataSnapshot);
            }

            @Override
            public void onRetrieveDataError(DatabaseError databaseError) {
                // display error dialog
                mErrorUtils.showError(getActivity(),
                        mContext.getResources().getString(R.string.default_error_message), "");
            }
        });

        // onClick listener
        ItemAdapter.onClickAdapterListener(new OnClickAdapterListener() {
            @Override
            public void onClick(int position) {
                Bundle args = new Bundle();
                args.putString(Constants.KEY_ITEM_ID, alItemDb.get(position).itemId);
                args.putString(Constants.KEY_CATEGORY, Enum.ItemCategoryChablee.CROWNS.toString());
                args.putString(Constants.KEY_ITEM_TYPE, com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString());

                Fragment fragment = new DetailFragment();
                fragment.setArguments(args);
                addFragment(fragment);
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

            // set header
            if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_crowns));
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_rings));
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_necklaces));
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_rocks));
            }

            // populate item list
            ArrayList<ItemDatabaseModel> items = new ArrayList<>();
            for (int i = 0; i < alItemDb.size(); i++) {
                if (alItemDb.get(i).category.equalsIgnoreCase(mCategory)) {
                    // add item
                    items.add(alItemDb.get(i));
                }
            }

            if (items.size() > 0) {
                // set adapter
                FrameworkUtils.setViewGone(tvNoItems);
                mItemAdapter.updateData(items);
            } else {
                // empty list
                FrameworkUtils.setViewVisible(tvNoItems);
            }
        }
    }

    /**
     * Method is used to populate models with data
     *
     * @param dataSnapshot data retrieved from firebase
     */
    private void populateDataLists(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            ItemModel chableeModel = snapshot.getValue(ItemModel.class);
            if (!FrameworkUtils.checkIfNull(snapshot.getValue()) &&
                    !FrameworkUtils.checkIfNull(chableeModel)) {
                chableeModel.category = mCategory;
                chableeModel.asin = ""; // no asin for Chablee items
                chableeModel.label = com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.NEW.toString();
                chableeModel.timestamp = FrameworkUtils.getCurrentDateTime();
                chableeModel.isBrowseItem = Utils.isBrowseItem();
            }

            // update database values for existing items
            for (int i = 0; i < alItemDb.size(); i++) {
                if (!FrameworkUtils.isStringEmpty(alItemDb.get(i).itemId) &&
                        !FrameworkUtils.checkIfNull(chableeModel) &&
                        !FrameworkUtils.isStringEmpty(chableeModel.itemId) &&
                        alItemDb.get(i).itemId.equalsIgnoreCase(chableeModel.itemId)) {

                    // item exists in database
                    // update dynamically changing data e.g. category, label
                    alItemDb.get(i).category = chableeModel.category;
                    alItemDb.get(i).label = Utils.retrieveChableeItemLabel(alItemDb.get(i));
                    alItemDb.get(i).price = chableeModel.price;
                    alItemDb.get(i).salePrice = chableeModel.salePrice;
                    alItemDb.get(i).title = chableeModel.title;
                    alItemDb.get(i).description = chableeModel.description;
                    alItemDb.get(i).purchaseUrl = chableeModel.purchaseUrl;
                    alItemDb.get(i).imageUrl1 = chableeModel.imageUrl1;
                    alItemDb.get(i).imageUrl2 = chableeModel.imageUrl2;
                    alItemDb.get(i).imageUrl3 = chableeModel.imageUrl3;
                    alItemDb.get(i).imageUrl4 = chableeModel.imageUrl4;
                    alItemDb.get(i).imageUrl5 = chableeModel.imageUrl5;
                    alItemDb.get(i).isBrowseItem = chableeModel.isBrowseItem;
                    alItemDb.get(i).isFeatured = chableeModel.isFeatured;
                    alItemDb.get(i).isMostPopular = chableeModel.isMostPopular;
                    break;
                }
            }
        }

        // update database
        new AsyncTaskUpdateDatabase(mContext, mItemProvider, alItemDb).execute();

        // add Chablee items to list
        ArrayList<ItemDatabaseModel> items = new ArrayList<>();
        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).category.equalsIgnoreCase(mCategory)) {
                // add item
                items.add(alItemDb.get(i));
            }
        }

        // set adapter
        mItemAdapter.updateData(items);
        swipeRefreshLayout.setRefreshing(false);
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
        if (!FrameworkUtils.checkIfNull(mErrorUtils)) {
            // dismiss error dialog
            mErrorUtils.dismiss();
        }
        super.onDetach();
        // enable drawer
        ((MainActivity) mContext).setDrawerState(true);
    }
}
