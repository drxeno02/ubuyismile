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

    // test ad unit id
    public static final String AD_ID_TEST = "950036DB8197D296BE390357BD9A964E";

    // default image size
    public static final int DEFAULT_IMAGE_SIZE_250 = 250;
    public static final int DEFAULT_IMAGE_SIZE_500 = 500;

    // bundle keys
    public static final String KEY_CATEGORY = "key_category";
    public static final String KEY_ITEM_TYPE = "key_item_type";
    public static final String KEY_ITEM_ID = "key_item_id";
    public static final String KEY_SCREENSHOT_POS = "key_screenshot_pos";
    public static final String KEY_SEARCH_CATEGORY = "key_search_category";
    public static final String KEY_DAILY_BONUS = "key_daily_bonus";
    public static final String KEY_DAILY_BONUS_DATE = "key_daily_bonus_date";
}
