package com.app.framework.requests.customerreview;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonard on 10/16/2017.
 */

public class RetrieveCustomerReviewsRS {

    public class Item {

        @SerializedName("ASIN")
        public String asin;

        public class CustomReviews {

            @SerializedName("IFrameURL")
            public String iFrameURL;

            @SerializedName("HasReviews")
            public boolean hasReviews;
        }
    }
}
