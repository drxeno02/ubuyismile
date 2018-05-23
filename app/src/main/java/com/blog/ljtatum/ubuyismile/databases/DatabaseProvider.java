package com.blog.ljtatum.ubuyismile.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.databases.schema.ItemSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonard on 8/11/2016.
 */
public class DatabaseProvider<T extends DatabaseModel> extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "data.db";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    private static DatabaseProvider mInstance;
    private SQLiteDatabase mDatabase;

    /**
     * Constructor for DatabaseProvider
     *
     * @param context Interface to global information about an application environment
     */
    private DatabaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (FrameworkUtils.checkIfNull(mDatabase) || !mDatabase.isOpen()) {
            mDatabase = getWritableDatabase();
        }
    }

    /**
     * Method is used to get the instance
     *
     * @param context Interface to global information about an application environment
     * @param <T>     Generics
     * @return An instance of DatabaseProvider
     */
    public synchronized static <T extends DatabaseModel> DatabaseProvider<T> getInstance(Context context) {
        if (FrameworkUtils.checkIfNull(mInstance)) {
            mInstance = new DatabaseProvider<>(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemSchema.getCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXISTS.concat(ItemSchema.TABLE_NAME));
        onCreate(db);
    }

    /**
     * Method is used to create table
     *
     * @param model Generic model
     * @return Variable of type T
     */
    public T create(T model) {
        long rowId = mDatabase.insert(model.getTableName(), null, model.getContentValues());
        String selection = model.getPrimaryKeyName() + " = " + rowId;
        T temp = null;
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                Cursor cursor = mDatabase.query(model.getTableName(),
                        model.getColumns(), selection, null, null, null, null);
                if (!FrameworkUtils.checkIfNull(cursor) && cursor.moveToFirst()) {
                    temp = model.fromCursor(cursor);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
                e.printStackTrace();
            }
        }
        return temp;
    }

    /**
     * Method is used to delete a data item
     *
     * @param tableName   Name of table
     * @param whereClause The optional WHERE clause to apply when deleting. Passing null
     *                    will delete all rows
     * @param whereArgs   You may include ?s in the where clause, which will be replaced by the
     *                    values from whereArgs. The values will be bound as Strings
     * @return 0 if database is not open
     */
    public void delete(String tableName, String whereClause, String[] whereArgs) {
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                mDatabase.delete(tableName, whereClause, whereArgs);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                mDatabase.delete(tableName, null, null);
            }
        }
    }

    /**
     * Method is used to retrieve one data item
     *
     * @param selection A filter declaring which rows to return, formatted as an SQL WHERE
     *                  clause (excluding the WHERE itself). Passing null will return all rows
     *                  for the given table
     * @param cls       Returns a new instance of the class represented by this Class, created by
     *                  invoking the default (that is, zero-argument) constructor
     * @throws InstantiationException Thrown when a program attempts to access a constructor
     *                                which is not accessible from the location where the reference is made
     * @throws IllegalAccessException Thrown when a program attempts to access a field or method
     *                                which is not accessible from the location where the reference is made
     */
    public T getOne(String selection, Class<T> cls) throws InstantiationException, IllegalAccessException {
        List<T> list = get(selection, cls);
        if (FrameworkUtils.checkIfNull(list) || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Method is used to retrieve data
     *
     * @param selection A filter declaring which rows to return, formatted as an SQL WHERE
     *                  clause (excluding the WHERE itself). Passing null will return all rows
     *                  for the given table
     * @param cls       Returns a new instance of the class represented by this Class, created by
     *                  invoking the default (that is, zero-argument) constructor
     * @return rows The queried result set
     * @throws InstantiationException Thrown when a program attempts to access a constructor
     *                                which is not accessible from the location where the reference is made
     * @throws IllegalAccessException Thrown when a program attempts to access a field or method
     *                                which is not accessible from the location where the reference is made
     */
    public List<T> get(String selection, Class<T> cls) throws InstantiationException, IllegalAccessException {
        List<T> rows = new ArrayList<>();
        T model = cls.newInstance();

        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                Cursor cursor = mDatabase.query(model.getTableName(),
                        model.getColumns(), selection, null, null, null, null);
                if (!FrameworkUtils.checkIfNull(cursor)) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        rows.add((T) model.fromCursor(cursor));
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    /**
     * Method is used to retrieve a row of data
     *
     * @param cls Returns a new instance of the class represented by this Class, created by
     *            invoking the default (that is, zero-argument) constructor
     * @return rows The queried result set
     * @throws InstantiationException Thrown when a program attempts to access a constructor
     *                                which is not accessible from the location where the reference is made
     * @throws IllegalAccessException Thrown when a program attempts to access a field or method
     *                                which is not accessible from the location where the reference is made
     */
    public List<T> getAll(Class<T> cls) throws InstantiationException, IllegalAccessException {
        List<T> rows = new ArrayList<>();
        T model = cls.newInstance();

        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                Cursor cursor = mDatabase.query(model.getTableName(),
                        model.getColumns(), null, null, null, null, null);
                if (!FrameworkUtils.checkIfNull(cursor)) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        rows.add((T) model.fromCursor(cursor));
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    /**
     * Method is used to update data on database
     *
     * @param model     The table to update in
     * @param where     The optional WHERE clause to apply when updating. Passing null will
     *                  update all rows
     * @param whereArgs You may include ?s in the where clause, which will be replaced by
     *                  the values from whereArgs. The values will be bound as Strings
     * @return zero if database is not open
     */
    public void update(T model, String where, String[] whereArgs) {
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            mDatabase.update(model.getTableName(), model.getContentValues(), where, whereArgs);
        }
    }

    /**
     * Method is used to insert data on database
     *
     * @param alItemDb List of items to insert into database
     * @param where    The optional WHERE clause to apply when updating. Passing null will
     *                 update all rows
     */
    public void insert(List<ItemDatabaseModel> alItemDb, String[] where) {
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            ContentValues values = new ContentValues();
            for (int i = 0; i < alItemDb.size(); i++) {
                values.put(where[0], alItemDb.get(i).category);
                values.put(where[1], alItemDb.get(i).asin);
                values.put(where[2], alItemDb.get(i).label);
                values.put(where[3], alItemDb.get(i).timestamp);
                values.put(where[4], alItemDb.get(i).itemId);
                values.put(where[5], alItemDb.get(i).price);
                values.put(where[6], alItemDb.get(i).salePrice);
                values.put(where[7], alItemDb.get(i).title);
                values.put(where[8], alItemDb.get(i).description);
                values.put(where[9], alItemDb.get(i).purchaseUrl);
                values.put(where[10], alItemDb.get(i).imageUrl1);
                values.put(where[11], alItemDb.get(i).imageUrl2);
                values.put(where[12], alItemDb.get(i).imageUrl3);
                values.put(where[13], alItemDb.get(i).imageUrl4);
                values.put(where[14], alItemDb.get(i).imageUrl5);
                values.put(where[15], alItemDb.get(i).isBrowseItem);
                values.put(where[16], alItemDb.get(i).isFeatured);
                values.put(where[17], alItemDb.get(i).isMostPopular);
                values.put(where[18], alItemDb.get(i).isFavorite);
                // insert into table
                mDatabase.insert(ItemSchema.TABLE_NAME, null, values);
            }
        }
    }

    /**
     * Method is used to delete all data
     *
     * @param tableName Name of table to delete all data from
     */
    public void deleteAllData(String tableName) {
        mDatabase.execSQL("delete from " + tableName);
    }

    /**
     * Method is used to retrieve the amount of database rows
     *
     * @return The amount of database rows
     */
    public int getDatabaseRowCount() {
        String countQuery = "SELECT  * FROM " + ItemSchema.TABLE_NAME;
        mDatabase = getReadableDatabase();
        if (mDatabase.isOpen()) {
            Cursor cursor = mDatabase.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }
}
