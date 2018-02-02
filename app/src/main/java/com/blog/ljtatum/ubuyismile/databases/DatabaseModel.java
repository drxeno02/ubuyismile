package com.blog.ljtatum.ubuyismile.databases;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by anthony on 5/12/15.
 */
interface DatabaseModel {
    <T extends DatabaseModel> T fromCursor(Cursor cursor);

    /**
     * Used to get the columns
     *
     * @return Returns columns
     */
    String[] getColumns();

    /**
     * Used to get the information present in the tables
     *
     * @return Return content values
     */
    ContentValues getContentValues();

    /**
     * Get the name of primary Key
     *
     * @return Returns primary key
     */
    String getPrimaryKeyName();

    /**
     * Method to get the table name
     *
     * @return Returns table name
     */
    String getTableName();

    /**
     * Method returns String value
     *
     * @return Returns String value
     */
    String toString();
}
