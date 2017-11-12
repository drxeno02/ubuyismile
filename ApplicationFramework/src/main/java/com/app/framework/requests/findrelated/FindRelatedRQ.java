package com.app.framework.requests.findrelated;

import com.app.framework.requests.BaseEntityRQ;
import com.google.gson.annotations.SerializedName;

/**
 * Created by leonard on 10/16/2017.
 */

public class FindRelatedRQ extends BaseEntityRQ {

    @SerializedName("RelationshipType")
    public String relationshipType;
}
