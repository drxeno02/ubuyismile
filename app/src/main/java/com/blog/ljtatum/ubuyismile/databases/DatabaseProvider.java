package com.blog.ljtatum.ubuyismile.databases;

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
    public int delete(String tableName, String whereClause, String[] whereArgs) {
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                return mDatabase.delete(tableName, whereClause, whereArgs);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return mDatabase.delete(tableName, null, null);
            }
        }
        return 0;
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
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    rows.add((T) model.fromCursor(cursor));
                    cursor.moveToNext();
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
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    rows.add((T) model.fromCursor(cursor));
                    cursor.moveToNext();
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    /**
     * Method is used to retrieve a row of data with sorting options
     *
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *                (excluding the ORDER BY itself). Passing null will use the default sort
     *                order, which may be unordered
     * @param cls     Returns a new instance of the class represented by this Class, created by
     *                invoking the default (that is, zero-argument) constructor
     * @return rows The queried result set
     * @throws InstantiationException Thrown when a program attempts to access a constructor
     *                                which is not accessible from the location where the reference is made
     * @throws IllegalAccessException Thrown when a program attempts to access a field or method
     *                                which is not accessible from the location where the reference is made
     */
    public List<T> getAll(String orderBy, Class<T> cls) throws InstantiationException, IllegalAccessException {
        List<T> rows = new ArrayList<>();
        T model = cls.newInstance();

        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                Cursor cursor = mDatabase.query(model.getTableName(),
                        model.getColumns(), null, null, null, null, orderBy);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    rows.add((T) model.fromCursor(cursor));
                    cursor.moveToNext();
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rows;
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
     * Method is used to update data on database
     *
     * @param model     The table to update in
     * @param where     The optional WHERE clause to apply when updating. Passing null will
     *                  update all rows
     * @param whereArgs You may include ?s in the where clause, which will be replaced by
     *                  the values from whereArgs. The values will be bound as Strings
     * @return zero if database is not open
     */
    public int update(T model, String where, String[] whereArgs) {
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            return mDatabase.update(model.getTableName(), model.getContentValues(), where, whereArgs);
        }
        return 0;
    }

    /**
     * Method is used to delete all data
     *
     * @param tableName
     */
    public void deleteAllData(String tableName) {
        mDatabase.execSQL("delete from " + tableName);
    }
}
