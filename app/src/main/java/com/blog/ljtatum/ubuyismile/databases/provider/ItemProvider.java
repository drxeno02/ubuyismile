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
     * Method is used to return back the first data object in the db
     *
     * @return First data object in the db, otherwise null
     */
    public ItemDatabaseModel getInfo() {
        try {
            List<ItemDatabaseModel> data = mProvider.getAll(ItemDatabaseModel.class);
            return (!FrameworkUtils.checkIfNull(data) && data.size() > 0) ? data.get(0) : null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            ;
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
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
            ;
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method is used to update item db
     *
     * @param itemDatabaseModel The item to update in db
     */
    public void update(ItemDatabaseModel itemDatabaseModel) {
        mProvider.update(itemDatabaseModel, ItemSchema.CATEGORY + " = ?", new String[]{itemDatabaseModel.category});
        mProvider.update(itemDatabaseModel, ItemSchema.ASIN + " = ?", new String[]{itemDatabaseModel.asin});
        mProvider.update(itemDatabaseModel, ItemSchema.LABEL + " = ?", new String[]{itemDatabaseModel.label});
        mProvider.update(itemDatabaseModel, ItemSchema.TIMESTAMP + " = ?", new String[]{itemDatabaseModel.timestamp});
        mProvider.update(itemDatabaseModel, ItemSchema.PRICE + " = ?", new String[]{itemDatabaseModel.price});
        mProvider.update(itemDatabaseModel, ItemSchema.SALE_PRICE + " = ?", new String[]{itemDatabaseModel.salePrice});
        mProvider.update(itemDatabaseModel, ItemSchema.TITLE + " = ?", new String[]{itemDatabaseModel.title});
        mProvider.update(itemDatabaseModel, ItemSchema.DESCRIPTION + " = ?", new String[]{itemDatabaseModel.description});
        mProvider.update(itemDatabaseModel, ItemSchema.PURCHASE_URL + " = ?", new String[]{itemDatabaseModel.purchaseUrl});
        mProvider.update(itemDatabaseModel, ItemSchema.IMAGE_URL_1 + " = ?", new String[]{itemDatabaseModel.imageUrl1});
        mProvider.update(itemDatabaseModel, ItemSchema.IMAGE_URL_2 + " = ?", new String[]{itemDatabaseModel.imageUrl2});
        mProvider.update(itemDatabaseModel, ItemSchema.IMAGE_URL_3 + " = ?", new String[]{itemDatabaseModel.imageUrl3});
        mProvider.update(itemDatabaseModel, ItemSchema.IMAGE_URL_4 + " = ?", new String[]{itemDatabaseModel.imageUrl4});
        mProvider.update(itemDatabaseModel, ItemSchema.IMAGE_URL_5 + " = ?", new String[]{itemDatabaseModel.imageUrl5});
        mProvider.update(itemDatabaseModel, ItemSchema.IS_BROWSABLE + " = ?", new String[]{String.valueOf(itemDatabaseModel.isBrowseItem)});
        mProvider.update(itemDatabaseModel, ItemSchema.IS_FEATURED + " = ?", new String[]{String.valueOf(itemDatabaseModel.isFeatured)});
        mProvider.update(itemDatabaseModel, ItemSchema.IS_MOST_POPULAR + " = ?", new String[]{String.valueOf(itemDatabaseModel.isMostPopular)});
    }

    /**
     * Method is used to update all items in db
     *
     * @param alItemDb List of items to update in db
     */
    public void updateAll(List<ItemDatabaseModel> alItemDb) {
        for (int i = 0; i < alItemDb.size(); i++) {
            mProvider.update(alItemDb.get(i), ItemSchema.CATEGORY + " = ?", new String[]{alItemDb.get(i).category});
            mProvider.update(alItemDb.get(i), ItemSchema.ASIN + " = ?", new String[]{alItemDb.get(i).asin});
            mProvider.update(alItemDb.get(i), ItemSchema.LABEL + " = ?", new String[]{alItemDb.get(i).label});
            mProvider.update(alItemDb.get(i), ItemSchema.TIMESTAMP + " = ?", new String[]{alItemDb.get(i).timestamp});
            mProvider.update(alItemDb.get(i), ItemSchema.PRICE + " = ?", new String[]{alItemDb.get(i).price});
            mProvider.update(alItemDb.get(i), ItemSchema.SALE_PRICE + " = ?", new String[]{alItemDb.get(i).salePrice});
            mProvider.update(alItemDb.get(i), ItemSchema.TITLE + " = ?", new String[]{alItemDb.get(i).title});
            mProvider.update(alItemDb.get(i), ItemSchema.DESCRIPTION + " = ?", new String[]{alItemDb.get(i).description});
            mProvider.update(alItemDb.get(i), ItemSchema.PURCHASE_URL + " = ?", new String[]{alItemDb.get(i).purchaseUrl});
            mProvider.update(alItemDb.get(i), ItemSchema.IMAGE_URL_1 + " = ?", new String[]{alItemDb.get(i).imageUrl1});
            mProvider.update(alItemDb.get(i), ItemSchema.IMAGE_URL_2 + " = ?", new String[]{alItemDb.get(i).imageUrl2});
            mProvider.update(alItemDb.get(i), ItemSchema.IMAGE_URL_3 + " = ?", new String[]{alItemDb.get(i).imageUrl3});
            mProvider.update(alItemDb.get(i), ItemSchema.IMAGE_URL_4 + " = ?", new String[]{alItemDb.get(i).imageUrl4});
            mProvider.update(alItemDb.get(i), ItemSchema.IMAGE_URL_5 + " = ?", new String[]{alItemDb.get(i).imageUrl5});
            mProvider.update(alItemDb.get(i), ItemSchema.IS_BROWSABLE + " = ?", new String[]{String.valueOf(alItemDb.get(i).isBrowseItem)});
            mProvider.update(alItemDb.get(i), ItemSchema.IS_FEATURED + " = ?", new String[]{String.valueOf(alItemDb.get(i).isFeatured)});
            mProvider.update(alItemDb.get(i), ItemSchema.IS_MOST_POPULAR + " = ?", new String[]{String.valueOf(alItemDb.get(i).isMostPopular)});
        }
    }

    /**
     * Method to delete all the account information from the database table
     */
    public void deleteTables() {
        mProvider.deleteAllData(ItemSchema.TABLE_NAME);
    }

    /**
     * Store user session information in SQLite database when registering a user
     */
    public void create(ItemDatabaseModel session) {
        mProvider.create(session);
    }
}
