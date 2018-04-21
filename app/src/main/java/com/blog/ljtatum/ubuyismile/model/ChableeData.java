package com.blog.ljtatum.ubuyismile.model;

import android.support.annotation.NonNull;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/25/2017.
 */

public class ChableeData {
    private static ArrayList<ItemModel> alCrowns = new ArrayList<>();
    private static ArrayList<ItemModel> alRings = new ArrayList<>();
    private static ArrayList<ItemModel> alNecklaces = new ArrayList<>();
    private static ArrayList<ItemModel> alRocks = new ArrayList<>();

    /**
     * Getter for Chablee crown items
     *
     * @return List of Chablee crown items
     */
    public static ArrayList<ItemModel> getCrowns() {
        return alCrowns;
    }

    /**
     * Setter for Chablee crown items
     *
     * @param alCrowns List of Chablee crown items
     */
    public static void setCrowns(ArrayList<ItemModel> alCrowns) {
        ChableeData.alCrowns = filterItemList(alCrowns);
    }

    /**
     * Getter for Chablee rings items
     *
     * @return List of Chablee rings items
     */
    public static ArrayList<ItemModel> getRings() {
        return alRings;
    }

    /**
     * Setter for Chablee rings items
     *
     * @param alRings List of Chablee rings items
     */
    public static void setRings(ArrayList<ItemModel> alRings) {
        ChableeData.alRings = filterItemList(alRings);
    }

    /**
     * Getter for Chablee necklaces items
     *
     * @return List of Chablee necklaces items
     */
    public static ArrayList<ItemModel> getNecklaces() {
        return alNecklaces;
    }

    /**
     * Setter for Chablee necklaces items
     *
     * @param alNecklaces List of Chablee necklaces items
     */
    public static void setNecklaces(ArrayList<ItemModel> alNecklaces) {
        ChableeData.alNecklaces = filterItemList(alNecklaces);
    }

    /**
     * Getter for Chablee rocks items
     *
     * @return List of Chablee rocks items
     */
    public static ArrayList<ItemModel> getRocks() {
        return alRocks;
    }

    /**
     * Setter for Chablee rocks items
     *
     * @param alRocks List of Chablee rocks items
     */
    public static void setRocks(ArrayList<ItemModel> alRocks) {
        ChableeData.alRocks = filterItemList(alRocks);
    }

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

    /**
     * Method is used to remove empty items. Any item that does not have the required
     * attribute 'title' is removed.
     *
     * @param alItems List of Chablee items
     * @return Filtered list of Chablee items
     */
    private static ArrayList<ItemModel> filterItemList(@NonNull ArrayList<ItemModel> alItems) {
        ArrayList<ItemModel> filteredItems = new ArrayList<>();
        for (int i = 0; i < alItems.size(); i++) {
            if (!FrameworkUtils.isStringEmpty(alItems.get(i).title)) {
                ItemModel itemModel = new ItemModel();
                itemModel.itemId = alItems.get(i).itemId;
                itemModel.price = alItems.get(i).price;
                itemModel.salePrice = alItems.get(i).salePrice;
                itemModel.title = alItems.get(i).title;
                itemModel.description = alItems.get(i).description;
                itemModel.imageUrl1 = alItems.get(i).imageUrl1;
                itemModel.imageUrl2 = alItems.get(i).imageUrl2;
                itemModel.imageUrl3 = alItems.get(i).imageUrl3;
                itemModel.imageUrl4 = alItems.get(i).imageUrl4;
                itemModel.imageUrl5 = alItems.get(i).imageUrl5;
                itemModel.purchaseUrl = alItems.get(i).purchaseUrl;
                itemModel.category = alItems.get(i).category;
                itemModel.label = alItems.get(i).label;
                itemModel.timestamp = alItems.get(i).timestamp;
                itemModel.isFeatured = alItems.get(i).isFeatured;
                itemModel.isMostPopular = alItems.get(i).isMostPopular;
                itemModel.isBrowseItem = alItems.get(i).isBrowseItem;
                itemModel.isFeatured = alItems.get(i).isFeatured;
                itemModel.isMostPopular = alItems.get(i).isMostPopular;
                filteredItems.add(itemModel);
            }
        }
        return filteredItems;
    }
}
