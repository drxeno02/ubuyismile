package com.blog.ljtatum.ubuyismile.model;

import com.app.amazon.framework.enums.Enum;

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
        ChableeData.alCrowns = alCrowns;
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
        ChableeData.alRings = alRings;
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
        ChableeData.alNecklaces = alNecklaces;
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
        ChableeData.alRocks = alRocks;
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
}
