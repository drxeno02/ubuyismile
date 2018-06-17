package com.blog.ljtatum.ubuyismile.interfaces;

import android.view.View;

import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.enums.Enum;

/**
 * Created by LJTat on 4/3/2018.
 */

public interface OnClickAdapterListener {

    void onClick(ItemDatabaseModel item, Enum.ItemType itemType);
}
