package com.blog.ljtatum.ubuyismile.activity;

import android.os.Bundle;

import com.app.amazon.framework.utils.AmazonWebServiceAuthentication;
import com.blog.ljtatum.ubuyismile.R;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AmazonWebServiceAuthentication authentication = AmazonWebServiceAuthentication.create(
                getResources().getString(R.string.amazon_tag),
                getResources().getString(R.string.amazon_access_key),
                getResources().getString(R.string.amazon_secret_key));

    }

}
