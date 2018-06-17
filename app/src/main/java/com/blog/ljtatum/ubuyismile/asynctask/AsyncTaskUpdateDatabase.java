package com.blog.ljtatum.ubuyismile.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.dialog.DialogUtils;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.interfaces.OnDatabaseChangeListener;

import java.util.List;

/**
 * Update database items on a separate thread
 * <p>
 * <p>AsyncTask enables proper and easy use of the UI thread. This class allows to perform
 * background operations and publish results on the UI thread without having to manipulate
 * threads and/or handlers.</p>
 */
public class AsyncTaskUpdateDatabase extends AsyncTask<Void, Void, Void> {

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    // custom callback
    private static OnDatabaseChangeListener mOnDatabaseChangeListener;

    /**
     * Method is used to set callback for when database is updated
     *
     * @param listener Callback for when database is updated
     */
    public static void onDatabaseChangeListener(OnDatabaseChangeListener listener) {
        mOnDatabaseChangeListener = listener;
    }

    /**
     * Constructor
     *
     * @param itemProvider Provider to update database
     * @param itemDb       List of items to update in database
     */
    public AsyncTaskUpdateDatabase(@NonNull ItemProvider itemProvider, @NonNull List<ItemDatabaseModel> itemDb) {
        mItemProvider = itemProvider;
        alItemDb = itemDb;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }


    @Override
    protected Void doInBackground(Void... params) {
        if (!FrameworkUtils.checkIfNull(mItemProvider) && !FrameworkUtils.checkIfNull(alItemDb)) {
            for (int i = 0; i < alItemDb.size(); i++) {
                mItemProvider.update(alItemDb.get(i));
            }
            if (!FrameworkUtils.checkIfNull(mOnDatabaseChangeListener)) {
                // set listener
                mOnDatabaseChangeListener.onDatabaseUpdate();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
