package com.app.framework.requests.isbn;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonard on 10/16/2017.
 */

public class LookupIsbnRS {

    public class Item {

        @SerializedName("ASIN")
        public String asin;

        @SerializedName("DetailPageURL")
        public String detailPageURL;

        @SerializedName("SalesRank")
        public String salesRank;

        public class ItemAttributes {

            @SerializedName("Author")
            public String author;

            @SerializedName("Binding")
            public String binding;

            @SerializedName("DeweyDecimalNumber")
            public double deweyDecimalNumber;

            @SerializedName("EAN")
            public String ean;

            @SerializedName("Feature")
            public String feature;

            @SerializedName("ISBN")
            public String isbn;

            @SerializedName("Manufacturer")
            public String manufacturer;

            @SerializedName("NumberOfItems")
            public long numberOfItems;

            @SerializedName("NumberOfPages")
            public long numberOfPages;

            @SerializedName("ProductGroup")
            public String productGroup;

            @SerializedName("PublicationDate")
            public String publicationDate;

            @SerializedName("Publisher")
            public String publisher;

            @SerializedName("Studio")
            public String studio;

            @SerializedName("Title")
            public String title;
        }
    }
}
