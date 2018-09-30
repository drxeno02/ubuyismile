package com.blog.ljtatum.ubuyismile.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.amazon.framework.AmazonRequestManager;
import com.app.amazon.framework.enums.Enum;
import com.app.amazon.framework.interfaces.OnAWSRequestListener;
import com.app.amazon.framework.model.ItemId;
import com.app.amazon.framework.utils.AmazonProductAdvertisingApiRequestBuilder;
import com.app.amazon.framework.utils.AmazonWebServiceAuthentication;
import com.app.framework.anim.CustomAnimations;
import com.app.framework.anim.listeners.OnAnimationCompleteListener;
import com.app.framework.listeners.OnFirebaseValueListener;
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.dialog.DialogUtils;
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateItemDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.constants.Durations;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.interfaces.OnDatabaseChangeListener;
import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.blog.ljtatum.ubuyismile.model.AmazonData;
import com.blog.ljtatum.ubuyismile.model.AmazonResponseModel;
import com.blog.ljtatum.ubuyismile.model.Categories;
import com.blog.ljtatum.ubuyismile.model.CreateDatabaseItemModel;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.ErrorUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.fabric.sdk.android.Fabric;

import static com.blog.ljtatum.ubuyismile.saxparse.SAXParseHandler.SAXParse;

