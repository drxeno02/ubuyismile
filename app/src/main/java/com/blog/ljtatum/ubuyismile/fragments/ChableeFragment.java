package com.blog.ljtatum.ubuyismile.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.app.framework.utilities.network.NetworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.activity.MainActivity;
import com.blog.ljtatum.ubuyismile.adapter.ItemDetailAdapter;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateItemDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.constants.Durations;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.ErrorUtils;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJTat on 1/3/2018.
 */

public class ChableeFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;
    private ErrorUtils mErrorUtils;
    private View mRootView;
    private String mCategory;
    private TextView tvFragmentHeader, tvNoItems;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    // adapter
    private ItemDetailAdapter itemDetailAdapter;

    // refresh layout
    private SwipeRefreshLayout swipeRefreshLayout;

    // container for banner ads
    private AdView adView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        mActivity = getActivity();
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
        itemDetailAdapter = new ItemDetailAdapter(mContext, new ArrayList<ItemDatabaseModel>());
        rvItems.setAdapter(itemDetailAdapter);

        // ad banner
        adView = mRootView.findViewById(R.id.ad_view);
        try {
            if (NetworkUtils.isNetworkAvailable(mContext)
                    && NetworkUtils.isConnected(mContext)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // request banner ads
                        AdRequest adRequestBanner;
                        if (Constants.DEBUG) {
                            // load test ad
                            adRequestBanner = new AdRequest.Builder().addTestDevice(Constants.AD_ID_TEST).build();
                        } else {
                            // load production ad
                            adRequestBanner = new AdRequest.Builder().build();
                        }
                        // load banner ads
                        adView.loadAd(adRequestBanner);
                    }
                }, Durations.DELAY_INTERVAL_MS_500);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            // set default ad image
            adView.setBackgroundResource(R.drawable.banner);
        }

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
        // OnRefreshListener
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
                mErrorUtils.showError(mActivity, getResources().getString(R.string.default_error_message));
            }
        });

        // onClick listener
        ItemDetailAdapter.onClickAdapterListener(new OnClickAdapterListener() {
            @Override
            public void onClick(ItemDatabaseModel item) {
                Bundle args = new Bundle();
                args.putString(Constants.KEY_ITEM_ID, item.itemId);
                args.putString(Constants.KEY_CATEGORY, item.category);
                args.putString(Constants.KEY_ITEM_TYPE, com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString());

                BaseFragment fragment = new DetailFragment();
                fragment.setArguments(args);
                fragment.setOnRemoveListener(new BaseFragment.OnRemoveFragment() {
                    @Override
                    public void onRemove() {
                        // update database list
                        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();
                    }
                });
                addFragment(fragment);
            }
        });

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // do nothing
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // set default ad image
                adView.setBackgroundResource(R.drawable.banner);
            }

            @Override
            public void onAdOpened() {
                // do nothing
            }

            @Override
            public void onAdLeftApplication() {
                // do nothing
            }

            @Override
            public void onAdClosed() {
                // do nothing
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
            List<ItemDatabaseModel> items = new ArrayList<>();
            for (int i = 0; i < alItemDb.size(); i++) {
                if (alItemDb.get(i).category.equalsIgnoreCase(mCategory)) {
                    // add item
                    items.add(alItemDb.get(i));
                }
            }

            if (items.size() > 0) {
                // set adapter
                FrameworkUtils.setViewGone(tvNoItems);
                itemDetailAdapter.updateData(items);
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
                chableeModel.itemType = com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString();
                chableeModel.isLabelSet = false;

                if (!FrameworkUtils.isStringEmpty(chableeModel.title) &&
                        !FrameworkUtils.isStringEmpty(chableeModel.description)) {
                    // update database values for existing items or add new item to database
                    boolean isItemExisting = false;
                    int index = 0;
                    for (int i = 0; i < alItemDb.size(); i++) {
                        if (!FrameworkUtils.isStringEmpty(alItemDb.get(i).itemId) &&
                                !FrameworkUtils.isStringEmpty(chableeModel.itemId) &&
                                alItemDb.get(i).itemId.equalsIgnoreCase(chableeModel.itemId)) {
                            // set index
                            index = i;
                            // set flag
                            isItemExisting = true;
                            break;
                        }
                    }

                    if (isItemExisting) {
                        // item exists in database
                        // update dynamically changing data e.g. category, label
                        alItemDb.get(index).category = chableeModel.category;
                        alItemDb.get(index).asin = chableeModel.asin;
                        alItemDb.get(index).label = Utils.retrieveChableeItemLabel(alItemDb.get(index));
                        alItemDb.get(index).itemId = chableeModel.itemId;
                        alItemDb.get(index).itemType = chableeModel.itemType;
                        alItemDb.get(index).price = chableeModel.price;
                        alItemDb.get(index).salePrice = chableeModel.salePrice;
                        alItemDb.get(index).title = chableeModel.title;
                        alItemDb.get(index).description = chableeModel.description;
                        alItemDb.get(index).purchaseUrl = chableeModel.purchaseUrl;
                        alItemDb.get(index).imageUrl1 = chableeModel.imageUrl1;
                        alItemDb.get(index).imageUrl2 = chableeModel.imageUrl2;
                        alItemDb.get(index).imageUrl3 = chableeModel.imageUrl3;
                        alItemDb.get(index).imageUrl4 = chableeModel.imageUrl4;
                        alItemDb.get(index).imageUrl5 = chableeModel.imageUrl5;
                        alItemDb.get(index).isBrowseItem = chableeModel.isBrowseItem;
                        alItemDb.get(index).isFeatured = chableeModel.isFeatured;
                        alItemDb.get(index).isMostPopular = chableeModel.isMostPopular;
                        alItemDb.get(index).isLabelSet = !Utils.isItemTimestampBeforeModifiedTimestamp(
                                alItemDb.get(index), false);
                    } else {
                        // item does not exist in database
                        // stored data
                        ItemDatabaseModel itemDatabaseModel = new ItemDatabaseModel();
                        itemDatabaseModel.category = chableeModel.category;
                        itemDatabaseModel.asin = chableeModel.asin;
                        itemDatabaseModel.label = chableeModel.label;
                        itemDatabaseModel.timestamp = chableeModel.timestamp;
                        itemDatabaseModel.timestampSearch = chableeModel.timestampSearch;
                        itemDatabaseModel.itemId = chableeModel.itemId;
                        itemDatabaseModel.itemType = chableeModel.itemType;
                        itemDatabaseModel.price = chableeModel.price;
                        itemDatabaseModel.salePrice = chableeModel.salePrice;
                        itemDatabaseModel.title = chableeModel.title;
                        itemDatabaseModel.description = chableeModel.description;
                        itemDatabaseModel.purchaseUrl = chableeModel.purchaseUrl;
                        itemDatabaseModel.imageUrl1 = chableeModel.imageUrl1;
                        itemDatabaseModel.imageUrl2 = chableeModel.imageUrl2;
                        itemDatabaseModel.imageUrl3 = chableeModel.imageUrl3;
                        itemDatabaseModel.imageUrl4 = chableeModel.imageUrl4;
                        itemDatabaseModel.imageUrl5 = chableeModel.imageUrl5;
                        itemDatabaseModel.isBrowseItem = chableeModel.isBrowseItem;
                        itemDatabaseModel.isFeatured = chableeModel.isFeatured;
                        itemDatabaseModel.isMostPopular = chableeModel.isMostPopular;
                        itemDatabaseModel.isFavorite = chableeModel.isFavorite;
                        itemDatabaseModel.isSearch = chableeModel.isSearch;
                        itemDatabaseModel.isLabelSet = chableeModel.isLabelSet;
                        alItemDb.add(itemDatabaseModel);
                    }
                }
            }
        }

        // update database
        new AsyncTaskUpdateItemDatabase(mContext, mItemProvider, alItemDb, null).execute();

        // add Chablee items to list
        List<ItemDatabaseModel> items = new ArrayList<>();
        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).category.equalsIgnoreCase(mCategory)) {
                // add item
                items.add(alItemDb.get(i));
            }
        }

        if (items.size() > 0) {
            // set adapter
            FrameworkUtils.setViewGone(tvNoItems);
            itemDetailAdapter.updateData(items);
        } else {
            // empty list
            FrameworkUtils.setViewVisible(tvNoItems);
        }
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
        // enable drawer
        ((MainActivity) mContext).setDrawerState(true);
        super.onDetach();
    }
}
