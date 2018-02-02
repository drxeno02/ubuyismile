package com.blog.ljtatum.ubuyismile.databases.schema;

import android.provider.BaseColumns;

/**
 * Created by LJTat on 1/27/2018.
 */

public class ItemSchema implements BaseColumns {
    // table name
    public static final String TABLE_NAME = "item_table";

    public static final String CATEGORY = "category";
    public static final String ASIN = "asin";
    public static final String LABEL = "label";
    public static final String TIMESTAMP = "timestamp";
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
    public static final String IS_BROWSABLE = "is_browsable";
    public static final String IS_FEATURED = "is_featured";
    public static final String IS_MOST_POPULAR = "is_most_popular";

    /**
     * Method is used to create a table for user session data
     *
     * @return UserSessionSchema table information
     */
    public static String getCreateTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(");
        sb.append(_ID + " INTEGER PRIMARY KEY, ");
        sb.append(CATEGORY + " TEXT NOT NULL, ");
        sb.append(ASIN + " TEXT NOT NULL, ");
        sb.append(LABEL + " TEXT NOT NULL, ");
        sb.append(TIMESTAMP + " TEXT NOT NULL, ");
        sb.append(PRICE + " TEXT NOT NULL, ");
        sb.append(SALE_PRICE + " TEXT NOT NULL, ");
        sb.append(TITLE + " TEXT NOT NULL, ");
        sb.append(DESCRIPTION + " TEXT NOT NULL, ");
        sb.append(PURCHASE_URL + " TEXT NOT NULL, ");
        sb.append(IMAGE_URL_1 + " TEXT NOT NULL, ");
        sb.append(IMAGE_URL_2 + " TEXT NOT NULL, ");
        sb.append(IMAGE_URL_3 + " TEXT NOT NULL, ");
        sb.append(IMAGE_URL_4 + " TEXT NOT NULL, ");
        sb.append(IMAGE_URL_5 + " TEXT NOT NULL, ");
        sb.append(IS_BROWSABLE + " TEXT NOT NULL, ");
        sb.append(IS_FEATURED + " TEXT NOT NULL, ");
        sb.append(IS_MOST_POPULAR + " TEXT NOT NULL");
        sb.append(");");
        return sb.toString();
    }
}
