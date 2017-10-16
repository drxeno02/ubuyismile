package com.app.framework.requests.isbn;

import com.app.framework.requests.BaseEntityRQ;
import com.google.gson.annotations.SerializedName;

/**
 * Created by leonard on 10/16/2017.
 */

public class LookupIsbnRQ extends BaseEntityRQ {

    @SerializedName("SearchIndex")
    public String searchIndex;
}
