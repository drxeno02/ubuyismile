package com.blog.ljtatum.ubuyismile.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.amazon.framework.RequestManager;
import com.app.amazon.framework.enums.Enum;
import com.app.amazon.framework.interfaces.OnAWSRequestListener;
import com.app.amazon.framework.utils.AmazonWebServiceAuthentication;
import com.app.framework.listeners.OnFirebaseValueListener;
import com.app.framework.sharedpref.SharedPref;
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.apprater.AppRaterUtil;
import com.app.framework.utilities.dialog.DialogUtils;
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.adapter.ItemBrowseAdapter;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
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
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.ErrorUtils;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.blog.ljtatum.ubuyismile.utils.InfoBarUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.blog.ljtatum.ubuyismile.saxparse.SAXParseHandler.SAXParse;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String ID_PREFIX = "id_";

    private Activity mActivity;
    private ErrorUtils mErrorUtils;
    private DrawerLayout mDrawerLayout;
    private ArrayList<String> alAmazonCategories, alChableeCategories;
    private int categoryIndex = 0; // default
    private boolean isAmazonFirebaseDataRetrieved, isChableeFirebaseDataRetrieved, isDbEmpty;

    // information bar
    private InfoBarUtils mInfoBarUtils;

    // shared pref
    private SharedPref mSharedPref;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    // adapter
    private ItemBrowseAdapter itemBrowseAdapter;

    // Amazon web service authentication
    AmazonWebServiceAuthentication mAmazonAuth;

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
        // retrieve firebase data
        retrieveFirebaseData();
        // toggle information bar
        toggleInfoBar(true);

        // uncomment below to create firebase db. Will also reset all data
//        createFirebaseDb();


        // B00W0TD6Y6 - Poetry in Programming
        // 076243631X - Mammoth Book of Tattoos
//        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder
//                .forItemLookup("B00W0TD6Y6, 076243631X", ItemId.Type.ISBN)
//                .includeInformationAbout(Enum.ResponseGroupItemLookup.IMAGES)
//                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, authentication);

//        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder
//                .forItemSearch("Title, Total, Rest"                                                                                                                                     )
//                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, authentication);

//        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder
//                .forItemBrowse(Enum.ItemBrowseNodeId.VIDEO_GAMES)
//                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, authentication);

