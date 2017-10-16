package com.app.framework.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonard on 10/16/2017.
 */

public class BaseEntityRQ {

    @SerializedName("Service")
    public String service;

    @SerializedName("Operation")
    public String operation;

    @SerializedName("ResponseGroup")
    public String responseGroup;

    @SerializedName("IdType")
    public String idType;

    @SerializedName("ItemId")
    public String itemId;

    @SerializedName("AssociateTag")
    public String associateTag;

    @SerializedName("AWSAccessKeyId")
    public String awsAccessKeyId;

    @SerializedName("Timestamp")
    public String timestamp;

    @SerializedName("Signature")
    public String signature;
}
