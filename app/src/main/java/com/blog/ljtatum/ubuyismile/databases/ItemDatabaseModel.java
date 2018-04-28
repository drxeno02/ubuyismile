package com.blog.ljtatum.ubuyismile.databases;

import android.content.ContentValues;
import android.database.Cursor;

import com.blog.ljtatum.ubuyismile.databases.schema.ItemSchema;

/**
 * Created by LJTat on 1/27/2018.
 */

public class ItemDatabaseModel implements DatabaseModel {

    // base values
    public String category, asin, label, timestamp, itemId;
    public boolean isBrowseItem;

    // Chablee specific values
    public String price, salePrice, title, description, purchaseUrl, imageUrl1, imageUrl2,
            imageUrl3, imageUrl4, imageUrl5;
    public boolean isFeatured, isMostPopular, isFavorite;

    @Override
    public <T extends DatabaseModel> T fromCursor(Cursor cursor) {
        ItemDatabaseModel item = new ItemDatabaseModel();
        item.category = cursor.getString(cursor.getColumnIndex(ItemSchema.CATEGORY));
        item.asin = cursor.getString(cursor.getColumnIndex(ItemSchema.ASIN));
        item.label = cursor.getString(cursor.getColumnIndex(ItemSchema.LABEL));
        item.timestamp = cursor.getString(cursor.getColumnIndex(ItemSchema.TIMESTAMP));
        item.itemId = cursor.getString(cursor.getColumnIndex(ItemSchema.ITEM_ID));
        item.price = cursor.getString(cursor.getColumnIndex(ItemSchema.PRICE));
        item.salePrice = cursor.getString(cursor.getColumnIndex(ItemSchema.SALE_PRICE));
        item.title = cursor.getString(cursor.getColumnIndex(ItemSchema.TITLE));
        item.description = cursor.getString(cursor.getColumnIndex(ItemSchema.DESCRIPTION));
        item.purchaseUrl = cursor.getString(cursor.getColumnIndex(ItemSchema.PURCHASE_URL));
        item.imageUrl1 = cursor.getString(cursor.getColumnIndex(ItemSchema.IMAGE_URL_1));
        item.imageUrl2 = cursor.getString(cursor.getColumnIndex(ItemSchema.IMAGE_URL_2));
        item.imageUrl3 = cursor.getString(cursor.getColumnIndex(ItemSchema.IMAGE_URL_3));
        item.imageUrl4 = cursor.getString(cursor.getColumnIndex(ItemSchema.IMAGE_URL_4));
        item.imageUrl5 = cursor.getString(cursor.getColumnIndex(ItemSchema.IMAGE_URL_5));
        item.isBrowseItem = cursor.getInt(cursor.getColumnIndex(ItemSchema.IS_BROWSABLE)) > 0;
        item.isFeatured = cursor.getInt(cursor.getColumnIndex(ItemSchema.IS_FEATURED)) > 0;
        item.isMostPopular = cursor.getInt(cursor.getColumnIndex(ItemSchema.IS_MOST_POPULAR)) > 0;
        item.isFavorite = cursor.getInt(cursor.getColumnIndex(ItemSchema.IS_FAVORITE)) > 0;
        return (T) item;
    }

    @Override
    public String[] getColumns() {
        return new String[] {
                ItemSchema.CATEGORY,
                ItemSchema.ASIN,
                ItemSchema.LABEL,
                ItemSchema.TIMESTAMP,
                ItemSchema.ITEM_ID,
                ItemSchema.PRICE,
                ItemSchema.SALE_PRICE,
                ItemSchema.TITLE,
                ItemSchema.DESCRIPTION,
                ItemSchema.PURCHASE_URL,
                ItemSchema.IMAGE_URL_1,
                ItemSchema.IMAGE_URL_2,
                ItemSchema.IMAGE_URL_3,
                ItemSchema.IMAGE_URL_4,
                ItemSchema.IMAGE_URL_5,
                ItemSchema.IS_BROWSABLE,
                ItemSchema.IS_FEATURED,
                ItemSchema.IS_MOST_POPULAR,
                ItemSchema.IS_FAVORITE
        };
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(ItemSchema.CATEGORY, category);
        values.put(ItemSchema.ASIN, asin);
        values.put(ItemSchema.LABEL, label);
        values.put(ItemSchema.TIMESTAMP, timestamp);
        values.put(ItemSchema.ITEM_ID, itemId);
        values.put(ItemSchema.PRICE, price);
        values.put(ItemSchema.SALE_PRICE, salePrice);
        values.put(ItemSchema.TITLE, title);
        values.put(ItemSchema.DESCRIPTION, description);
        values.put(ItemSchema.PURCHASE_URL, purchaseUrl);
        values.put(ItemSchema.IMAGE_URL_1, imageUrl1);
        values.put(ItemSchema.IMAGE_URL_2, imageUrl2);
        values.put(ItemSchema.IMAGE_URL_3, imageUrl3);
        values.put(ItemSchema.IMAGE_URL_4, imageUrl4);
        values.put(ItemSchema.IMAGE_URL_5, imageUrl5);
        values.put(ItemSchema.IS_BROWSABLE, isBrowseItem);
        values.put(ItemSchema.IS_FEATURED, isFeatured);
        values.put(ItemSchema.IS_MOST_POPULAR, isMostPopular);
        values.put(ItemSchema.IS_FAVORITE, isFavorite);
        return values;
    }

    @Override
    public String getPrimaryKeyName() {
        return ItemSchema._ID;
    }

    @Override
    public String getTableName() {
        return ItemSchema.TABLE_NAME;
    }
}