//        mAsyncTask = new RequestTask().execute(requestUrl);
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        mActivity = MainActivity.this;
        mErrorUtils = new ErrorUtils();
        alAmazonCategories = Categories.getAmazonCategories(); // retrieve all Amazon categories
        alChableeCategories = Categories.getChableeCategories(); // retrieve all Chablee categories
        mInfoBarUtils = new InfoBarUtils();

        // instantiate shared prefs
        mSharedPref = new SharedPref(this, com.app.framework.constants.Constants.PREF_FILE_NAME);

        // rate this app
        new AppRaterUtil(this, getPackageName());

        // track Happiness
        HappinessUtils.trackHappiness(HappinessUtils.EVENT_APP_LAUNCH);

        // instantiate SQLite database
        mItemProvider = new ItemProvider(this);
        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();
        isDbEmpty = alItemDb.size() == 0;

        // instantiate Amazon auth
        mAmazonAuth = AmazonWebServiceAuthentication.create(
                getResources().getString(R.string.amazon_tag),
                getResources().getString(R.string.amazon_access_key),
                getResources().getString(R.string.amazon_secret_key));

        // initialize adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        RecyclerView rvItems = findViewById(R.id.rv_items);
        rvItems.setLayoutManager(gridLayoutManager);
        itemBrowseAdapter = new ItemBrowseAdapter(this, new ArrayList<ItemDatabaseModel>());
        rvItems.setAdapter(itemBrowseAdapter);

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
        // OnAWSRequestListener
        RequestManager.onAWSRequestListener(new OnAWSRequestListener() {
            @Override
            public void onAWSSuccess(@NonNull String response) {
                // retrieve item_a model
                AmazonResponseModel itemModel = SAXParse(response);
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
                if (!isAmazonFirebaseDataRetrieved) {
                    if (categoryIndex < alAmazonCategories.size()) {
                        // increase category index
                        categoryIndex++;
                    }
                } else if (!isChableeFirebaseDataRetrieved) {
                    if (categoryIndex < alChableeCategories.size()) {
                        // increase category index
                        categoryIndex++;
                    }
                }
                // retrieve firebase data
                retrieveFirebaseData();
            }

            @Override
            public void onRetrieveDataError(DatabaseError databaseError) {
                // display error dialog
                mErrorUtils.showError(MainActivity.this,
                        getResources().getString(R.string.default_error_message), "");
            }
        });

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
    }

    /**
     * Method is used to populate models with data
     *
     * @param dataSnapshot data retrieved from firebase
     */
    private void populateDataLists(@NonNull DataSnapshot dataSnapshot) {
        if (!isAmazonFirebaseDataRetrieved) {

            ArrayList<ItemModel> alData = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                if (!FrameworkUtils.checkIfNull(snapshot.getValue()) &&
                        !FrameworkUtils.isStringEmpty(snapshot.getValue().toString())) {
                    // local data
                    ItemModel itemModel = new ItemModel();
                    itemModel.category = alAmazonCategories.get(categoryIndex);
                    itemModel.asin = snapshot.getValue().toString();
                    itemModel.label = com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.NEW.toString();
                    itemModel.timestamp = FrameworkUtils.getCurrentDateTime();
                    itemModel.isBrowseItem = Utils.isBrowseItem();
                    alData.add(itemModel);

                    if (isDbEmpty) {
                        // stored data
                        ItemDatabaseModel itemDatabaseModel = new ItemDatabaseModel();
                        itemDatabaseModel.category = itemModel.category;
                        itemDatabaseModel.asin = itemModel.asin;
                        itemDatabaseModel.label = itemModel.label;
                        itemDatabaseModel.timestamp = itemModel.timestamp;
                        itemDatabaseModel.price = "";
                        itemDatabaseModel.salePrice = "";
                        itemDatabaseModel.title = "";
                        itemDatabaseModel.description = "";
                        itemDatabaseModel.purchaseUrl = "";
                        itemDatabaseModel.imageUrl1 = "";
                        itemDatabaseModel.imageUrl2 = "";
                        itemDatabaseModel.imageUrl3 = "";
                        itemDatabaseModel.imageUrl4 = "";
                        itemDatabaseModel.imageUrl5 = "";
                        itemDatabaseModel.isBrowseItem = itemModel.isBrowseItem;
                        itemDatabaseModel.isFeatured = false;
                        itemDatabaseModel.isMostPopular = false;
                        itemDatabaseModel.isFavorite = false;
                        alItemDb.add(itemDatabaseModel);
                    } else {
                        // TODO iterate through SQLite db and update values
                    }
                }
            }

            if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.DEALS.toString())) {
                // set deals list
                AmazonData.setDeals(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.APPAREL.toString())) {
                // set apparel list
                AmazonData.setApparel(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.APPLIANCES.toString())) {
                // set appliances list
                AmazonData.setAppliances(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.AUTOMOTIVE.toString())) {
                // set automotive list
                AmazonData.setAutomotive(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.BABY.toString())) {
                // set baby list
                AmazonData.setBaby(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.BEAUTY.toString())) {
                // set beauty list
                AmazonData.setBeauty(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.BOOKS.toString())) {
                // set book list
                AmazonData.setBooks(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.DVD.toString())) {
                // set DVD list
                AmazonData.setDVD(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.ELECTRONICS.toString())) {
                // set electronics list
                AmazonData.setElectronics(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.GROCERY.toString())) {
                // set grocery list
                AmazonData.setGrocery(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.HEALTH_AND_PERSONAL_CARE.toString())) {
                // set health and personal care list
                AmazonData.setHealthPesonalCare(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.HOME_AND_GARDEN.toString())) {
                // set home and garden list
                AmazonData.setHomeGarden(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.JEWELRY.toString())) {
                // set jewelry list
                AmazonData.setJewelry(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.KINDLE_STORE.toString())) {
                // set kindle store list
                AmazonData.setKindleStore(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.LAWN_AND_GARDEN.toString())) {
                // set lawn and garden list
                AmazonData.setLawnGarden(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.LUGGAGE_AND_BAGS.toString())) {
                // set luggage bugs list
                AmazonData.setLuggageBags(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.LUXURY_BEAUTY.toString())) {
                // set luxery and beauty list
                AmazonData.setLuxeryBeauty(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.MUSIC.toString())) {
                // set music list
                AmazonData.setMusic(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.MUSICAL_INSTRUMENTS.toString())) {
                // set musical instruments list
                AmazonData.setMusicalInstruments(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.OFFICE_PRODUCTS.toString())) {
                // set office products list
                AmazonData.setOfficeProducts(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.AMAZON_PANTRY.toString())) {
                // set Amazon pantry list
                AmazonData.setAmazonPantry(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.PC_HARDWARE.toString())) {
                // set pc hardware list
                AmazonData.setPCHardware(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.PET_SUPPLIES.toString())) {
                // set pet supplies list
                AmazonData.setPetSupplies(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.SHOES.toString())) {
                // set shoes list
                AmazonData.setShoes(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.SOFTWARE.toString())) {
                // set software list
                AmazonData.setSoftware(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.SPORTING_GOODS.toString())) {
                // set sporting goods list
                AmazonData.setSportingGoods(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.TOYS.toString())) {
                // set toys list
                AmazonData.setToys(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.VIDEO_GAMES.toString())) {
                // set videogames list
                AmazonData.setVideoGames(alData);
            } else if (alData.size() > 0 && categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.WATCHES.toString())) {
                // set watches list
                AmazonData.setWatches(alData);
            }

        } else if (!isChableeFirebaseDataRetrieved) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                ItemModel chableeModel = snapshot.getValue(ItemModel.class);
                if (!FrameworkUtils.checkIfNull(snapshot.getValue()) &&
                        !FrameworkUtils.checkIfNull(chableeModel)) {
                    chableeModel.category = alChableeCategories.get(categoryIndex);
                    chableeModel.asin = ""; // no asin for Chablee items
                    chableeModel.label = com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.NEW.toString();
                    chableeModel.timestamp = FrameworkUtils.getCurrentDateTime();
                    chableeModel.itemType = com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString();
                    chableeModel.isLabelSet = false;

                    // only add data if it exists. This is enforced by the required
                    // title and description values
                    if (isDbEmpty) {
                        if (!FrameworkUtils.isStringEmpty(chableeModel.title) &&
                                !FrameworkUtils.isStringEmpty(chableeModel.description)) {
                            // stored data
                            ItemDatabaseModel itemDatabaseModel = new ItemDatabaseModel();
                            itemDatabaseModel.category = chableeModel.category;
                            itemDatabaseModel.asin = chableeModel.asin;
                            itemDatabaseModel.label = chableeModel.label;
                            itemDatabaseModel.timestamp = chableeModel.timestamp;
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
                            itemDatabaseModel.isLabelSet = chableeModel.isLabelSet;
                            alItemDb.add(itemDatabaseModel);
                        }
                    } else {
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
                                alItemDb.get(index).label = Utils.retrieveChableeItemLabel(alItemDb.get(index));
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
                                itemDatabaseModel.isLabelSet = chableeModel.isLabelSet;
                                alItemDb.add(itemDatabaseModel);
                            }
                        }
                    }
                }
            }
        } else {
            // Amazon and Chablee requests made
            // make Amazon requests
            AmazonData.getAmazonASINRequest(AmazonData.getBooks());
        }
    }

    /**
     * Method is used to retrieve Firebase data
     */
    private void retrieveFirebaseData() {
        if (categoryIndex == 0) {
            // show progress dialog
            DialogUtils.showProgressDialog(this);
        }

        // retrieve more data from Amazon so long that category index is less than
        // Amazon category list size otherwise retrieve Chablee data
        if (!isAmazonFirebaseDataRetrieved) {
            if (categoryIndex < alAmazonCategories.size()) {
                // retrieve data (AMAZON)
                FirebaseUtils.retrieveItemsAmazon(alAmazonCategories.get(categoryIndex));
            } else {
                // reset
                categoryIndex = 0;
                // Amazon queries completed
                isAmazonFirebaseDataRetrieved = true;
                // retrieve data (CHABLEE)
                FirebaseUtils.retrieveItemsChablee(alChableeCategories.get(categoryIndex));
            }
        } else if (!isChableeFirebaseDataRetrieved) {
            if (categoryIndex < alChableeCategories.size()) {
                // retrieve data (CHABLEE)
                FirebaseUtils.retrieveItemsChablee(alChableeCategories.get(categoryIndex));
            } else {
                // reset
                categoryIndex = 0;
                // Chablee queries completed
                isChableeFirebaseDataRetrieved = true;

                if (isAmazonFirebaseDataRetrieved && isChableeFirebaseDataRetrieved) {
                    // set browse data
                    setBrowseAdapter();

                    if (isDbEmpty) {
                        createSQLiteDb();
                    } else {
                        // update database
                        new AsyncTaskUpdateDatabase(this, mItemProvider, alItemDb).execute();
                        printDb();
                    }
                }
            }
        }
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

        // set adapter
        itemBrowseAdapter.updateData(items);

        // dismiss progress dialog
        DialogUtils.dismissProgressDialog();
    }

    /**
     * Method is used to create SQLite db
     */
    private void createSQLiteDb() {
        if (!FrameworkUtils.checkIfNull(mItemProvider) && !FrameworkUtils.checkIfNull(alItemDb)) {
            mItemProvider.insert(alItemDb);
        }
    }

    /**
     * Method is used to display/hide information bar
     *
     * @param isDisplay True to display information bar, otherwise false
     */
    public void toggleInfoBar(boolean isDisplay) {
        if (isDisplay) {
            if (mSharedPref.getLongPref(com.app.framework.constants.Constants.KEY_APP_LAUNCH_COUNT, 0L) < 5) {
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
     * @param isEnabled True to enable drawer interaction, otherwise disable interaction
     */
    public void setDrawerState(boolean isEnabled) {
        if (!FrameworkUtils.checkIfNull(mDrawerLayout)) {
            if (isEnabled) {
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
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        BaseFragment fragment = null;
        Bundle args = new Bundle();
        switch (item.getItemId()) {
            case R.id.nav_browse:
                // TODO for this section remove all fragments and close drawer. This simply brings up the main screen
                break;
            case R.id.nav_search:
                args.putString(Constants.KEY_SEARCH_CATEGORY, com.blog.ljtatum.ubuyismile.enums.Enum.SearchCategory.ALL.toString());
                fragment = new SearchFragment();
                fragment.setArguments(args);
                break;
            case R.id.nav_settings:

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

    /**
     * Method is used to print contents of the database
     */
    private void printDb() {
        if (FrameworkUtils.checkIfNull(alItemDb) || alItemDb.size() == 0) {
            return;
        }

        for (int i = 0; i < alItemDb.size(); i++) {
            Logger.v(TAG, "category= " + alItemDb.get(i).category);
            Logger.v(TAG, "asin= " + alItemDb.get(i).asin);
            Logger.v(TAG, "label= " + alItemDb.get(i).label);
            Logger.v(TAG, "timestamp= " + alItemDb.get(i).timestamp);
            Logger.v(TAG, "price= " + alItemDb.get(i).price);
            Logger.v(TAG, "salePrice= " + alItemDb.get(i).salePrice);
            Logger.v(TAG, "title= " + alItemDb.get(i).title);
            Logger.v(TAG, "description= " + alItemDb.get(i).description);
            Logger.v(TAG, "purchaseUrl= " + alItemDb.get(i).purchaseUrl);
            Logger.v(TAG, "imageUrl1= " + alItemDb.get(i).imageUrl1);
            Logger.v(TAG, "imageUrl2= " + alItemDb.get(i).imageUrl2);
            Logger.v(TAG, "imageUrl3= " + alItemDb.get(i).imageUrl3);
            Logger.v(TAG, "imageUrl4= " + alItemDb.get(i).imageUrl4);
            Logger.v(TAG, "imageUrl5= " + alItemDb.get(i).imageUrl5);
            Logger.v(TAG, "isBrowseItem= " + alItemDb.get(i).isBrowseItem);
            Logger.v(TAG, "isFeatured= " + alItemDb.get(i).isFeatured);
            Logger.v(TAG, "isMostPopular= " + alItemDb.get(i).isMostPopular);
            Logger.v(TAG, "isFavorite= " + alItemDb.get(i).isFavorite);
        }
    }

    /**
     * Method is used to create/populate database on Firebase with empty data
     */
    private void createFirebaseDb() {

        // setup Chablee database
        HashMap<String, ItemModel> mapChablee = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            ItemModel chableeModel = new ItemModel();
            chableeModel.itemId = ID_PREFIX.concat(String.valueOf(i));
            chableeModel.title = " ";
            chableeModel.description = " ";
            chableeModel.price = " ";
            chableeModel.salePrice = " ";
            chableeModel.purchaseUrl = " ";
            chableeModel.imageUrl1 = " ";
            chableeModel.imageUrl2 = " ";
            chableeModel.imageUrl3 = " ";
            chableeModel.imageUrl4 = " ";
            chableeModel.imageUrl5 = " ";
            chableeModel.isFeatured = false;
            chableeModel.isMostPopular = false;
            // add to hashmap
            mapChablee.put(String.valueOf(i), chableeModel);
        }
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.CROWNS.toString());
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.RINGS.toString());
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.NECKLACES.toString());
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.GEMSTONE.toString());
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.ROCKS.toString());

        // setup Amazon database
        HashMap<String, String> mapAmazon = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            mapAmazon.put(String.valueOf(i), " ");
        }
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.DEALS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.APPAREL.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.APPLIANCES.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.AUTOMOTIVE.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.BABY.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.BEAUTY.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.BOOKS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.DVD.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.ELECTRONICS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.GROCERY.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.HEALTH_AND_PERSONAL_CARE.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.HOME_AND_GARDEN.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.JEWELRY.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.KINDLE_STORE.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.LAWN_AND_GARDEN.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.LUGGAGE_AND_BAGS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.LUXURY_BEAUTY.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.MUSIC.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.MUSICAL_INSTRUMENTS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.OFFICE_PRODUCTS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.AMAZON_PANTRY.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.PC_HARDWARE.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.PET_SUPPLIES.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.SHOES.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.SOFTWARE.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.SPORTING_GOODS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.TOYS.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.VIDEO_GAMES.toString());
        FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), Enum.ItemCategory.WATCHES.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        super.onDestroy();
    }
}