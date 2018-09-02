package com.blog.ljtatum.ubuyismile.model;

/**
 * Created by LJTat on 1/25/2018.
 * Class is used only for creating the attributes on Firebase. For simplicity reasons,
 * the attributes added to Firebase is a limited subset of what the app tracks. The rest
 * of the attributes are all tracked in local database
 */

public class CreateDatabaseItemModel {
    // base values
    public String asin, label, timestamp, itemId, itemType;

    // Chablee specific values
    public String price, salePrice, title, description, purchaseUrl, imageUrl1, imageUrl2,
            imageUrl3, imageUrl4, imageUrl5, imageUrl6;
    public boolean isMostPopular;

}
