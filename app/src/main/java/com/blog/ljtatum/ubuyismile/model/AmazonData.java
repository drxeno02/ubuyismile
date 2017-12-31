package com.blog.ljtatum.ubuyismile.model;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.app.amazon.framework.enums.Enum;

import org.apache.commons.codec.binary.StringUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by LJTat on 12/25/2017.
 */

public class AmazonData {
    private static ArrayList<AmazonModel> alDeals = new ArrayList<>();
    private static ArrayList<AmazonModel> alApparel = new ArrayList<>();
    private static ArrayList<AmazonModel> alAppliances = new ArrayList<>();
    private static ArrayList<AmazonModel> alAutomotive = new ArrayList<>();
    private static ArrayList<AmazonModel> alBaby = new ArrayList<>();
    private static ArrayList<AmazonModel> alBeauty = new ArrayList<>();
    private static ArrayList<AmazonModel> alBooks = new ArrayList<>();
    private static ArrayList<AmazonModel> alDVD = new ArrayList<>();
    private static ArrayList<AmazonModel> alElectronics = new ArrayList<>();
    private static ArrayList<AmazonModel> alGrocery = new ArrayList<>();
    private static ArrayList<AmazonModel> alHealthPesonalCare = new ArrayList<>();
    private static ArrayList<AmazonModel> alHomeGarden = new ArrayList<>();
    private static ArrayList<AmazonModel> alJewelry = new ArrayList<>();
    private static ArrayList<AmazonModel> alKindleStore = new ArrayList<>();
    private static ArrayList<AmazonModel> alLawnGarden = new ArrayList<>();
    private static ArrayList<AmazonModel> alLuggageBags = new ArrayList<>();
    private static ArrayList<AmazonModel> alLuxeryBeauty = new ArrayList<>();
    private static ArrayList<AmazonModel> alMusic = new ArrayList<>();
    private static ArrayList<AmazonModel> alMusicalInstruments = new ArrayList<>();
    private static ArrayList<AmazonModel> alOfficeProducts = new ArrayList<>();
    private static ArrayList<AmazonModel> alAmazonPantry = new ArrayList<>();
    private static ArrayList<AmazonModel> alPCHardware = new ArrayList<>();
    private static ArrayList<AmazonModel> alPetSupplies = new ArrayList<>();
    private static ArrayList<AmazonModel> alShoes = new ArrayList<>();
    private static ArrayList<AmazonModel> alSoftware = new ArrayList<>();
    private static ArrayList<AmazonModel> alSportingGoods = new ArrayList<>();
    private static ArrayList<AmazonModel> alToys = new ArrayList<>();
    private static ArrayList<AmazonModel> alVideoGames = new ArrayList<>();
    private static ArrayList<AmazonModel> alWatches = new ArrayList<>();

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

    @SuppressLint("NewApi")
    public static String getAmazonASINRequest(@NonNull ArrayList<AmazonModel> asinList) {
        // append all asin numbers for Amazon request
        StringBuilder asinRequest = new StringBuilder();
        for (int i = 0; i < asinList.size(); i++) {
            if (i == 0) {
                asinRequest.append(asinList.get(i).asin);
            } else {
                asinRequest.append(" , ").append(asinList.get(i).asin);
            }
        }
        return asinRequest.toString().trim();
    }

    /**
     * Getter for deals items
     *
     * @return List of deals items
     */
    public static ArrayList<AmazonModel> getDeals() {
        return alDeals;
    }

    /**
     * Setter for deals items
     *
     * @param alDeals List of deals items
     */
    public static void setDeals(ArrayList<AmazonModel> alDeals) {
        AmazonData.alDeals = alDeals;
    }

    /**
     * Getter for apparel items
     *
     * @return List of apparel items
     */
    public static ArrayList<AmazonModel> getApparel() {
        return alApparel;
    }

    /**
     * Setter for apparel items
     *
     * @param alApparel List of apparel items
     */
    public static void setApparel(ArrayList<AmazonModel> alApparel) {
        AmazonData.alApparel = alApparel;
    }

    /**
     * Getter for appliances items
     *
     * @return List of appliances items
     */
    public static ArrayList<AmazonModel> getAppliances() {
        return alAppliances;
    }

    /**
     * Setter for appliances items
     *
     * @param alAppliances List of appliances items
     */
    public static void setAppliances(ArrayList<AmazonModel> alAppliances) {
        AmazonData.alAppliances = alAppliances;
    }

    /**
     * Getter for automotive items
     *
     * @return List of automotive items
     */
    public static ArrayList<AmazonModel> getAutomotive() {
        return alAutomotive;
    }

    /**
     * Setter for automotive items
     *
     * @param alAutomotive List of automotive items
     */
    public static void setAutomotive(ArrayList<AmazonModel> alAutomotive) {
        AmazonData.alAutomotive = alAutomotive;
    }

    /**
     * Getter for baby items
     *
     * @return List of baby items
     */
    public static ArrayList<AmazonModel> getBaby() {
        return alBaby;
    }

    /**
     * Setter for baby items
     *
     * @param alBaby List of baby items
     */
    public static void setBaby(ArrayList<AmazonModel> alBaby) {
        AmazonData.alBaby = alBaby;
    }

