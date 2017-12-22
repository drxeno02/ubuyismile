package com.blog.ljtatum.ubuyismile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by leonard on 9/22/2017.
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private final int SPLASH_TIMER = 2000;

    @Override protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // create an intent that will start MainActivity
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // print memory
        Utils.printMemory(TAG);
        // print app info
        Utils.printInfo(this, this);
    }
}
