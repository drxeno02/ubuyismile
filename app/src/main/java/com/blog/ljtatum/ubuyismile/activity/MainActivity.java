package com.blog.ljtatum.ubuyismile.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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
import com.blog.ljtatum.ubuyismile.fragments.AboutFragment;
import com.blog.ljtatum.ubuyismile.fragments.PrivacyFragment;
import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.blog.ljtatum.ubuyismile.model.AmazonData;
import com.blog.ljtatum.ubuyismile.model.AmazonModel;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ChableeModel;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

import static com.blog.ljtatum.ubuyismile.saxparse.SAXParseHandler.SAXParse;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ArrayList<String> alAmazonCategories, alChableeCategories;
    private int categoryIndex = 0; // default
    private boolean isAmazonFirebaseDataRetrieved, isChableeFirebaseDataRetrieved;

    // amazon web service authentication
    AmazonWebServiceAuthentication mAmazonAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        // initialize views and listeners
        initializeViews();
        initializeHandlers();
        initializeListeners();
        retrieveFirebaseData();

        // uncomment below to create firebase db
        //createFirebaseDb();



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
        alAmazonCategories = AmazonData.getAmazonCategories(); // retrieve all Amazon categories
        alChableeCategories = ChableeData.getChableeCategories(); // retrieve all Chablee categories

        // instantiate amazon auth
        mAmazonAuth = AmazonWebServiceAuthentication.create(
                getResources().getString(R.string.amazon_tag),
                getResources().getString(R.string.amazon_access_key),
                getResources().getString(R.string.amazon_secret_key));

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
                // retrieve item model
                ItemModel itemModel = SAXParse(response);
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
                Logger.v("TEST", "dataSnapshot= " + dataSnapshot);

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
                    if (categoryIndex < alAmazonCategories.size()) {
                        // increase category index
                        categoryIndex++;
                        retrieveFirebaseData();
                    }
                }
            }

            @Override
            public void onRetrieveDataError(DatabaseError databaseError) {

            }
        });
    }

    private void populateDataLists(DataSnapshot dataSnapshot) {
        if (!isAmazonFirebaseDataRetrieved) {

            HashMap<String, AmazonModel> map = new HashMap<>();

            if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.DEALS.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.APPAREL.toString())) {


            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.APPLIANCES.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.AUTOMOTIVE.toString())) {


            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.BABY.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.BEAUTY.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.BOOKS.toString())) {
                    ArrayList<AmazonModel> alBooks = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!FrameworkUtils.checkIfNull(snapshot.getValue()) &&
                                !FrameworkUtils.isStringEmpty(snapshot.getValue().toString())) {
                            AmazonModel amazonModel = new AmazonModel();
                            amazonModel.category = alAmazonCategories.get(categoryIndex);
                            amazonModel.asin = snapshot.getValue().toString();
                            alBooks.add(amazonModel);
                        }
                    }
                    // set book list
                    AmazonData.setBooks(alBooks);
            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.DVD.toString())) {


            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.ELECTRONICS.toString())) {


            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.GROCERY.toString())) {


            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.HEALTH_AND_PERSONAL_CARE.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.HOME_AND_GARDEN.toString())) {


            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.JEWELRY.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.KINDLE_STORE.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.LAWN_AND_GARDEN.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.LUGGAGE_AND_BAGS.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.LUXURY_BEAUTY.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.MUSIC.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.MUSICAL_INSTRUMENTS.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.OFFICE_PRODUCTS.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.AMAZON_PANTRY.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.PC_HARDWARE.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.PET_SUPPLIES.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.SHOES.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.SOFTWARE.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.SPORTING_GOODS.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.TOYS.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.VIDEO_GAMES.toString())) {

            } else if (categoryIndex < alAmazonCategories.size() &&
                    alAmazonCategories.get(categoryIndex).equalsIgnoreCase(Enum.ItemCategory.WATCHES.toString())) {

            }


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

        // retrieve more data from amazon so long that category index is less than
        // amazon category list size otherwise retrieve Chablee data
        if (!isAmazonFirebaseDataRetrieved) {
            if (categoryIndex < alAmazonCategories.size()) {
                // retrieve data (AMAZON)
                FirebaseUtils.retrieveItemsAmazon(alAmazonCategories.get(categoryIndex));
            } else {
                // reset
                categoryIndex = 0;
                // amazon queries completed
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
                // amazon queries completed
                isChableeFirebaseDataRetrieved = true;

                if (isAmazonFirebaseDataRetrieved && isChableeFirebaseDataRetrieved) {
                    // dismiss progress dialog
                    DialogUtils.dismissProgressDialog();
                }
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
        switch (item.getItemId()) {
            case R.id.nav_browse:

                break;
            case R.id.nav_search:

                break;
            case R.id.nav_settings:

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
        HashMap<String, ChableeModel> mapChablee = new HashMap<>();
        for (int i = 0; i < 250; i++) {
            ChableeModel chableeModel = new ChableeModel();
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
            // add to hashmap
            mapChablee.put(String.valueOf(i), chableeModel);
        }
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.RINGS.toString());
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.NECKLACES.toString());
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.GEMSTONE.toString());
        FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), Enum.ItemCategoryChablee.ROCKS.toString());

        // setup Amazon database
        HashMap<String, String> mapAmazon = new HashMap<>();
        for (int i = 0; i < 500; i++) {
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