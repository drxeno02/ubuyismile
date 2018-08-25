package com.blog.ljtatum.ubuyismile.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.device.DeviceUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.activity.MainActivity;
import com.blog.ljtatum.ubuyismile.adapter.ItemAutoCompletedAdapter;
import com.blog.ljtatum.ubuyismile.adapter.SearchHistoryAdapter;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateItemDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.enums.Enum;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;
import com.blog.ljtatum.ubuyismile.interfaces.OnDatabaseChangeListener;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    private static final int MAX_SEARCH_HISTORY_RESULTS = 15;

    private Context mContext;
    private Activity mActivity;
    private View mRootView;

    private String mSearchCategory;
    private TextView tvItemDetailCta;
    private ImageView ivClear, ivBack;
    private AutoCompleteTextView acSearch;
    private ItemDatabaseModel mSelectedItem;

    // adapter
    private ItemAutoCompletedAdapter itemAutoCompleteAdapter;
    private SearchHistoryAdapter searchHistoryAdapter;
    private RecyclerView rvSearchHistory;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_search, container, false);

        // instantiate views
        initializeViews();
        initializeHandlers();
        initializeListeners();
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

        // initialize adapter (auto complete)
        acSearch = mRootView.findViewById(R.id.ac_search);
        ivClear = mRootView.findViewById(R.id.iv_clear);
        ivBack = mRootView.findViewById(R.id.iv_back);
        tvItemDetailCta = mRootView.findViewById(R.id.tv_item_detail);
        itemAutoCompleteAdapter = new ItemAutoCompletedAdapter(mContext, R.layout.item_auto_complete, alItemDb);
        acSearch.setAdapter(itemAutoCompleteAdapter);

        // initialize adapter (search history)
        rvSearchHistory = mRootView.findViewById(R.id.rv_search_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchHistory.setLayoutManager(layoutManager);
        searchHistoryAdapter = new SearchHistoryAdapter(mContext, getSearchHistory());
        rvSearchHistory.setAdapter(searchHistoryAdapter);

        // request focus
        acSearch.requestFocus();

        // set CTA state
        setCtaEnabled(false);
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        ivClear.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvItemDetailCta.setOnClickListener(this);
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {
        acSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // hide keyboard
                DeviceUtils.hideKeyboard(mActivity, mActivity.getWindow().getDecorView().getWindowToken());
                // retrieve selected item
                mSelectedItem = (ItemDatabaseModel) acSearch.getAdapter().getItem(i);
                // set text
                acSearch.setText(mSelectedItem.title);
                // set cursor end of text
                acSearch.setSelection(acSearch.getText().length());
                // update search history
                updateSearchHistory();
            }
        });

        // addTextChangedListener listener
        acSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    // set visibility
                    FrameworkUtils.setViewVisible(ivClear);
                } else {
                    // set visibility
                    FrameworkUtils.setViewGone(ivClear);
                    // reset selected item
                    mSelectedItem = null;
                    // set CTA state
                    setCtaEnabled(false);
                }

                // check if text has been change from selected results
                if (tvItemDetailCta.isEnabled()) {
                    if (!FrameworkUtils.checkIfNull(mSelectedItem) &&
                            acSearch.getText().length() > 0 && !mSelectedItem.title.trim().equalsIgnoreCase(
                            acSearch.getText().toString().trim())) {
                        // set CTA state
                        setCtaEnabled(false);
                    }
                } else {
                    if (!FrameworkUtils.checkIfNull(mSelectedItem) &&
                            acSearch.getText().length() > 0 && mSelectedItem.title.trim().equalsIgnoreCase(
                            acSearch.getText().toString().trim())) {
                        // set CTA state
                        setCtaEnabled(true);
                    }
                }
            }
        });

        // onClick listener
        SearchHistoryAdapter.onClickAdapterListener(new OnClickAdapterListener() {
            @Override
            public void onClick(ItemDatabaseModel item) {
                // hide keyboard
                DeviceUtils.hideKeyboard(mActivity, mActivity.getWindow().getDecorView().getWindowToken());
                // retrieve selected item
                mSelectedItem = item;
                // set text
                acSearch.setText(mSelectedItem.title);
                // set cursor end of text
                acSearch.setSelection(acSearch.getText().length());
                // update search history
                updateSearchHistory();
            }
        });

        // onDatabseChange listener
        AsyncTaskUpdateItemDatabase.onDatabaseChangeListener(new OnDatabaseChangeListener() {
            @Override
            public void onDatabaseUpdate() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // update item list from database
                        alItemDb = mItemProvider.getAllInfo();
                        // update adapter
                        searchHistoryAdapter.updateData(getSearchHistory());
                        // set CTA state
                        setCtaEnabled(true);
                    }
                });
            }
        });
    }

    /**
     * Method is used to retrieve bundle information
     */
    private void getBundle() {
        Bundle args = getArguments();
        if (!FrameworkUtils.checkIfNull(args)) {
            mSearchCategory = args.getString(Constants.KEY_SEARCH_CATEGORY, "");

            if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.ALL.toString())) {
                // do not filter results when searching for all items
                return;
            }

            // create filtered list
            List<ItemDatabaseModel> filteredItemList = new ArrayList<>();

            // filter searchable results
            for (int i = 0; i < alItemDb.size(); i++) {

                if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.ALL_GOOD_DEALS.toString())) {
                    if (Utils.isItemOnSale(alItemDb.get(i)) || alItemDb.get(i).category.equalsIgnoreCase(
                            com.app.amazon.framework.enums.Enum.ItemCategory.DEALS.toString())) {
                        filteredItemList.add(alItemDb.get(i));
                    }
                } else if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.BOOKS.toString())) {
                    if (alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.BOOKS.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.KINDLE_STORE.toString())) {
                        filteredItemList.add(alItemDb.get(i));
                    }
                } else if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.ELECTRONICS.toString())) {
                    if (alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.ELECTRONICS.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.APPLIANCES.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.PC_HARDWARE.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.SOFTWARE.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.VIDEO_GAMES.toString())) {
                        filteredItemList.add(alItemDb.get(i));
                    }
                } else if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.FOOD.toString())) {
                    if (alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.GROCERY.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.AMAZON_PANTRY.toString())) {
                        filteredItemList.add(alItemDb.get(i));
                    }
                } else if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.HEALTH_BEAUTY.toString())) {
                    if (alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.BEAUTY.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.HEALTH_AND_PERSONAL_CARE.toString()) ||
                            alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.LUXURY_BEAUTY.toString())) {
                        filteredItemList.add(alItemDb.get(i));
                    }
                } else if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.MOVIES.toString())) {
                    if (alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.DVD.toString())) {
                        filteredItemList.add(alItemDb.get(i));
                    }
                } else if (mSearchCategory.equalsIgnoreCase(Enum.SearchCategory.VIDEO_GAMES.toString())) {
                    if (alItemDb.get(i).category.equalsIgnoreCase(com.app.amazon.framework.enums.Enum.ItemCategory.VIDEO_GAMES.toString())) {
                        filteredItemList.add(alItemDb.get(i));
                    }
                }
            }

            // update adapter
            itemAutoCompleteAdapter.updateData(filteredItemList);
        }
    }

    /**
     * Method is used to enable / disable the Call To Action (CTA) button
     *
     * @param isEnabled True if the CTA button should be enabled, otherwise false
     */
    private void setCtaEnabled(boolean isEnabled) {
        tvItemDetailCta.setEnabled(isEnabled);
        if (isEnabled) {
            tvItemDetailCta.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvItemDetailCta.setBackground(ContextCompat.getDrawable(mContext, R.drawable.pill_blue));
        } else {
            tvItemDetailCta.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            tvItemDetailCta.setBackground(ContextCompat.getDrawable(mContext, R.drawable.pill_grey));
        }
    }

    /**
     * Method is used to update search history
     */
    private void updateSearchHistory() {
        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).itemId.equalsIgnoreCase(mSelectedItem.itemId)) {
                alItemDb.get(i).timestampSearch = FrameworkUtils.getCurrentDateTime();
                alItemDb.get(i).itemId = alItemDb.get(i).itemId;
                alItemDb.get(i).isSearch = true;
                break;
            }
        }
        // update database
        new AsyncTaskUpdateItemDatabase(mContext, mItemProvider, alItemDb, null).execute();
    }

    /**
     * Method is used to retrieve list of items that have been recently searched
     *
     * @return List of items that have been recently searched
     */
    private List<ItemDatabaseModel> getSearchHistory() {
        // create new empty search history list
        List<ItemDatabaseModel> alFilteredSearchHistory = new ArrayList<>();

        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).isSearch) {
                // add item to search history list
                alFilteredSearchHistory.add(alItemDb.get(i));
            }
        }

        // order search items by date/time
        Collections.sort(alFilteredSearchHistory, new Comparator<ItemDatabaseModel>() {
            public int compare(ItemDatabaseModel obj1, ItemDatabaseModel obj2) {
                if (FrameworkUtils.isStringEmpty(obj1.timestampSearch) ||
                        FrameworkUtils.isStringEmpty(obj2.timestampSearch)) {
                    return 0;
                }
                return obj2.timestampSearch.compareTo(obj1.timestampSearch);
            }
        });

        if (alFilteredSearchHistory.size() > MAX_SEARCH_HISTORY_RESULTS) {
            // only allow maxed number of recently searched items
            return alFilteredSearchHistory.subList(0, MAX_SEARCH_HISTORY_RESULTS);
        }
        return alFilteredSearchHistory;
    }

    @Override
    public void onClick(@NonNull View view) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (view.getId()) {
            case R.id.iv_back:
                remove();
                popBackStack();
            case R.id.iv_clear:
                // clear text
                acSearch.setText("");
                break;
            case R.id.tv_item_detail:
                // hide keyboard
                DeviceUtils.hideKeyboard(mActivity, mActivity.getWindow().getDecorView().getWindowToken());

                Bundle args = new Bundle();
                args.putString(Constants.KEY_ITEM_ID, mSelectedItem.itemId);
                args.putString(Constants.KEY_CATEGORY, mSelectedItem.category);
                args.putString(Constants.KEY_ITEM_TYPE, com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.SEARCH.toString());

                BaseFragment fragment = new DetailFragment();
                fragment.setArguments(args);
                fragment.setOnRemoveListener(new OnRemoveFragment() {
                    @Override
                    public void onRemove() {
                        // show keyboard
                        DeviceUtils.showKeyboard(mActivity);
                    }
                });
                addFragment(fragment);
                break;
            default:
                break;
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

    @Override
    public void onDetach() {
        // enable drawer
        ((MainActivity) mContext).setDrawerState(true);
        super.onDetach();
    }
}
