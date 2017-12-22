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
        APPLIANCES("Appliances"),
        AUTOMOTIVE("Automotive"),
        BABY("Baby"),
        BEAUTY("Beauty"),
        BOOKS("Books"),
        DVD("DVD"),
        ELECTRONICS("Electronics"),
        GROCERY("Grocery"),
        HEALTH_AND_PERSONAL_CARE("HealthPersonalCare"),
        HOME_AND_GARDEN("HomeGarden"),
        JEWELRY("Jewelry"),
        KINDLE_STORE("KindleStore"),
        LAWN_AND_GARDEN("LawnAndGarden"),
        LUGGAGE_AND_BAGS("Luggage"),
        LUXURY_BEAUTY("LuxuryBeauty"),
        MUSIC("Music"),
        MUSICAL_INSTRUMENTS("MusicalInstruments"),
        OFFICE_PRODUCTS("OfficeProducts"),
        AMAZON_PANTRY("Pantry"),
        PC_HARDWARE("PCHardware"),
        PET_SUPPLIES("PetSupplies"),
        SHOES("Shoes"),
        SOFTWARE("Software"),
        SPORTING_GOODS("SportingGoods"),
        TOYS("Toys"),
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

    public enum ItemBrowseNodeId {

        ALL(""),
        APPAREL("1571272031"),
        APPLIANCES("5122349031"),
        AUTOMOTIVE("4772061031"),
        BABY("1571275031"),
        BEAUTY("1355017031"),
        BOOKS("976390031"),
        DVD("976417031"),
        ELECTRONICS("976420031"),
        GROCERY("2454179031"),
        HEALTH_AND_PERSONAL_CARE("1350385031"),
        HOME_AND_GARDEN("2454176031"),
        JEWELRY("1951049031"),
        KINDLE_STORE("1571278031"),
        LAWN_AND_GARDEN("2454175031"),
        LUGGAGE_AND_BAGS("2454170031"),
        LUXURY_BEAUTY("5311359031"),
        MUSIC("976446031"),
        MUSICAL_INSTRUMENTS("3677698031"),
        OFFICE_PRODUCTS("2454173031"),
        AMAZON_PANTRY("9574332031"),
        PC_HARDWARE("976393031"),
        PET_SUPPLIES("4740420031"),
        SHOES("1571284031"),
        SOFTWARE("976452031"),
        SPORTING_GOODS("1984444031"),
        TOYS("1350381031"),
        VIDEO_GAMES("976461031"),
        WATCHES("1350388031");

        private final String requestValue;

        ItemBrowseNodeId(final String requestValue) {
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
