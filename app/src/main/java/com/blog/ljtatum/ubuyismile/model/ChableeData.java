package com.blog.ljtatum.ubuyismile.model;

import android.support.annotation.NonNull;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/25/2017.
 */

public class ChableeData {
    private static ArrayList<ChableeModel> alCrowns = new ArrayList<>();
    private static ArrayList<ChableeModel> alRings = new ArrayList<>();
    private static ArrayList<ChableeModel> alNecklaces = new ArrayList<>();
    private static ArrayList<ChableeModel> alRocks = new ArrayList<>();

    /**
     * Getter for Chablee crown items
     *
     * @return List of Chablee crown items
     */
    public static ArrayList<ChableeModel> getCrowns() {
        return alCrowns;
    }

    /**
     * Setter for Chablee crown items
     *
     * @param alCrowns List of Chablee crown items
     */
    public static void setCrowns(ArrayList<ChableeModel> alCrowns) {
        ChableeData.alCrowns = filterItemList(alCrowns);
    }

    /**
     * Getter for Chablee rings items
     *
     * @return List of Chablee rings items
     */
    public static ArrayList<ChableeModel> getRings() {
        return alRings;
    }

    /**
     * Setter for Chablee rings items
     *
     * @param alRings List of Chablee rings items
     */
    public static void setRings(ArrayList<ChableeModel> alRings) {
        ChableeData.alRings = filterItemList(alRings);
    }

    /**
     * Getter for Chablee necklaces items
     *
     * @return List of Chablee necklaces items
     */
    public static ArrayList<ChableeModel> getNecklaces() {
        return alNecklaces;
    }

    /**
     * Setter for Chablee necklaces items
     *
     * @param alNecklaces List of Chablee necklaces items
     */
    public static void setNecklaces(ArrayList<ChableeModel> alNecklaces) {
        ChableeData.alNecklaces = filterItemList(alNecklaces);
    }

    /**
     * Getter for Chablee rocks items
     *
     * @return List of Chablee rocks items
     */
    public static ArrayList<ChableeModel> getRocks() {
        return alRocks;
    }

    /**
     * Setter for Chablee rocks items
     *
     * @param alRocks List of Chablee rocks items
     */
    public static void setRocks(ArrayList<ChableeModel> alRocks) {
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
    private static ArrayList<ChableeModel> filterItemList(@NonNull ArrayList<ChableeModel> alItems) {
        ArrayList<ChableeModel> filteredItems = new ArrayList<>();
        for (int i = 0; i < alItems.size(); i++) {
            if (!FrameworkUtils.isStringEmpty(alItems.get(i).title)) {
                ChableeModel chableeModel = new ChableeModel();
                chableeModel.price = alItems.get(i).price;
                chableeModel.salePrice = alItems.get(i).salePrice;
                chableeModel.title = alItems.get(i).title;
                chableeModel.description = alItems.get(i).description;
                chableeModel.imageUrl1 = alItems.get(i).imageUrl1;
                chableeModel.imageUrl2 = alItems.get(i).imageUrl2;
                chableeModel.imageUrl3 = alItems.get(i).imageUrl3;
                chableeModel.imageUrl4 = alItems.get(i).imageUrl4;
                chableeModel.imageUrl5 = alItems.get(i).imageUrl5;
                chableeModel.category = alItems.get(i).category;
                chableeModel.label = alItems.get(i).label;
                chableeModel.timestamp = alItems.get(i).timestamp;
                chableeModel.isFeatured = alItems.get(i).isFeatured;
                chableeModel.isMostPopular = alItems.get(i).isMostPopular;
                filteredItems.add(chableeModel);
            }
        }
        return filteredItems;
    }
}