    /**
     * Getter for beauty items
     *
     * @return List of beauty items
     */
    public static ArrayList<AmazonModel> getBeauty() {
        return alBeauty;
    }

    /**
     * Setter for beauty items
     *
     * @param alBeauty List of beauty items
     */
    public static void setBeauty(ArrayList<AmazonModel> alBeauty) {
        AmazonData.alBeauty = alBeauty;
    }

    /**
     * Getter for books items
     *
     * @return List of books items
     */
    public static ArrayList<AmazonModel> getBooks() {
        return alBooks;
    }

    /**
     * Setter for books items
     *
     * @param alBooks List of books items
     */
    public static void setBooks(ArrayList<AmazonModel> alBooks) {
        AmazonData.alBooks = alBooks;
    }

    /**
     * Getter for DVD items
     *
     * @return List of DVD items
     */
    public static ArrayList<AmazonModel> getDVD() {
        return alDVD;
    }

    /**
     * Setter for DVD items
     *
     * @param alDVD List of DVD items
     */
    public static void setDVD(ArrayList<AmazonModel> alDVD) {
        AmazonData.alDVD = alDVD;
    }

    /**
     * Getter for electronics items
     *
     * @return List of electronics items
     */
    public static ArrayList<AmazonModel> getElectronics() {
        return alElectronics;
    }

    /**
     * Setter for electronics items
     *
     * @param alElectronics List of electronics items
     */
    public static void setElectronics(ArrayList<AmazonModel> alElectronics) {
        AmazonData.alElectronics = alElectronics;
    }

    /**
     * Getter for grocery items
     *
     * @return List of grocery items
     */
    public static ArrayList<AmazonModel> getGrocery() {
        return alGrocery;
    }

    /**
     * Setter for grocery items
     *
     * @param alGrocery List of grocery items
     */
    public static void setGrocery(ArrayList<AmazonModel> alGrocery) {
        AmazonData.alGrocery = alGrocery;
    }

    /**
     * Getter for health and personal care items
     *
     * @return List of health and personal care items
     */
    public static ArrayList<AmazonModel> getHealthPesonalCare() {
        return alHealthPesonalCare;
    }

    /**
     * Setter for health and personal care items
     *
     * @param alHealthPesonalCare List of health and personal care items
     */
    public static void setHealthPesonalCare(ArrayList<AmazonModel> alHealthPesonalCare) {
        AmazonData.alHealthPesonalCare = alHealthPesonalCare;
    }

    /**
     * Getter for home and garden items
     *
     * @return List of home and garden items
     */
    public static ArrayList<AmazonModel> getHomeGarden() {
        return alHomeGarden;
    }

    /**
     * Setter for home and garden items
     *
     * @param alHomeGarden List of home and garden items
     */
    public static void setHomeGarden(ArrayList<AmazonModel> alHomeGarden) {
        AmazonData.alHomeGarden = alHomeGarden;
    }

    /**
     * Getter for jewelry items
     *
     * @return List of jewelry items
     */
    public static ArrayList<AmazonModel> getJewelry() {
        return alJewelry;
    }

    /**
     * Setter for jewelry items
     *
     * @param alJewelry List of jewelry items
     */
    public static void setJewelry(ArrayList<AmazonModel> alJewelry) {
        AmazonData.alJewelry = alJewelry;
    }

    /**
     * Getter for kindle store items
     *
     * @return List of kindle store items
     */
    public static ArrayList<AmazonModel> getKindleStore() {
        return alKindleStore;
    }

    /**
     * Setter for kindle store items
     *
     * @param alKindleStore List of kindle store items
     */
    public static void setKindleStore(ArrayList<AmazonModel> alKindleStore) {
        AmazonData.alKindleStore = alKindleStore;
    }

    /**
     * Getter for lawn and garden items
     *
     * @return List of lawn and garden items
     */
    public static ArrayList<AmazonModel> getLawnGarden() {
        return alLawnGarden;
    }

    /**
     * Setter for lawn and garden items
     *
     * @param alLawnGarden List of lawn and garden items
     */
    public static void setLawnGarden(ArrayList<AmazonModel> alLawnGarden) {
        AmazonData.alLawnGarden = alLawnGarden;
    }

    /**
     * Getter for luggage items
     *
     * @return List of luggage items
     */
    public static ArrayList<AmazonModel> getLuggageBags() {
        return alLuggageBags;
    }

    /**
     * Setter for luggage items
     *
     * @param alLuggageBags List of luggage items
     */
    public static void setLuggageBags(ArrayList<AmazonModel> alLuggageBags) {
        AmazonData.alLuggageBags = alLuggageBags;
    }

    /**
     * Getter for luxery and beauty items
     *
     * @return List of luxery and beauty items
     */
    public static ArrayList<AmazonModel> getLuxeryBeauty() {
        return alLuxeryBeauty;
    }

    /**
     * Setter for luxery and beauty items
     *
     * @param alLuxeryBeauty List of luxery and beauty items
     */
    public static void setLuxeryBeauty(ArrayList<AmazonModel> alLuxeryBeauty) {
        AmazonData.alLuxeryBeauty = alLuxeryBeauty;
    }