/**
 * Created by leonard on 9/22/2017.
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final String ID_PREFIX = "id_";

    // Amazon web service authentication
    private AmazonWebServiceAuthentication mAmazonAuth;

    private ErrorUtils mErrorUtils;
    private ArrayList<String> alAmazonCategories, alChableeCategories;
    private TextView tvProgress;
    private boolean isAmazonFirebaseDataRetrieved, isChableeFirebaseDataRetrieved, isDbEmpty;
    private int categoryIndex;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        // initialize views and listeners
        initializeViews();
        initializeListeners();
        // retrieve firebase data
        retrieveFirebaseData();

        // NOTE: uncomment below to create firebase db. Will also reset all data
//        createFirebaseDb();

        // NOTE: example requests to Amazon done with ISBN, keywords and browse category
        // B00W0TD6Y6 - Poetry in Programming
        // 076243631X - Mammoth Book of Tattoos
//        final String requestUrlA = AmazonProductAdvertisingApiRequestBuilder
//                .forItemLookup("B00W0TD6Y6, 076243631X", ItemId.Type.ISBN)
//                .includeInformationAbout(Enum.ResponseGroupItemLookup.IMAGES)
//                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, mAmazonAuth);
//
//        final String requestUrlB = AmazonProductAdvertisingApiRequestBuilder
//                .forItemSearch("Title, Total, Rest"                                                                                                                                     )
//                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, mAmazonAuth);
//
//        final String requestUrlC = AmazonProductAdvertisingApiRequestBuilder
//                .forItemBrowse(Enum.ItemBrowseNodeId.VIDEO_GAMES)
//                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, mAmazonAuth);
//        new AmazonRequestManager().execute(requestUrlA);
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        mErrorUtils = new ErrorUtils();
        alAmazonCategories = Categories.getAmazonCategories(); // retrieve all Amazon categories
        alChableeCategories = Categories.getChableeCategories(); // retrieve all Chablee categories

        // instantiate SQLite database
        mItemProvider = new ItemProvider(this);
        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();
        isDbEmpty = alItemDb.size() == 0;

        // instantiate views
        tvProgress = findViewById(R.id.tv_progress);

        // instantiate Amazon auth
        mAmazonAuth = AmazonWebServiceAuthentication.create(
                getResources().getString(R.string.amazon_tag),
                getResources().getString(R.string.amazon_access_key),
                getResources().getString(R.string.amazon_secret_key));

        // start loading text animation
        startLoadingTextAnimation();
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {
        // OnAWSRequestListener
        AmazonRequestManager.onAWSRequestListener(new OnAWSRequestListener() {
            @Override
            public void onAWSSuccess(@NonNull String response) {
                // retrieve item_a model
                AmazonResponseModel itemModel = SAXParse(response);
                Log.e(TAG, "made amazon request :: response = " + response);
                Log.e(TAG, "made amazon request :: asin = " + itemModel.asin);
                Log.e(TAG, "made amazon request :: detailPageURL = " + itemModel.detailPageURL);
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
                    if (categoryIndex == 0) {
                        // update loading text animation
                        startLoadingTextAnimation();
                    }

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
                mErrorUtils.showError(SplashActivity.this,
                        getResources().getString(R.string.default_error_message));
            }
        });

        // onDatabaseChange listener
        AsyncTaskUpdateItemDatabase.onDatabaseChangeListener(new OnDatabaseChangeListener() {
            @Override
            public void onDatabaseUpdate() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // go to MainActivity
                        goToActivity(SplashActivity.this, MainActivity.class, null, true, true);
                    }
                });
            }
        });
    }

    /**
     * Method is used to start loading text animation
     */
    private void startLoadingTextAnimation() {
        final String[] arryLoadingAmazonMessages = getResources().getStringArray(R.array.loading_amazon_messages);
        final String[] arryLoadingChableeMessages = getResources().getStringArray(R.array.loading_chablee_messages);

        // set text
        if (!isAmazonFirebaseDataRetrieved) {
            if (tvProgress.getVisibility() != View.VISIBLE) {
                Random rand = new Random();
                tvProgress.setText(arryLoadingAmazonMessages[rand.nextInt(3)]);
                CustomAnimations.fadeAnimation(this, tvProgress, R.anim.fade_in_500, true, false);
            }
        } else if (!isChableeFirebaseDataRetrieved) {
            CustomAnimations.fadeAnimation(this, tvProgress, R.anim.fade_out_200, false, true);
            CustomAnimations.onAnimationCompleteListener(new OnAnimationCompleteListener() {
                @Override
                public void onAnimationComplete() {
                    Random rand = new Random();
                    tvProgress.setText(arryLoadingChableeMessages[rand.nextInt(3)]);
                    CustomAnimations.fadeAnimation(SplashActivity.this, tvProgress, R.anim.fade_in_500, true, false);
                }
            });
        }
    }

    /**
     * Method is used to create SQLite db
     */
    private void createSQLiteDb() {
        if (!FrameworkUtils.checkIfNull(mItemProvider) && !FrameworkUtils.checkIfNull(alItemDb)) {
            mItemProvider.create(alItemDb);
        }
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
                        itemDatabaseModel.timestampSearch = "";
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
                        itemDatabaseModel.imageUrl6 = "";
                        itemDatabaseModel.isBrowseItem = itemModel.isBrowseItem;
                        itemDatabaseModel.isFeatured = false;
                        itemDatabaseModel.isMostPopular = false;
                        itemDatabaseModel.isFavorite = false;
                        itemDatabaseModel.isLabelSet = false;
                        itemDatabaseModel.isSearch = false;
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
                    chableeModel.isFavorite = false;

                    // only add data if it exists. This is enforced by the required title
                    if (!FrameworkUtils.isStringEmpty(chableeModel.title)) {
                        // update database values for existing items or add new item to database
                        boolean isItemExisting = false;
                        int index = 0;
                        for (int i = 0; i < alItemDb.size(); i++) {
                            if (!FrameworkUtils.isStringEmpty(alItemDb.get(i).itemId) &&
                                    !FrameworkUtils.isStringEmpty(chableeModel.itemId) &&
                                    alItemDb.get(i).itemId.equalsIgnoreCase(chableeModel.itemId) &&
                                    alItemDb.get(i).category.equalsIgnoreCase(chableeModel.category)) {
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
                            alItemDb.get(index).imageUrl6 = chableeModel.imageUrl6;
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
                            itemDatabaseModel.imageUrl6 = chableeModel.imageUrl6;
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
        } else {
            // make Amazon requests
            AmazonData.getAmazonASINRequest(AmazonData.getBooks());
        }
    }

    /**
     * Method is used to retrieve Firebase data
     */
    private void retrieveFirebaseData() {
        if (!isAmazonFirebaseDataRetrieved) {
            if (categoryIndex == 0) {
                // show progress dialog
                DialogUtils.showProgressDialog(this);
            }

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

                // dismiss progress dialog
                DialogUtils.dismissProgressDialog();

                if (isDbEmpty) {
                    createSQLiteDb();
                } else {
                    // update database
                    new AsyncTaskUpdateItemDatabase(this, mItemProvider, alItemDb, null).execute();
                }
                // print database
                printDb();
            }
        }
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
            Logger.v(TAG, "timestampSearch= " + alItemDb.get(i).timestampSearch);
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
            Logger.v(TAG, "imageUrl6= " + alItemDb.get(i).imageUrl6);
            Logger.v(TAG, "isBrowseItem= " + alItemDb.get(i).isBrowseItem);
            Logger.v(TAG, "isFeatured= " + alItemDb.get(i).isFeatured);
            Logger.v(TAG, "isMostPopular= " + alItemDb.get(i).isMostPopular);
            Logger.v(TAG, "isFavorite= " + alItemDb.get(i).isFavorite);
            Logger.v(TAG, "isLabelSet= " + alItemDb.get(i).isLabelSet);
            Logger.v(TAG, "isSearch= " + alItemDb.get(i).isSearch);
        }
    }

    /**
     * Method is used to create/populate database on Firebase with empty data
     */
    private void createFirebaseDb() {
        // categories (AMAZON)
        final String[] arryCategoriesAmazon = {Enum.ItemCategory.DEALS.toString(), Enum.ItemCategory.APPAREL.toString(),
                Enum.ItemCategory.APPLIANCES.toString(), Enum.ItemCategory.AUTOMOTIVE.toString(),
                Enum.ItemCategory.BABY.toString(), Enum.ItemCategory.BEAUTY.toString(),
                Enum.ItemCategory.BOOKS.toString(), Enum.ItemCategory.DVD.toString(),
                Enum.ItemCategory.ELECTRONICS.toString(), Enum.ItemCategory.GROCERY.toString(),
                Enum.ItemCategory.HEALTH_AND_PERSONAL_CARE.toString(), Enum.ItemCategory.HOME_AND_GARDEN.toString(),
                Enum.ItemCategory.JEWELRY.toString(), Enum.ItemCategory.KINDLE_STORE.toString(),
                Enum.ItemCategory.LAWN_AND_GARDEN.toString(), Enum.ItemCategory.LUGGAGE_AND_BAGS.toString(),
                Enum.ItemCategory.LUXURY_BEAUTY.toString(), Enum.ItemCategory.MUSIC.toString(),
                Enum.ItemCategory.MUSICAL_INSTRUMENTS.toString(), Enum.ItemCategory.OFFICE_PRODUCTS.toString(),
                Enum.ItemCategory.AMAZON_PANTRY.toString(), Enum.ItemCategory.PC_HARDWARE.toString(),
                Enum.ItemCategory.PET_SUPPLIES.toString(), Enum.ItemCategory.SHOES.toString(),
                Enum.ItemCategory.SOFTWARE.toString(), Enum.ItemCategory.SPORTING_GOODS.toString(),
                Enum.ItemCategory.TOYS.toString(), Enum.ItemCategory.VIDEO_GAMES.toString(),
                Enum.ItemCategory.WATCHES.toString()};
        // categories (CHABLEE)
        final String[] arryCategoriesChablee = {Enum.ItemCategoryChablee.CROWNS.toString(),
                Enum.ItemCategoryChablee.RINGS.toString(), Enum.ItemCategoryChablee.NECKLACES.toString(),
                Enum.ItemCategoryChablee.GEMSTONE.toString(), Enum.ItemCategoryChablee.ROCKS.toString()};

        // id value
        int id = -1;

        // setup Amazon database
        for (int i = 0; i < arryCategoriesAmazon.length; i++) {
            // setup Amazon database
            HashMap<String, String> mapAmazon = new HashMap<>();
            for (int n = 0; n < 75; n++) {
                mapAmazon.put(String.valueOf(n), " ");
            }
            FirebaseUtils.addValues(new ArrayList<>(mapAmazon.values()), arryCategoriesAmazon[i]);
        }
        for (int i = 0; i < arryCategoriesChablee.length; i++) {
            // setup Chablee database
            HashMap<String, CreateDatabaseItemModel> mapChablee = new HashMap<>();
            for (int n = 0; n < 60; n++) {
                id++;
                CreateDatabaseItemModel chableeModel = new CreateDatabaseItemModel();
                chableeModel.itemId = ID_PREFIX.concat(String.valueOf(id));
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
                chableeModel.imageUrl6 = " ";
                chableeModel.isMostPopular = false;
                // add to hashmap
                mapChablee.put(String.valueOf(id), chableeModel);
            }
            FirebaseUtils.addValuesChab(new ArrayList<>(mapChablee.values()), arryCategoriesChablee[i]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // print memory
        Utils.printMemory(TAG);
        // print app info
        Utils.printInfo(this, this);
    }
}
