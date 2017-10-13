package com.blog.ljtatum.ubuyismile.activity;

import android.os.Bundle;
import android.util.Log;

import com.app.amazon.framework.enums.Enum;
import com.app.amazon.framework.model.ItemId;
import com.app.amazon.framework.utils.AmazonProductAdvertisingApiRequestBuilder;
import com.app.amazon.framework.utils.AmazonWebServiceAuthentication;
import com.blog.ljtatum.ubuyismile.R;


public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AmazonWebServiceAuthentication myAuthentication = AmazonWebServiceAuthentication.create("drxeno02-20", "AKIAJVWJJT4YYSPVVDWQ", "s/l4sdka9svv6difLhgYEnBQQRdNofgSVG9TSwWI");
        Log.e("TEST", "associate tag= " + myAuthentication.getAssociateTag());
        Log.e("TEST", "access key= " + myAuthentication.getAwsAccessKey());
        Log.e("TEST", "secret key= " + myAuthentication.getAwsSecretKey());

        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder
                .forItemLookup("Poetry in Programming: Building Applications with the Android SDK", ItemId.Type.ISBN)
                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, myAuthentication);

        Log.e("TEST", "request url= " + requestUrl);

    }
}