    /**
     * Getter for music items
     *
     * @return List of music items
     */
    public static ArrayList<AmazonModel> getMusic() {
        return alMusic;
    }

    /**
     * Setter for music items
     *
     * @param alMusic List of music items
     */
    public static void setMusic(ArrayList<AmazonModel> alMusic) {
        AmazonData.alMusic = alMusic;
    }

    /**
     * Getter for musical instruments items
     *
     * @return List of musical instruments items
     */
    public static ArrayList<AmazonModel> getMusicalInstruments() {
        return alMusicalInstruments;
    }

    /**
     * Setter for musical instruments items
     *
     * @param alMusicalInstruments List of musical instruments items
     */
    public static void setMusicalInstruments(ArrayList<AmazonModel> alMusicalInstruments) {
        AmazonData.alMusicalInstruments = alMusicalInstruments;
    }

    /**
     * Getter for office supplies items
     *
     * @return List of office supplies items
     */
    public static ArrayList<AmazonModel> getOfficeProducts() {
        return alOfficeProducts;
    }

    /**
     * Setter for office supplies items
     *
     * @param alOfficeProducts List of office supplies items
     */
    public static void setOfficeProducts(ArrayList<AmazonModel> alOfficeProducts) {
        AmazonData.alOfficeProducts = alOfficeProducts;
    }

    /**
     * Getter for amazon pantry items
     *
     * @return List of amazon pantry items
     */
    public static ArrayList<AmazonModel> getAmazonPantry() {
        return alAmazonPantry;
    }

    /**
     * Setter for amazon pantry items
     *
     * @param alAmazonPantry List of amazon pantry items
     */
    public static void setAmazonPantry(ArrayList<AmazonModel> alAmazonPantry) {
        AmazonData.alAmazonPantry = alAmazonPantry;
    }

    /**
     * Getter for PC hardware items
     *
     * @return List of PC hardware items
     */
    public static ArrayList<AmazonModel> getPCHardware() {
        return alPCHardware;
    }

    /**
     * Setter for PC hardware items
     *
     * @param alPCHardware List of PC hardware items
     */
    public static void setPCHardware(ArrayList<AmazonModel> alPCHardware) {
        AmazonData.alPCHardware = alPCHardware;
    }

    /**
     * Getter for pet supplies items
     *
     * @return List of pet supplies items
     */
    public static ArrayList<AmazonModel> getPetSupplies() {
        return alPetSupplies;
    }

    /**
     * Setter for pet supplies items
     *
     * @param alPetSupplies List of pet supplies items
     */
    public static void setPetSupplies(ArrayList<AmazonModel> alPetSupplies) {
        AmazonData.alPetSupplies = alPetSupplies;
    }

    /**
     * Getter for shoes items
     *
     * @return List of shoes items
     */
    public static ArrayList<AmazonModel> getShoes() {
        return alShoes;
    }

    /**
     * Setter for shoes items
     *
     * @param alShoes List of shoes items
     */
    public static void setShoes(ArrayList<AmazonModel> alShoes) {
        AmazonData.alShoes = alShoes;
    }

    /**
     * Getter for software items
     *
     * @return List of software items
     */
    public static ArrayList<AmazonModel> getSoftware() {
        return alSoftware;
    }

    /**
     * Setter for software items
     *
     * @param alSoftware List of software items
     */
    public static void setSoftware(ArrayList<AmazonModel> alSoftware) {
        AmazonData.alSoftware = alSoftware;
    }

    /**
     * Getter for sporting goods items
     *
     * @return List of sporting goods items
     */
    public static ArrayList<AmazonModel> getSportingGoods() {
        return alSportingGoods;
    }

    /**
     * Setter for sporting goods items
     *
     * @param alSportingGoods List of sporting goods items
     */
    public static void setSportingGoods(ArrayList<AmazonModel> alSportingGoods) {
        AmazonData.alSportingGoods = alSportingGoods;
    }

    /**
     * Getter for toys items
     *
     * @return List of toys items
     */
    public static ArrayList<AmazonModel> getToys() {
        return alToys;
    }

    /**
     * Setter for toys items
     *
     * @param alToys List of toys items
     */
    public static void setToys(ArrayList<AmazonModel> alToys) {
        AmazonData.alToys = alToys;
    }

    /**
     * Getter for video games items
     *
     * @return List of video games items
     */
    public static ArrayList<AmazonModel> getVideoGames() {
        return alVideoGames;
    }

    /**
     * Setter for video games items
     *
     * @param alVideoGames List of video games items
     */
    public static void setVideoGames(ArrayList<AmazonModel> alVideoGames) {
        AmazonData.alVideoGames = alVideoGames;
    }

    /**
     * Getter for watches items
     *
     * @return List of watches items
     */
    public static ArrayList<AmazonModel> getWatches() {
        return alWatches;
    }

    /**
     * Setter for watches items
     *
     * @param alWatches List of watches items
     */
    public static void setWatches(ArrayList<AmazonModel> alWatches) {
        AmazonData.alWatches = alWatches;
    }
}
