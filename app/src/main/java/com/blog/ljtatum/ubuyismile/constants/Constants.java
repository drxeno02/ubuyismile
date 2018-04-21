package com.blog.ljtatum.ubuyismile.constants;

import com.blog.ljtatum.ubuyismile.BuildConfig;

/**
 * Created by leonard on 9/22/2017.
 */

public class Constants {
    // debuggable mode; true to see debug logs otherwise false
    public static final boolean DEBUG = BuildConfig.DEBUG_MODE;

    // verbose mode; true to see verbosity mode otherwise false
    public static final boolean DEBUG_VERBOSE = true;

    // default image size
    public static final int DEFAULT_IMAGE_SIZE_500 = 500;
    public static final int DEFAULT_IMAGE_SIZE_700 = 700;

    // bundle keys
    public static final String KEY_CATEGORY = "key_category";
    public static final String KEY_ITEM_TYPE = "key_item_type";
    public static final String KEY_ITEM_POS = "key_item_pos";
    public static final String KEY_ITEM_PURCHASE_URL = "key_item_url";

}
