package com.blog.ljtatum.ubuyismile.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.amazon.framework.AmazonRequestManager;
import com.app.amazon.framework.enums.Enum;
import com.app.amazon.framework.interfaces.OnAWSRequestListener;
import com.app.amazon.framework.model.ItemId;
import com.app.amazon.framework.utils.AmazonProductAdvertisingApiRequestBuilder;
import com.app.amazon.framework.utils.AmazonWebServiceAuthentication;
import com.app.framework.listeners.OnFirebaseValueListener;
import com.app.framework.sharedpref.SharedPref;
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.apprater.AppRaterUtil;
import com.app.framework.utilities.dialog.DialogUtils;
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.app.framework.utilities.network.NetworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.adapter.ItemBrowseAdapter;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateItemDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.constants.Durations;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.fragments.AboutFragment;
import com.blog.ljtatum.ubuyismile.fragments.BaseFragment;
import com.blog.ljtatum.ubuyismile.fragments.ChableeFragment;
import com.blog.ljtatum.ubuyismile.fragments.DetailFragment;
import com.blog.ljtatum.ubuyismile.fragments.PrivacyFragment;
import com.blog.ljtatum.ubuyismile.fragments.SearchFragment;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;
import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.blog.ljtatum.ubuyismile.model.AmazonData;
import com.blog.ljtatum.ubuyismile.model.AmazonResponseModel;
import com.blog.ljtatum.ubuyismile.model.Categories;
import com.blog.ljtatum.ubuyismile.model.CreateDatabaseItemModel;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.DailyBonusUtils;
import com.blog.ljtatum.ubuyismile.utils.ErrorUtils;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.blog.ljtatum.ubuyismile.utils.HolidayUtils;
import com.blog.ljtatum.ubuyismile.utils.InfoBarUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.blog.ljtatum.ubuyismile.saxparse.SAXParseHandler.SAXParse;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Activity mActivity;
    private ErrorUtils mErrorUtils;
    private DrawerLayout mDrawerLayout;

    // information bar
    private InfoBarUtils mInfoBarUtils;
    // shared pref
    private SharedPref mSharedPref;
    // adapter
    private ItemBrowseAdapter itemBrowseAdapter;
    // container for banner ads
    private AdView adView;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        // initialize views and listeners
        initializeViews();
        initializeHandlers();
        initializeListeners();
        // toggle information bar
        toggleInfoBar(true);
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        mActivity = MainActivity.this;
        mErrorUtils = new ErrorUtils();
        mInfoBarUtils = new InfoBarUtils();

        // instantiate shared prefs
        mSharedPref = new SharedPref(this, com.app.framework.constants.Constants.PREF_FILE_NAME);
        if (mSharedPref.getIntPref(Constants.KEY_DAILY_BONUS, 0) < 7) {
            // daily bonus
            new DailyBonusUtils(this);
        } else {
            // rate this app
            new AppRaterUtil(this, getPackageName());
        }

        // track Happiness
        HappinessUtils.trackHappiness(HappinessUtils.EVENT_APP_LAUNCH);

        // holiday
        HolidayUtils holidayUtils = new HolidayUtils(this);
        // instantiate holiday views
        RelativeLayout rlHolidayParent = findViewById(R.id.rl_holiday_parent);
        TextView tvHolidayDateRange = findViewById(R.id.tv_holiday_date_range);
        TextView tvHolidayTitle = findViewById(R.id.tv_holiday_title);
        TextView tvHolidayMessage = findViewById(R.id.tv_holiday_message);
        if (holidayUtils.isHoliday()) {
            // display holiday UI
            tvHolidayDateRange.setText(holidayUtils.getHolidayDateRange());
            tvHolidayTitle.setText(holidayUtils.getHolidayTitle());
            tvHolidayMessage.setText(holidayUtils.getHolidayMessage());

            // set background
            rlHolidayParent.setBackground(ContextCompat.getDrawable(this, holidayUtils.getHolidayDrawable()));

            // set visibility
            FrameworkUtils.setViewVisible(rlHolidayParent);
        } else {
            // set visibility
            FrameworkUtils.setViewGone(rlHolidayParent);
        }

        // instantiate SQLite database
        mItemProvider = new ItemProvider(this);
        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();

        // initialize adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        RecyclerView rvItems = findViewById(R.id.rv_items);
        rvItems.setLayoutManager(gridLayoutManager);
        itemBrowseAdapter = new ItemBrowseAdapter(this, alItemDb);
        rvItems.setAdapter(itemBrowseAdapter);

        // ad banner
        adView = findViewById(R.id.ad_view);
        try {
            if (NetworkUtils.isNetworkAvailable(this)
                    && NetworkUtils.isConnected(this)) {

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

        // drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        // navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {
        // onClick listener
        ItemBrowseAdapter.onClickAdapterListener(new OnClickAdapterListener() {
            @Override
            public void onClick(ItemDatabaseModel item) {
                Bundle args = new Bundle();
                args.putString(Constants.KEY_ITEM_ID, item.itemId);
                args.putString(Constants.KEY_CATEGORY, item.category);
                args.putString(Constants.KEY_ITEM_TYPE, com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString());

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
     * Method is used to display Browse items. These are randomly selected items chosen to
     * be highlighted during initial load
     */
    private void setBrowseAdapter() {
        List<ItemDatabaseModel> items = new ArrayList<>();

        // add Amazon browse items to list
        // TODO add browse items for Amazon

        // add Chablee items to list
        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).isBrowseItem || Utils.isBrowseItem()) {
                // add item
                items.add(alItemDb.get(i));
            }
        }

        // update adapter
        itemBrowseAdapter.updateData(items);

        // dismiss progress dialog
        DialogUtils.dismissProgressDialog();
    }

    /**
     * Method is used to display/hide information bar
     *
     * @param isDisplay True to display information bar, otherwise false
     */
    public void toggleInfoBar(boolean isDisplay) {
        if (isDisplay) {
            if (mSharedPref.getIntPref(com.app.framework.constants.Constants.KEY_APP_LAUNCH_COUNT, 0) < 5) {
                // display information bar 100% of the time
                mInfoBarUtils.showInfoBar(mActivity, false);
            } else {
                Random rand = new Random();
                // display information bar 20% of the time
                if (rand.nextInt(10) <= 2) {
                    mInfoBarUtils.showInfoBar(mActivity, false);
                }
            }
        } else {
            // display information bar
            mInfoBarUtils.dismiss();
        }
    }

    /**
     * Method is used to enable/disable drawer
     *
     * @param isDrawerUnlocked True to enable drawer interaction, otherwise disable interaction
     */
    public void setDrawerState(boolean isDrawerUnlocked) {
        if (!FrameworkUtils.checkIfNull(mDrawerLayout)) {
            if (isDrawerUnlocked) {
                // only unlock (enable) drawer interaction if it is disabled
                if (mDrawerLayout.getDrawerLockMode(GravityCompat.START) != DrawerLayout.LOCK_MODE_UNLOCKED) {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            } else {
                // only allow disabling of drawer interaction if the drawer is closed
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        }
    }

    @Override
    public void onClick(@NonNull View view) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (view.getId()) {
            case R.id.iv_close_info:
                // toggle information bar
                toggleInfoBar(false);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        BaseFragment fragment = null;
        Bundle args = new Bundle();
        switch (item.getItemId()) {
            case R.id.nav_browse:
                removeAllFragments();
                break;
            case R.id.nav_search:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.ALL.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_chablee_crowns:
                args.putString(Constants.KEY_CATEGORY, Enum.ItemCategoryChablee.CROWNS.toString());
                fragment = new ChableeFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_chablee_rings:
                args.putString(Constants.KEY_CATEGORY, Enum.ItemCategoryChablee.RINGS.toString());
                fragment = new ChableeFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_chablee_necklaces:
                args.putString(Constants.KEY_CATEGORY, Enum.ItemCategoryChablee.NECKLACES.toString());
                fragment = new ChableeFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_chablee_rocks:
                args.putString(Constants.KEY_CATEGORY, Enum.ItemCategoryChablee.ROCKS.toString());
                fragment = new ChableeFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_all_good_deals:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.ALL_GOOD_DEALS.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_quick_search_books:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.BOOKS.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_quick_search_electronics:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.ELECTRONICS.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_quick_search_food:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.FOOD.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_quick_search_health_beauty:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.HEALTH_BEAUTY.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_quick_search_movies:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.MOVIES.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_quick_search_video_games:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.VIDEO_GAMES.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_quick_search_chablee:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.CHABLEE.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.nav_privacy:
                fragment = new PrivacyFragment();
                break;
            default:
                break;
        }
        // add fragment
        if (!FrameworkUtils.checkIfNull(fragment)) {
            addFragment(fragment);
        }

        // close drawer after selection
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    public void onPause() {
        // pause adview
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // resume adview
        adView.resume();
    }

    @Override
    protected void onDestroy() {
        if (!FrameworkUtils.checkIfNull(mErrorUtils)) {
            // dismiss error dialog
            mErrorUtils.dismiss();
        }
        if (!FrameworkUtils.checkIfNull(mInfoBarUtils)) {
            // dismiss information dialog
            mInfoBarUtils.dismiss();
        }
        if (!FrameworkUtils.checkIfNull(adView)) {
            // destroy the adview
            adView.destroy();
        }
        super.onDestroy();
    }
}