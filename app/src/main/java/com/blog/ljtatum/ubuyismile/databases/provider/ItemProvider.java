package com.blog.ljtatum.ubuyismile.databases.provider;

import android.content.Context;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.databases.DatabaseProvider;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.schema.ItemSchema;

import java.util.List;

/**
 * Created by LJTat on 1/27/2018.
 */

public class ItemProvider {

    private final String[] ARRY_CATEGORIES = {ItemSchema.CATEGORY, ItemSchema.ASIN, ItemSchema.LABEL,
            ItemSchema.TIMESTAMP, ItemSchema.ITEM_ID, ItemSchema.PRICE, ItemSchema.SALE_PRICE,
            ItemSchema.TITLE, ItemSchema.DESCRIPTION, ItemSchema.PURCHASE_URL,
            ItemSchema.IMAGE_URL_1, ItemSchema.IMAGE_URL_2, ItemSchema.IMAGE_URL_3,
            ItemSchema.IMAGE_URL_4, ItemSchema.IMAGE_URL_5, ItemSchema.IS_BROWSABLE,
            ItemSchema.IS_FEATURED, ItemSchema.IS_MOST_POPULAR, ItemSchema.IS_FAVORITE};
    private final DatabaseProvider<ItemDatabaseModel> mProvider;

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     */
    public ItemProvider(Context context) {
        mProvider = DatabaseProvider.getInstance(context);
    }

    /**
     * Method is used to return back all data objects in the db
     *
     * @return All data objects in the db, otherwise null
     */
    public List<ItemDatabaseModel> getAllInfo() {
        try {
            List<ItemDatabaseModel> data = mProvider.getAll(ItemDatabaseModel.class);
            return (!FrameworkUtils.checkIfNull(data) && data.size() > 0) ? data : null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method is used to update item db (used for single update)
     *
     * @param itemDatabaseModel The item to update in db
     */
    public void update(ItemDatabaseModel itemDatabaseModel) {
        mProvider.update(itemDatabaseModel, ItemSchema.ITEM_ID + " = ?",
                new String[]{itemDatabaseModel.itemId});


    }

    /**
     * Method is used to update all items in db
     *
     * @param alItemDb List of items to update in db
     */
    public void insert(List<ItemDatabaseModel> alItemDb) {
        mProvider.insert(alItemDb, ARRY_CATEGORIES);
    }

    /**
     * Method to delete all the account information from the database table
     */
    public void deleteTables() {
        mProvider.deleteAllData(ItemSchema.TABLE_NAME);
    }

    /**
     * Create database with data (used for single update)
     * @param itemDatabaseModel Item to create/instantiate database with
     */
    public void create(ItemDatabaseModel itemDatabaseModel) {
        mProvider.create(itemDatabaseModel);
    }

    /**
     * Method is used to retrieve the number of database entries
     *
     * @return The number of database entries
     */
    public int getCount() {
        return mProvider.getDatabaseRowCount();
    }
}
