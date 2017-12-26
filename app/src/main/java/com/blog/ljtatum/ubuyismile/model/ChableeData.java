package com.blog.ljtatum.ubuyismile.model;

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
}
