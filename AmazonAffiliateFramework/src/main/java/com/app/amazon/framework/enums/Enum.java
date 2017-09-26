package com.app.amazon.framework.enums;

/**
 * Created by leonard on 9/26/2017.
 */

public class Enum {

    /**
     * Enum used to select category for item query, e.g. electronics, apparal
     */
    public enum ItemCategory {

        ALL("All"),
        APPAREL("Apparel"),
        AUTOMOTIVE("Automotive"),
        BABY("Baby"),
        BEAUTY("Beauty"),
        BOOKS("Books"),
        CLASSICAL("Classical"),
        DVD("DVD"),
        ELECTRONICS("Electronics"),
        FOREIGN_BOOKS("ForeignBooks"),
        GROCERY("Grocery"),
        HEALTH_AND_PERSONAL_CARE("HealthPersonalCare"),
        HOME_AND_GARDEN("HomeGarden"),
        JEWELRY("Jewelry"),
        KINDLE_STORE("KindleStore"),
        KITCHEN("Kitchen"),
        LIGHTING("Lighting"),
        MAGAZINES("Magazines"),
        MARKETPLACE("Marketplace"),
        MP3_DOWNLOADS("MP3Downloads"),
        MUSIC("Music"),
        MUSICAL_INSTRUMENTS("MusicalInstruments"),
        MUSIC_TRACKS("MusicTracks"),
        OFFICE_PRODUCTS("OfficeProducts"),
        OUTDOOR_LIVING("OutdoorLiving"),
        OUTLET("Outlet"),
        PC_HARDWARE("PCHardware"),
        PHOTO("Photo"),
        SHOES("Shoes"),
        SOFTWARE("Software"),
        SOFTWARE_VIDEO_GAMES("SoftwareVideoGames"),
        SPORTING_GOODS("SportingGoods"),
        TOOLS("Tools"),
        TOYS("Toys"),
        VHS("VHS"),
        VIDEO("Video"),
        VIDEO_GAMES("VideoGames"),
        WATCHES("Watches");

        private final String requestValue;

        ItemCategory(final String requestValue) {
            this.requestValue = requestValue;
        }

        /**
         * Gives the value used in the URL request that represents this item category
         *
         * @return The value used in the URL request that represents this item category
         */
        public String getRequestValue() {
            return requestValue;
        }
    }

    /**
     * Enum used to select condition for item query, e.g. new, used, refurbished, ect
     */
    public enum ItemCondition {

        ALL("All"),
        NEW("New"),
        USED("Used"),
        REFURBISHED("Refurbished"),
        COLLECTIBLE("Collectible");

        private final String requestValue;

        ItemCondition(final String requestValue) {
            this.requestValue = requestValue;
        }

        /**
         * Gives the value used in the URL request that represents this item condition
         *
         * @return The value used in the URL request that represents this item condition
         */
        public String getRequestValue() {
            return requestValue;
        }
    }

    /**
     * Enum used to query item information
     */
    public enum ItemInformation {

        ACCESSORIES("Accessories"),
        ALTERNATIVE_VERSIONS("AlternativeVersions"),
        ATTRIBUTES("ItemAttributes"),
        BROWSE_NODES("BrowseNodes"),
        EDITORIAL_REVIEW("EditorialReview"),
        IMAGES("Images"),
        OFFER_FULL("OfferFull"),
        OFFER_SUMMARY("OfferSummary"),
        OFFERS("Offers"),
        REVIEWS("Reviews"),
        SALES_RANK("SalesRank"),
        SIMILARITIES("Similarities"),
        TRACKS("Tracks"),
        VARIATION_IMAGES("VariationImages"),
        VARIATION_MATRIX("VariationMatrix"),
        VARIATION_SUMMARY("VariationSummary"),
        VARIATIONS("Variations");

        private final String requestValue;

        ItemInformation(final String requestValue) {
            this.requestValue = requestValue;
        }

        /**
         * Gives the value used in the URL request that represents this item information
         *
         * @return The value used in the URL request that represents this item information
         */
        public String getRequestValue() {
            return requestValue;
        }
    }

    /**
     * Enum used to retrieve available Amazon services based on location
     */
    public enum AmazonWebServiceLocation {

        CA("webservices.amazon.ca"),
        CN("webservices.amazon.cn"),
        CO_JP("webservices.amazon.co.jp"),
        CO_UK("webservices.amazon.co.uk"),
        COM("webservices.amazon.com"),
        COM_BR("webservices.amazon.com.br"),
        COM_MX("webservices.amazon.com.mx"),
        DE("webservices.amazon.de"),
        ES("webservices.amazon.es"),
        FR("webservices.amazon.fr"),
        IT("webservices.amazon.it");

        private final String webServiceUrl;

        AmazonWebServiceLocation(final String webServiceUrl) {
            this.webServiceUrl = webServiceUrl;
        }

        /**
         * Gives the listing of the available Amazon web services for different locations
         *
         * @return The listing of the available Amazon web services for different locations
         */
        public String getWebServiceUrl() {
            return webServiceUrl;
        }

    }

}
