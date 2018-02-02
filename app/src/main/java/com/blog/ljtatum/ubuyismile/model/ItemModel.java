package com.blog.ljtatum.ubuyismile.model;

/**
 * Created by LJTat on 1/25/2018.
 */

public class ItemModel {
    // base values
    public String category, asin, label, timestamp;
    public boolean isBrowseItem;

    // Chablee specific values
    public String price, salePrice, title, description, purchaseUrl, imageUrl1, imageUrl2,
            imageUrl3, imageUrl4, imageUrl5;
    public boolean isFeatured, isMostPopular;
}
