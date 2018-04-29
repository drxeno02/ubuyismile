package com.blog.ljtatum.ubuyismile.model;

import android.support.annotation.NonNull;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/25/2017.
 */

public class Categories {
    private static ArrayList<ItemModel> alCrowns = new ArrayList<>();
    private static ArrayList<ItemModel> alRings = new ArrayList<>();
    private static ArrayList<ItemModel> alNecklaces = new ArrayList<>();
    private static ArrayList<ItemModel> alRocks = new ArrayList<>();

    /**
     * Method is used to retrieve a list of all Chablee categories
     *
     * @return List of Chablee categories
     */
    public static ArrayList<String> getChableeCategories() {
        ArrayList<String> alChableeCategories = new ArrayList<>();
        alChableeCategories.add(Enum.ItemCategoryChablee.CROWNS.toString());
        alChableeCategories.add(Enum.ItemCategoryChablee.RINGS.toString());
        alChableeCategories.add(Enum.ItemCategoryChablee.NECKLACES.toString());
        alChableeCategories.add(Enum.ItemCategoryChablee.ROCKS.toString());
        return alChableeCategories;
    }
}
