package com.blog.ljtatum.ubuyismile.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.dialog.DialogUtils;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;

import java.util.List;

/**
 * Update database items on a separate thread
 *
 * <p>AsyncTask enables proper and easy use of the UI thread. This class allows to perform
 * background operations and publish results on the UI thread without having to manipulate
 * threads and/or handlers.</p>
 */
public class AsyncTaskUpdateDatabase extends AsyncTask<Void, Void, Void> {

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
    public AsyncTaskUpdateDatabase(@NonNull Context context, @NonNull ItemProvider itemProvider,
                                   @NonNull List<ItemDatabaseModel> itemDb) {
        mContext = context;
        mItemProvider = itemProvider;
        alItemDb = itemDb;
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
            for (int i = 0; i < alItemDb.size(); i++) {
                mItemProvider.update(alItemDb.get(i));
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
