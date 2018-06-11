package com.blog.ljtatum.ubuyismile.model;

/**
 * Created by LJTat on 1/25/2018.
 */

public class ItemModel {
    private static final String WIX_URL_PREFIX = "https://static.wixstatic.com/media/";

    // base values
    public String category, asin, label, timestamp, itemId;
    public boolean isBrowseItem, isLabelSet;

    // Chablee specific values
    public String price, salePrice, title, description, purchaseUrl, imageUrl1, imageUrl2,
            imageUrl3, imageUrl4, imageUrl5;
    public boolean isFeatured, isMostPopular, isFavorite;

    /**
     * Method is used to format image urls
     * @param url Image url to be checked for proper format
     * @return Formatted image url
     */
    public static String getFormattedImageUrl(String url) {
        if (!url.contains(WIX_URL_PREFIX)) {
            return WIX_URL_PREFIX.concat(url);
        }
        return url;
    }
}
