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
public class AsyncTaskUpdateItemDatabase extends AsyncTask<Void, Void, Void> {

    // custom callback
    private static OnDatabaseChangeListener mOnDatabaseChangeListener;
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    /**
     * Constructor
     *
     * @param context      Interface to global information about an application environment
     * @param itemProvider Provider to update database
     * @param itemDb       List of items to update in database
     */
    public AsyncTaskUpdateItemDatabase(@NonNull Context context, @NonNull ItemProvider itemProvider,
                                       @NonNull List<ItemDatabaseModel> itemDb) {
        mContext = context;
        mItemProvider = itemProvider;
        alItemDb = itemDb;
    }

    /**
     * Method is used to set callback for when database is updated
     *
     * @param listener Callback for when database is updated
     */
    public static void onDatabaseChangeListener(OnDatabaseChangeListener listener) {
        mOnDatabaseChangeListener = listener;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // hide progress dialog
        DialogUtils.dismissProgressDialog();
    }


    @Override
    protected Void doInBackground(Void... params) {
        if (!FrameworkUtils.checkIfNull(mItemProvider) && !FrameworkUtils.checkIfNull(alItemDb)) {
            // update database
            mItemProvider.update(alItemDb);
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
        // show progress dialog
        DialogUtils.showProgressDialog(mContext);
    }
}
