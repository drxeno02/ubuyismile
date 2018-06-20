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
    private static ArrayList<ItemModel> alDeals = new ArrayList<>();
    private static ArrayList<ItemModel> alApparel = new ArrayList<>();
    private static ArrayList<ItemModel> alAppliances = new ArrayList<>();
    private static ArrayList<ItemModel> alAutomotive = new ArrayList<>();
    private static ArrayList<ItemModel> alBaby = new ArrayList<>();
    private static ArrayList<ItemModel> alBeauty = new ArrayList<>();
    private static ArrayList<ItemModel> alBooks = new ArrayList<>();
    private static ArrayList<ItemModel> alDVD = new ArrayList<>();
    private static ArrayList<ItemModel> alElectronics = new ArrayList<>();
    private static ArrayList<ItemModel> alGrocery = new ArrayList<>();
    private static ArrayList<ItemModel> alHealthPesonalCare = new ArrayList<>();
    private static ArrayList<ItemModel> alHomeGarden = new ArrayList<>();
    private static ArrayList<ItemModel> alJewelry = new ArrayList<>();
    private static ArrayList<ItemModel> alKindleStore = new ArrayList<>();
    private static ArrayList<ItemModel> alLawnGarden = new ArrayList<>();
    private static ArrayList<ItemModel> alLuggageBags = new ArrayList<>();
    private static ArrayList<ItemModel> alLuxeryBeauty = new ArrayList<>();
    private static ArrayList<ItemModel> alMusic = new ArrayList<>();
    private static ArrayList<ItemModel> alMusicalInstruments = new ArrayList<>();
    private static ArrayList<ItemModel> alOfficeProducts = new ArrayList<>();
    private static ArrayList<ItemModel> alAmazonPantry = new ArrayList<>();
    private static ArrayList<ItemModel> alPCHardware = new ArrayList<>();
    private static ArrayList<ItemModel> alPetSupplies = new ArrayList<>();
    private static ArrayList<ItemModel> alShoes = new ArrayList<>();
    private static ArrayList<ItemModel> alSoftware = new ArrayList<>();
    private static ArrayList<ItemModel> alSportingGoods = new ArrayList<>();
    private static ArrayList<ItemModel> alToys = new ArrayList<>();
    private static ArrayList<ItemModel> alVideoGames = new ArrayList<>();
    private static ArrayList<ItemModel> alWatches = new ArrayList<>();


    @SuppressLint("NewApi")
    public static String getAmazonASINRequest(@NonNull ArrayList<ItemModel> asinList) {
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
    public static ArrayList<ItemModel> getDeals() {
        return alDeals;
    }

    /**
     * Setter for deals items
     *
     * @param alDeals List of deals items
     */
    public static void setDeals(ArrayList<ItemModel> alDeals) {
        AmazonData.alDeals = alDeals;
    }

    /**
     * Getter for apparel items
     *
     * @return List of apparel items
     */
    public static ArrayList<ItemModel> getApparel() {
        return alApparel;
    }

    /**
     * Setter for apparel items
     *
     * @param alApparel List of apparel items
     */
    public static void setApparel(ArrayList<ItemModel> alApparel) {
        AmazonData.alApparel = alApparel;
    }

    /**
     * Getter for appliances items
     *
     * @return List of appliances items
     */
    public static ArrayList<ItemModel> getAppliances() {
        return alAppliances;
    }

    /**
     * Setter for appliances items
     *
     * @param alAppliances List of appliances items
     */
    public static void setAppliances(ArrayList<ItemModel> alAppliances) {
        AmazonData.alAppliances = alAppliances;
    }

    /**
     * Getter for automotive items
     *
     * @return List of automotive items
     */
    public static ArrayList<ItemModel> getAutomotive() {
        return alAutomotive;
    }

    /**
     * Setter for automotive items
     *
     * @param alAutomotive List of automotive items
     */
    public static void setAutomotive(ArrayList<ItemModel> alAutomotive) {
        AmazonData.alAutomotive = alAutomotive;
    }

    /**
     * Getter for baby items
     *
     * @return List of baby items
     */
    public static ArrayList<ItemModel> getBaby() {
        return alBaby;
    }

    /**
     * Setter for baby items
     *
     * @param alBaby List of baby items
     */
    public static void setBaby(ArrayList<ItemModel> alBaby) {
        AmazonData.alBaby = alBaby;
    }

    /**
     * Getter for beauty items
     *
     * @return List of beauty items
     */
    public static ArrayList<ItemModel> getBeauty() {
        return alBeauty;
    }

    /**
     * Setter for beauty items
     *
     * @param alBeauty List of beauty items
     */
    public static void setBeauty(ArrayList<ItemModel> alBeauty) {
        AmazonData.alBeauty = alBeauty;
    }

    /**
     * Getter for books items
     *
     * @return List of books items
     */
    public static ArrayList<ItemModel> getBooks() {
        return alBooks;
    }

    /**
     * Setter for books items
     *
     * @param alBooks List of books items
     */
    public static void setBooks(ArrayList<ItemModel> alBooks) {
        AmazonData.alBooks = alBooks;
    }

    /**
     * Getter for DVD items
     *
     * @return List of DVD items
     */
    public static ArrayList<ItemModel> getDVD() {
        return alDVD;
    }

    /**
     * Setter for DVD items
     *
     * @param alDVD List of DVD items
     */
    public static void setDVD(ArrayList<ItemModel> alDVD) {
        AmazonData.alDVD = alDVD;
    }

    /**
     * Getter for electronics items
     *
     * @return List of electronics items
     */
    public static ArrayList<ItemModel> getElectronics() {
        return alElectronics;
    }

    /**
     * Setter for electronics items
     *
     * @param alElectronics List of electronics items
     */
    public static void setElectronics(ArrayList<ItemModel> alElectronics) {
        AmazonData.alElectronics = alElectronics;
    }

    /**
     * Getter for grocery items
     *
     * @return List of grocery items
     */
    public static ArrayList<ItemModel> getGrocery() {
        return alGrocery;
    }

    /**
     * Setter for grocery items
     *
     * @param alGrocery List of grocery items
     */
    public static void setGrocery(ArrayList<ItemModel> alGrocery) {
        AmazonData.alGrocery = alGrocery;
    }

    /**
     * Getter for health and personal care items
     *
     * @return List of health and personal care items
     */
    public static ArrayList<ItemModel> getHealthPesonalCare() {
        return alHealthPesonalCare;
    }

    /**
     * Setter for health and personal care items
     *
     * @param alHealthPesonalCare List of health and personal care items
     */
    public static void setHealthPesonalCare(ArrayList<ItemModel> alHealthPesonalCare) {
        AmazonData.alHealthPesonalCare = alHealthPesonalCare;
    }

    /**
     * Getter for home and garden items
     *
     * @return List of home and garden items
     */
    public static ArrayList<ItemModel> getHomeGarden() {
        return alHomeGarden;
    }

    /**
     * Setter for home and garden items
     *
     * @param alHomeGarden List of home and garden items
     */
    public static void setHomeGarden(ArrayList<ItemModel> alHomeGarden) {
        AmazonData.alHomeGarden = alHomeGarden;
    }

    /**
     * Getter for jewelry items
     *
     * @return List of jewelry items
     */
    public static ArrayList<ItemModel> getJewelry() {
        return alJewelry;
    }

    /**
     * Setter for jewelry items
     *
     * @param alJewelry List of jewelry items
     */
    public static void setJewelry(ArrayList<ItemModel> alJewelry) {
        AmazonData.alJewelry = alJewelry;
    }

    /**
     * Getter for kindle store items
     *
     * @return List of kindle store items
     */
    public static ArrayList<ItemModel> getKindleStore() {
        return alKindleStore;
    }

    /**
     * Setter for kindle store items
     *
     * @param alKindleStore List of kindle store items
     */
    public static void setKindleStore(ArrayList<ItemModel> alKindleStore) {
        AmazonData.alKindleStore = alKindleStore;
    }

    /**
     * Getter for lawn and garden items
     *
     * @return List of lawn and garden items
     */
    public static ArrayList<ItemModel> getLawnGarden() {
        return alLawnGarden;
    }

    /**
     * Setter for lawn and garden items
     *
     * @param alLawnGarden List of lawn and garden items
     */
    public static void setLawnGarden(ArrayList<ItemModel> alLawnGarden) {
        AmazonData.alLawnGarden = alLawnGarden;
    }

    /**
     * Getter for luggage items
     *
     * @return List of luggage items
     */
    public static ArrayList<ItemModel> getLuggageBags() {
        return alLuggageBags;
    }

    /**
     * Setter for luggage items
     *
     * @param alLuggageBags List of luggage items
     */
    public static void setLuggageBags(ArrayList<ItemModel> alLuggageBags) {
        AmazonData.alLuggageBags = alLuggageBags;
    }

    /**
     * Getter for luxery and beauty items
     *
     * @return List of luxery and beauty items
     */
    public static ArrayList<ItemModel> getLuxeryBeauty() {
        return alLuxeryBeauty;
    }

    /**
     * Setter for luxery and beauty items
     *
     * @param alLuxeryBeauty List of luxery and beauty items
     */
    public static void setLuxeryBeauty(ArrayList<ItemModel> alLuxeryBeauty) {
        AmazonData.alLuxeryBeauty = alLuxeryBeauty;
    }

    /**
     * Getter for music items
     *
     * @return List of music items
     */
    public static ArrayList<ItemModel> getMusic() {
        return alMusic;
    }

    /**
     * Setter for music items
     *
     * @param alMusic List of music items
     */
    public static void setMusic(ArrayList<ItemModel> alMusic) {
        AmazonData.alMusic = alMusic;
    }

    /**
     * Getter for musical instruments items
     *
     * @return List of musical instruments items
     */
    public static ArrayList<ItemModel> getMusicalInstruments() {
        return alMusicalInstruments;
    }

    /**
     * Setter for musical instruments items
     *
     * @param alMusicalInstruments List of musical instruments items
     */
    public static void setMusicalInstruments(ArrayList<ItemModel> alMusicalInstruments) {
        AmazonData.alMusicalInstruments = alMusicalInstruments;
    }

    /**
     * Getter for office supplies items
     *
     * @return List of office supplies items
     */
    public static ArrayList<ItemModel> getOfficeProducts() {
        return alOfficeProducts;
    }

    /**
     * Setter for office supplies items
     *
     * @param alOfficeProducts List of office supplies items
     */
    public static void setOfficeProducts(ArrayList<ItemModel> alOfficeProducts) {
        AmazonData.alOfficeProducts = alOfficeProducts;
    }

    /**
     * Getter for amazon pantry items
     *
     * @return List of amazon pantry items
     */
    public static ArrayList<ItemModel> getAmazonPantry() {
        return alAmazonPantry;
    }

    /**
     * Setter for amazon pantry items
     *
     * @param alAmazonPantry List of amazon pantry items
     */
    public static void setAmazonPantry(ArrayList<ItemModel> alAmazonPantry) {
        AmazonData.alAmazonPantry = alAmazonPantry;
    }

    /**
     * Getter for PC hardware items
     *
     * @return List of PC hardware items
     */
    public static ArrayList<ItemModel> getPCHardware() {
        return alPCHardware;
    }

    /**
     * Setter for PC hardware items
     *
     * @param alPCHardware List of PC hardware items
     */
    public static void setPCHardware(ArrayList<ItemModel> alPCHardware) {
        AmazonData.alPCHardware = alPCHardware;
    }

    /**
     * Getter for pet supplies items
     *
     * @return List of pet supplies items
     */
    public static ArrayList<ItemModel> getPetSupplies() {
        return alPetSupplies;
    }

    /**
     * Setter for pet supplies items
     *
     * @param alPetSupplies List of pet supplies items
     */
    public static void setPetSupplies(ArrayList<ItemModel> alPetSupplies) {
        AmazonData.alPetSupplies = alPetSupplies;
    }

    /**
     * Getter for shoes items
     *
     * @return List of shoes items
     */
    public static ArrayList<ItemModel> getShoes() {
        return alShoes;
    }

    /**
     * Setter for shoes items
     *
     * @param alShoes List of shoes items
     */
    public static void setShoes(ArrayList<ItemModel> alShoes) {
        AmazonData.alShoes = alShoes;
    }

    /**
     * Getter for software items
     *
     * @return List of software items
     */
    public static ArrayList<ItemModel> getSoftware() {
        return alSoftware;
    }

    /**
     * Setter for software items
     *
     * @param alSoftware List of software items
     */
    public static void setSoftware(ArrayList<ItemModel> alSoftware) {
        AmazonData.alSoftware = alSoftware;
    }

    /**
     * Getter for sporting goods items
     *
     * @return List of sporting goods items
     */
    public static ArrayList<ItemModel> getSportingGoods() {
        return alSportingGoods;
    }

    /**
     * Setter for sporting goods items
     *
     * @param alSportingGoods List of sporting goods items
     */
    public static void setSportingGoods(ArrayList<ItemModel> alSportingGoods) {
        AmazonData.alSportingGoods = alSportingGoods;
    }

    /**
     * Getter for toys items
     *
     * @return List of toys items
     */
    public static ArrayList<ItemModel> getToys() {
        return alToys;
    }

    /**
     * Setter for toys items
     *
     * @param alToys List of toys items
     */
    public static void setToys(ArrayList<ItemModel> alToys) {
        AmazonData.alToys = alToys;
    }

    /**
     * Getter for video games items
     *
     * @return List of video games items
     */
    public static ArrayList<ItemModel> getVideoGames() {
        return alVideoGames;
    }

    /**
     * Setter for video games items
     *
     * @param alVideoGames List of video games items
     */
    public static void setVideoGames(ArrayList<ItemModel> alVideoGames) {
        AmazonData.alVideoGames = alVideoGames;
    }

    /**
     * Getter for watches items
     *
     * @return List of watches items
     */
    public static ArrayList<ItemModel> getWatches() {
        return alWatches;
    }

    /**
     * Setter for watches items
     *
     * @param alWatches List of watches items
     */
    public static void setWatches(ArrayList<ItemModel> alWatches) {
        AmazonData.alWatches = alWatches;
    }
}
