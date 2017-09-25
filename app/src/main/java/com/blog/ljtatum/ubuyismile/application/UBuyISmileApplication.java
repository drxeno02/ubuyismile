package com.blog.ljtatum.ubuyismile.application;

import android.app.Application;

import com.app.framework.utilities.FirebaseUtils;
import com.google.firebase.FirebaseApp;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by LJTat on 9/24/2017.
 */

public class UBuyISmileApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        // instantiate FireBase
        FirebaseApp.initializeApp(this);
        // instantiate FirebaseUtils
        new FirebaseUtils();
    }
}
