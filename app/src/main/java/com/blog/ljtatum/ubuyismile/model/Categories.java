package com.blog.ljtatum.ubuyismile.model;

import com.app.amazon.framework.enums.Enum;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/25/2017.
 */

public class Categories {

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
     * Method is used to retrieve a list of all Amazon categories
     *
     * @return List of Amazon categories
     */
    public static ArrayList<String> getAmazonCategories() {
        ArrayList<String> alAmazonCategories = new ArrayList<>();
        alAmazonCategories.add(Enum.ItemCategory.DEALS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.APPAREL.toString());
//        alAmazonCategories.add(Enum.ItemCategory.APPLIANCES.toString());
//        alAmazonCategories.add(Enum.ItemCategory.AUTOMOTIVE.toString());
//        alAmazonCategories.add(Enum.ItemCategory.BABY.toString());
//        alAmazonCategories.add(Enum.ItemCategory.BEAUTY.toString());
        alAmazonCategories.add(Enum.ItemCategory.BOOKS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.DVD.toString());
//        alAmazonCategories.add(Enum.ItemCategory.ELECTRONICS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.GROCERY.toString());
//        alAmazonCategories.add(Enum.ItemCategory.HEALTH_AND_PERSONAL_CARE.toString());
//        alAmazonCategories.add(Enum.ItemCategory.HOME_AND_GARDEN.toString());
//        alAmazonCategories.add(Enum.ItemCategory.JEWELRY.toString());
//        alAmazonCategories.add(Enum.ItemCategory.KINDLE_STORE.toString());
//        alAmazonCategories.add(Enum.ItemCategory.LAWN_AND_GARDEN.toString());
//        alAmazonCategories.add(Enum.ItemCategory.LUGGAGE_AND_BAGS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.LUXURY_BEAUTY.toString());
//        alAmazonCategories.add(Enum.ItemCategory.MUSIC.toString());
//        alAmazonCategories.add(Enum.ItemCategory.MUSICAL_INSTRUMENTS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.OFFICE_PRODUCTS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.AMAZON_PANTRY.toString());
//        alAmazonCategories.add(Enum.ItemCategory.PC_HARDWARE.toString());
//        alAmazonCategories.add(Enum.ItemCategory.PET_SUPPLIES.toString());
//        alAmazonCategories.add(Enum.ItemCategory.SHOES.toString());
//        alAmazonCategories.add(Enum.ItemCategory.SOFTWARE.toString());
//        alAmazonCategories.add(Enum.ItemCategory.SPORTING_GOODS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.TOYS.toString());
//        alAmazonCategories.add(Enum.ItemCategory.VIDEO_GAMES.toString());
//        alAmazonCategories.add(Enum.ItemCategory.WATCHES.toString());
        return alAmazonCategories;
    }
}
