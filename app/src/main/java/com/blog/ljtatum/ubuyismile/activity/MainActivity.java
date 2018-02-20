package com.blog.ljtatum.ubuyismile.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.device.DeviceUtils;
import com.app.framework.utilities.dialog.DialogUtils;
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.adapter.ItemAdapter;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.fragments.AboutFragment;
import com.blog.ljtatum.ubuyismile.fragments.ChableeFragment;
import com.blog.ljtatum.ubuyismile.fragments.PrivacyFragment;
import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.blog.ljtatum.ubuyismile.model.AmazonData;
import com.blog.ljtatum.ubuyismile.model.AmazonResponseModel;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.ErrorUtils;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.blog.ljtatum.ubuyismile.saxparse.SAXParseHandler.SAXParse;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private ErrorUtils mErrorUtils;
    private DrawerLayout mDrawerLayout;
    private ArrayList<String> alAmazonCategories, alChableeCategories;
    private int categoryIndex = 0; // default
    private boolean isAmazonFirebaseDataRetrieved, isChableeFirebaseDataRetrieved;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    // adapter
    private LinearLayoutManager mLayoutManager;
    private ItemAdapter mItemAdapter;
    private RecyclerView rvItems;
    private ArrayList<ItemModel> alItems;

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

        // retrieve items from firebase to add to SQLite db
        if (alItemDb.size() == 0 && alItemDb.isEmpty()) {
            retrieveFirebaseData();
        } else {
            Logger.e("TEST", "sqlite db updated successfully");

            for (int i = 0; i < alItemDb.size(); i++) {
                Logger.v("TEST", "category= " + alItemDb.get(i).category);
                Logger.v("TEST", "asin= " + alItemDb.get(i).asin);
                Logger.v("TEST", "label= " + alItemDb.get(i).label);
                Logger.v("TEST", "timestamp= " + alItemDb.get(i).timestamp);
                Logger.v("TEST", "price= " + alItemDb.get(i).price);
                Logger.v("TEST", "salePrice= " + alItemDb.get(i).salePrice);
                Logger.v("TEST", "title= " + alItemDb.get(i).title);
                Logger.v("TEST", "description= " + alItemDb.get(i).description);
                Logger.v("TEST", "purchaseUrl= " + alItemDb.get(i).purchaseUrl);
                Logger.v("TEST", "imageUrl1= " + alItemDb.get(i).imageUrl1);
                Logger.v("TEST", "imageUrl2= " + alItemDb.get(i).imageUrl2);
                Logger.v("TEST", "imageUrl3= " + alItemDb.get(i).imageUrl3);
                Logger.v("TEST", "imageUrl4= " + alItemDb.get(i).imageUrl4);
                Logger.v("TEST", "imageUrl5= " + alItemDb.get(i).imageUrl5);
                Logger.v("TEST", "isBrowseItem= " + alItemDb.get(i).isBrowseItem);
                Logger.v("TEST", "isFeatured= " + alItemDb.get(i).isFeatured);
                Logger.v("TEST", "isMostPopular= " + alItemDb.get(i).isMostPopular);
            }
        }

        // uncomment below to create firebase db
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
        mContext = MainActivity.this;
        mErrorUtils = new ErrorUtils();
        alAmazonCategories = AmazonData.getAmazonCategories(); // retrieve all Amazon categories
        alChableeCategories = ChableeData.getChableeCategories(); // retrieve all Chablee categories
        alItems = new ArrayList<>();

        // track Happiness
        HappinessUtils.trackHappiness(null);

        // instantiate SQLite database
        mItemProvider = new ItemProvider(mContext);
        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();

        // instantiate Amazon auth
        mAmazonAuth = AmazonWebServiceAuthentication.create(
                getResources().getString(R.string.amazon_tag),
                getResources().getString(R.string.amazon_access_key),
                getResources().getString(R.string.amazon_secret_key));

        // initialize adapter
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItems = findViewById(R.id.rv_items);
        rvItems.setLayoutManager(mLayoutManager);
        mItemAdapter = new ItemAdapter(mContext, alItems,
                com.blog.ljtatum.ubuyismile.enums.Enum.AdapterType.BROWSE);
        rvItems.setAdapter(mItemAdapter);

        // drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (FrameworkUtils.checkIfNull(getTopFragment())) {
                    // show keyboard
                    DeviceUtils.showKeyboard(mContext);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // hide keyboard
                DeviceUtils.hideKeyboard(mContext, getWindow().getDecorView().getWindowToken());
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
                if (!isAmazonFirebaseDataRetrieved) {
                    if (categoryIndex < alAmazonCategories.size()) {
                        // increase category index
                        categoryIndex++;
                        retrieveFirebaseData();
                    }
                } else if (!isChableeFirebaseDataRetrieved) {
                    if (categoryIndex < alChableeCategories.size()) {
                        // increase category index
                        categoryIndex++;
                        retrieveFirebaseData();
                    }
                }
            }

            @Override
            public void onRetrieveDataError(DatabaseError databaseError) {
                // display error dialog
                mErrorUtils.showError(MainActivity.this,
                        mContext.getResources().getString(R.string.default_error_message), "");
            }
        });
    }

    /**
     * Method is used to populate models with data
     *
     * @param dataSnapshot data retrieved from firebase
     */
    private void populateDataLists(DataSnapshot dataSnapshot) {
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
                    alItemDb.add(itemDatabaseModel);
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
            ArrayList<ItemModel> alData = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                ItemModel chableeModel = snapshot.getValue(ItemModel.class);
                if (!FrameworkUtils.checkIfNull(snapshot.getValue()) &&
                        !FrameworkUtils.checkIfNull(chableeModel)) {
                    chableeModel.category = alChableeCategories.get(categoryIndex);
                    chableeModel.label = com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.NEW.toString();
                    chableeModel.timestamp = FrameworkUtils.getCurrentDateTime();
                    chableeModel.isBrowseItem = Utils.isBrowseItem();
                    alData.add(chableeModel);

                    // stored data
                    ItemDatabaseModel itemDatabaseModel = new ItemDatabaseModel();
                    itemDatabaseModel.category = chableeModel.category;
                    itemDatabaseModel.asin = "";
                    itemDatabaseModel.label = chableeModel.label;
                    itemDatabaseModel.timestamp = chableeModel.timestamp;
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
                    itemDatabaseModel.isBrowseItem = chableeModel.isBrowseItem;
                    itemDatabaseModel.isFeatured = false;
                    itemDatabaseModel.isMostPopular = false;
                    alItemDb.add(itemDatabaseModel);
                }
            }
            if (alData.size() > 0 && categoryIndex < alChableeCategories.size() &&
                    alChableeCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                // set crown list
                ChableeData.setCrowns(alData);
            } else if (alData.size() > 0 && categoryIndex < alChableeCategories.size() &&
                    alChableeCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                // set rings list
                ChableeData.setRings(alData);
            } else if (alData.size() > 0 && categoryIndex < alChableeCategories.size() &&
                    alChableeCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                // set necklaces list
                ChableeData.setNecklaces(alData);
            } else if (alData.size() > 0 && categoryIndex < alChableeCategories.size() &&
                    alChableeCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                // set rocks list
                ChableeData.setRocks(alData);
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
            DialogUtils.showProgressDialog(mContext);
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
                    // update SQLite db
                    updateSQLiteDb();
                }
            }
        }
    }

    /**
     * Method is used to display Browse items. These are randomly selected items chosen to
     * be highlighted during initial load
     */
    private void setBrowseAdapter() {
        // add Amazon browse items to list
        for (int i = 0; i < AmazonData.getAmazonCategories().size(); i++) {
            if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.DEALS.toString())) {
                for (int j = 0; j < AmazonData.getDeals().size(); j++) {
                    if (AmazonData.getDeals().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getDeals().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.APPAREL.toString())) {
                for (int j = 0; j < AmazonData.getApparel().size(); j++) {
                    if (AmazonData.getApparel().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getApparel().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.APPLIANCES.toString())) {
                for (int j = 0; j < AmazonData.getAppliances().size(); j++) {
                    if (AmazonData.getAppliances().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getAppliances().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.AUTOMOTIVE.toString())) {
                for (int j = 0; j < AmazonData.getAutomotive().size(); j++) {
                    if (AmazonData.getAutomotive().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getAutomotive().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.BABY.toString())) {
                for (int j = 0; j < AmazonData.getBaby().size(); j++) {
                    if (AmazonData.getBaby().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getBaby().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.BEAUTY.toString())) {
                for (int j = 0; j < AmazonData.getBeauty().size(); j++) {
                    if (AmazonData.getBeauty().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getBeauty().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.BOOKS.toString())) {
                for (int j = 0; j < AmazonData.getBooks().size(); j++) {
                    if (AmazonData.getBooks().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getBooks().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.DVD.toString())) {
                for (int j = 0; j < AmazonData.getDVD().size(); j++) {
                    if (AmazonData.getDVD().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getDVD().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.ELECTRONICS.toString())) {
                for (int j = 0; j < AmazonData.getElectronics().size(); j++) {
                    if (AmazonData.getElectronics().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getElectronics().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.GROCERY.toString())) {
                for (int j = 0; j < AmazonData.getGrocery().size(); j++) {
                    if (AmazonData.getGrocery().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getGrocery().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.HEALTH_AND_PERSONAL_CARE.toString())) {
                for (int j = 0; j < AmazonData.getHealthPesonalCare().size(); j++) {
                    if (AmazonData.getHealthPesonalCare().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getHealthPesonalCare().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.HOME_AND_GARDEN.toString())) {
                for (int j = 0; j < AmazonData.getHomeGarden().size(); j++) {
                    if (AmazonData.getHomeGarden().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getHomeGarden().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.JEWELRY.toString())) {
                for (int j = 0; j < AmazonData.getJewelry().size(); j++) {
                    if (AmazonData.getJewelry().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getJewelry().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.KINDLE_STORE.toString())) {
                for (int j = 0; j < AmazonData.getKindleStore().size(); j++) {
                    if (AmazonData.getKindleStore().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getKindleStore().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.LAWN_AND_GARDEN.toString())) {
                for (int j = 0; j < AmazonData.getLawnGarden().size(); j++) {
                    if (AmazonData.getLawnGarden().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getLawnGarden().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.LUGGAGE_AND_BAGS.toString())) {
                for (int j = 0; j < AmazonData.getLuggageBags().size(); j++) {
                    if (AmazonData.getLuggageBags().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getLuggageBags().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.LUXURY_BEAUTY.toString())) {
                for (int j = 0; j < AmazonData.getLuxeryBeauty().size(); j++) {
                    if (AmazonData.getLuxeryBeauty().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getLuxeryBeauty().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.MUSIC.toString())) {
                for (int j = 0; j < AmazonData.getMusic().size(); j++) {
                    if (AmazonData.getMusic().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getMusic().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.MUSICAL_INSTRUMENTS.toString())) {
                for (int j = 0; j < AmazonData.getMusicalInstruments().size(); j++) {
                    if (AmazonData.getMusicalInstruments().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getMusicalInstruments().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.OFFICE_PRODUCTS.toString())) {
                for (int j = 0; j < AmazonData.getOfficeProducts().size(); j++) {
                    if (AmazonData.getOfficeProducts().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getOfficeProducts().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.AMAZON_PANTRY.toString())) {
                for (int j = 0; j < AmazonData.getAmazonPantry().size(); j++) {
                    if (AmazonData.getAmazonPantry().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getAmazonPantry().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.PC_HARDWARE.toString())) {
                for (int j = 0; j < AmazonData.getPCHardware().size(); j++) {
                    if (AmazonData.getPCHardware().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getPCHardware().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.PET_SUPPLIES.toString())) {
                for (int j = 0; j < AmazonData.getPetSupplies().size(); j++) {
                    if (AmazonData.getPetSupplies().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getPetSupplies().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.SHOES.toString())) {
                for (int j = 0; j < AmazonData.getShoes().size(); j++) {
                    if (AmazonData.getShoes().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getShoes().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.SOFTWARE.toString())) {
                for (int j = 0; j < AmazonData.getSoftware().size(); j++) {
                    if (AmazonData.getSoftware().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getSoftware().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.SPORTING_GOODS.toString())) {
                for (int j = 0; j < AmazonData.getSportingGoods().size(); j++) {
                    if (AmazonData.getSportingGoods().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getSportingGoods().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.TOYS.toString())) {
                for (int j = 0; j < AmazonData.getToys().size(); j++) {
                    if (AmazonData.getToys().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getToys().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.VIDEO_GAMES.toString())) {
                for (int j = 0; j < AmazonData.getVideoGames().size(); j++) {
                    if (AmazonData.getVideoGames().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getVideoGames().get(j));
                    }
                }
            } else if (AmazonData.getAmazonCategories().get(i).equalsIgnoreCase(Enum.ItemCategory.WATCHES.toString())) {
                for (int j = 0; j < AmazonData.getWatches().size(); j++) {
                    if (AmazonData.getWatches().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(AmazonData.getWatches().get(j));
                    }
                }
            }
        }

        // add Chablee items to list
        for (int i = 0; i < ChableeData.getChableeCategories().size(); i++) {
            if (ChableeData.getChableeCategories().get(i).equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                for (int j = 0; j < ChableeData.getCrowns().size(); j++) {
                    if (ChableeData.getCrowns().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(ChableeData.getCrowns().get(j));
                    }
                }
            } else if (ChableeData.getChableeCategories().get(i).equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                for (int j = 0; j < ChableeData.getRings().size(); j++) {
                    if (ChableeData.getRings().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(ChableeData.getRings().get(j));
                    }
                }
            } else if (ChableeData.getChableeCategories().get(i).equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                for (int j = 0; j < ChableeData.getNecklaces().size(); j++) {
                    if (ChableeData.getNecklaces().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(ChableeData.getNecklaces().get(j));
                    }
                }
            } else if (ChableeData.getChableeCategories().get(i).equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                for (int j = 0; j < ChableeData.getRocks().size(); j++) {
                    if (ChableeData.getRocks().get(j).isBrowseItem) {
                        // add browse item
                        alItems.add(ChableeData.getRocks().get(j));
                    }
                }
            }
        }

        // set adapter
        mItemAdapter.updateData(alItems);

        // dismiss progress dialog
        DialogUtils.dismissProgressDialog();
    }

    /**
     * Method is used to update SQLite db
     */
    private void updateSQLiteDb() {
        if (!FrameworkUtils.checkIfNull(mItemProvider) && !FrameworkUtils.checkIfNull(alItemDb)) {
//            mItemProvider.updateAll(alItemDb);


            Logger.e("TEST", "alItemDb  size= " + alItemDb.size());
            for (int i = 0; i < alItemDb.size(); i++) {
                if (i == 0) {
                    Logger.e("TEST", "creating database like a mug");
                    mItemProvider.create(alItemDb.get(0));
                } else {
                    Logger.e("TEST", "updating whole database");
                    mItemProvider.update(alItemDb.get(i));
                }
            }

            List<ItemDatabaseModel> test = new ArrayList<>();
            test = mItemProvider.getAllInfo();
            Logger.v("TEST", "----------------------------------------------------");
            Logger.v("TEST", "DB size= " + test.size());
            for (int i = 0; i < test.size(); i++) {
                Logger.v("TEST", "category= " + test.get(i).category);
                Logger.v("TEST", "asin= " + test.get(i).asin);
                Logger.v("TEST", "label= " + test.get(i).label);
                Logger.v("TEST", "timestamp= " + test.get(i).timestamp);
                Logger.v("TEST", "price= " + test.get(i).price);
                Logger.v("TEST", "salePrice= " + test.get(i).salePrice);
                Logger.v("TEST", "title= " + test.get(i).title);
                Logger.v("TEST", "description= " + test.get(i).description);
                Logger.v("TEST", "purchaseUrl= " + test.get(i).purchaseUrl);
                Logger.v("TEST", "imageUrl1= " + test.get(i).imageUrl1);
                Logger.v("TEST", "imageUrl2= " + test.get(i).imageUrl2);
                Logger.v("TEST", "imageUrl3= " + test.get(i).imageUrl3);
                Logger.v("TEST", "imageUrl4= " + test.get(i).imageUrl4);
                Logger.v("TEST", "imageUrl5= " + test.get(i).imageUrl5);
                Logger.v("TEST", "isBrowseItem= " + test.get(i).isBrowseItem);
                Logger.v("TEST", "isFeatured= " + test.get(i).isFeatured);
                Logger.v("TEST", "isMostPopular= " + test.get(i).isMostPopular);
            }
        }
    }

    /**
     * Method is used to enable/disable drawer
     *
     * @param isEnabled True to enable drawer interaction, otherwise disable interaction
     */
    public void toggleDrawerState(boolean isEnabled) {
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (item.getItemId()) {
            case R.id.nav_browse:

                break;
            case R.id.nav_search:

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

                break;
            case R.id.nav_quick_search_books:

                break;
            case R.id.nav_quick_search_electronics:

                break;
            case R.id.nav_quick_search_food:

                break;
            case R.id.nav_quick_search_health_beauty:

                break;
            case R.id.nav_quick_search_movies:

                break;
            case R.id.nav_quick_search_video_games:

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
     * Method is used to create/populate database on Firebase with empty data
     */
    private void createFirebaseDb() {

        // setup Chablee database
        HashMap<String, ItemModel> mapChablee = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            ItemModel chableeModel = new ItemModel();
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

}