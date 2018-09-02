package com.blog.ljtatum.ubuyismile.databases.schema;

import android.provider.BaseColumns;

/**
 * Created by LJTat on 1/27/2018.
 */

public class ItemSchema implements BaseColumns {
    // table name
    public static final String TABLE_NAME = "item_table";

    // database keys
    public static final String CATEGORY = "category";
    public static final String ASIN = "asin";
    public static final String LABEL = "label";
    public static final String TIMESTAMP = "timestamp";
    public static final String TIMESTAMP_SEARCH = "timestamp_search";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_TYPE = "item_type";
    public static final String PRICE = "price";
    public static final String SALE_PRICE = "sale_price";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PURCHASE_URL = "purchase_url";
    public static final String IMAGE_URL_1 = "image_url_one";
    public static final String IMAGE_URL_2 = "image_url_two";
    public static final String IMAGE_URL_3 = "image_url_three";
    public static final String IMAGE_URL_4 = "image_url_four";
    public static final String IMAGE_URL_5 = "image_url_five";
    public static final String IMAGE_URL_6 = "image_url_six";
    public static final String IS_LABEL_SET = "is_label_set";
    public static final String IS_BROWSABLE = "is_browsable";
    public static final String IS_FEATURED = "is_featured";
    public static final String IS_MOST_POPULAR = "is_most_popular";
    public static final String IS_FAVORITE = "is_favorite";
    public static final String IS_SEARCH = "is_search";

    /**
     * Method is used to create a table for user session data
     *
     * @return UserSessionSchema table information
     */
    public static String getCreateTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(");
        sb.append(_ID + " INTEGER PRIMARY KEY, ");
        sb.append(CATEGORY + " TEXT, ");
        sb.append(ASIN + " TEXT, ");
        sb.append(LABEL + " TEXT, ");
        sb.append(TIMESTAMP + " TEXT, ");
        sb.append(TIMESTAMP_SEARCH + " TEXT, ");
        sb.append(ITEM_ID + " TEXT, ");
        sb.append(ITEM_TYPE + " TEXT, ");
        sb.append(PRICE + " TEXT, ");
        sb.append(SALE_PRICE + " TEXT, ");
        sb.append(TITLE + " TEXT, ");
        sb.append(DESCRIPTION + " TEXT, ");
        sb.append(PURCHASE_URL + " TEXT, ");
        sb.append(IMAGE_URL_1 + " TEXT, ");
        sb.append(IMAGE_URL_2 + " TEXT, ");
        sb.append(IMAGE_URL_3 + " TEXT, ");
        sb.append(IMAGE_URL_4 + " TEXT, ");
        sb.append(IMAGE_URL_5 + " TEXT, ");
        sb.append(IMAGE_URL_6 + " TEXT, ");
        sb.append(IS_LABEL_SET + " TEXT, ");
        sb.append(IS_BROWSABLE + " TEXT, ");
        sb.append(IS_FEATURED + " TEXT, ");
        sb.append(IS_MOST_POPULAR + " TEXT, ");
        sb.append(IS_FAVORITE + " TEXT, ");
        sb.append(IS_SEARCH + " TEXT");
        sb.append(");");
        return sb.toString();
    }
}
