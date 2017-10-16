package com.app.framework.requests.findrelated;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonard on 10/16/2017.
 */

public class FindRelatedRS {

    public class Item {

        @SerializedName("ASIN")
        public String asin;

        @SerializedName("DetailPageURL")
        public String detailPageURL;

        public class ItemAttributes {

            @SerializedName("Manufacturer")
            public String manufacturer;

            @SerializedName("ProductGroup")
            public boolean productGroup;

            @SerializedName("Title")
            public boolean title;
        }

    }
}
