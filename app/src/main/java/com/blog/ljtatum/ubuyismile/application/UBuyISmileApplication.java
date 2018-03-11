package com.blog.ljtatum.ubuyismile.application;

import android.app.Application;

import com.app.framework.utilities.apprater.AppRaterUtil;
import com.app.framework.utilities.firebase.FirebaseUtils;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.google.firebase.FirebaseApp;

/**
 * Created by leonard on 9/22/2017.
 */

public class UBuyISmileApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // instantiate utility classes
        new HappinessUtils(getApplicationContext());
        new AppRaterUtil(getApplicationContext(), getPackageName());

        try {
            // instantiate FireBase
            FirebaseApp.initializeApp(this);
            // instantiate FirebaseUtils
            new FirebaseUtils();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
