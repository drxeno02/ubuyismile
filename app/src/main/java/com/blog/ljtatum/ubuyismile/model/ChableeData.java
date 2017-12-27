package com.blog.ljtatum.ubuyismile.model;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.firebase.FirebaseUtils;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/25/2017.
 */

public class ChableeData {
    private static ArrayList<AmazonModel> alChablee = new ArrayList<>();

    /**
     * Getter for Chablee items
     *
     * @return List of Chablee items
     */
    public static ArrayList<AmazonModel> getChablee() {
        return alChablee;
    }

    /**
     * Setter for Chablee items
     *
     * @param alChablee List of Chablee items
     */
    public static void setChablee(ArrayList<AmazonModel> alChablee) {
        ChableeData.alChablee = alChablee;
    }

    /**
     * Method is used to retrieve a list of all Chablee categories
     *
     * @return List of Chablee categories
     */
    public static ArrayList<String> getChableeCategories() {
        ArrayList<String> alChableeCategories = new ArrayList<>();
        alChableeCategories.add(Enum.ItemCategoryChablee.RINGS.toString());
        alChableeCategories.add(Enum.ItemCategoryChablee.NECKLACES.toString());
        alChableeCategories.add(Enum.ItemCategoryChablee.GEMSTONE.toString());
        alChableeCategories.add(Enum.ItemCategoryChablee.ROCKS.toString());
        return alChableeCategories;
    }
}
