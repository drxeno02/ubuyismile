package com.app.amazon.framework.enums;

/**
 * Created by leonard on 9/26/2017.
 */

public class Enum {

    /**
     * Enum used to select category for item query, e.g. electronics, apparel (CHABLEE)
     */
    public enum ItemCategoryChablee {

        RINGS("Rings"),
        NECKLACES("Necklaces"),
        GEMSTONE("Gemstone"),
        ROCKS("Rocks");

        private final String requestValue;

        ItemCategoryChablee(final String requestValue) {
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
     * Enum used to select category for item query, e.g. electronics, apparel (AMAZON)
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
     * Enum used to query item information (LOOKUP)
     * <p>Specifies the types of values to return. You can specify multiple response groups in
     * one request by separating them with commas</p>
     */
    public enum ResponseGroupItemLookup {

        ACCESSORIES("Accessories"),
        BROWSE_NODES("BrowseNodes"),
        EDITORIAL_REVIEW("EditorialReview"),
        IMAGES("Images"),
        ITEM_ATTRIBUTES("ItemAttributes"),
        ITEM_IDS("ItemIds"),
        LARGE("Large"),
        MEDIUM("Medium"),
        OFFER_FULL("OfferFull"),
        OFFERS("Offers"),
        OFFER_SUMMARY("OfferSummary"),
        PROMOTION_SUMMARY("PromotionSummary"),
        RELATED_ITEMS("RelatedItems"),
        REVIEWS("Reviews"),
        SALES_RANK("SalesRank"),
        SIMILARITIES("Similarities"),
        SMALL("Small"), // default
        TRACKS("Tracks"),
        VARIATION_IMAGES("VariationImages"),
        VARIATION_SUMMARY("VariationSummary"),
        VARIATIONS("Variations");

        private final String requestValue;

        ResponseGroupItemLookup(final String requestValue) {
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
     * Enum used to query item information (SEARCH)
     * <p>Specifies the types of values to return. You can specify multiple response groups in
     * one request by separating them with commas</p>
     */
    public enum ResponseGroupItemSearch {

        ACCESSORIES("Accessories"),
        ALTERNATIVE_VERSIONS("AlternativeVersions"),
        BROWSE_NODES_INFO("BrowseNodeInfo"),
        BROWSE_NODES("BrowseNodes"),
        CART("Cart"),
        CART_NEW_RELEASES("CartNewReleases"),
        CART_TOP_SELLERS("CartTopSellers"),
        CART_SIMILARITIES("CartSimilarities"),
        EDITORIAL_REVIEW("EditorialReview"),
        IMAGES("Images"),
        ITEM_ATTRIBUTES("ItemAttributes"),
        ITEM_IDS("ItemIds"),
        LARGE("Large"),
        MEDIUM("Medium"),
        MOST_GIFTED("MostGifted"),
        NEW_RELEASES("NewReleases"),
        OFFER_FULL("OfferFull"),
        OFFER_LISTINGS("OfferListings"),
        OFFERS("Offers"),
        OFFER_SUMMARY("OfferSummary"),
        PROMOTION_SUMMARY("PromotionSummary"),
        RELATED_ITEMS("RelatedItems"),
        REQUEST("Request"),
        REVIEWS("Reviews"),
        SALES_RANK("SalesRank"),
        SEARCH_BINS("SearchBins"),
        SIMILARITIES("Similarities"),
        SMALL("Small"), // default
        TOP_SELLERS("TopSellers"),
        TRACKS("Tracks"),
        VARIATION_MATRIX("VariationMatrix"),
        VARIATION_OFFERS("VariationOffers"),
        VARIATION_IMAGES("VariationImages"),
        VARIATION_SUMMARY("VariationSummary"),
        VARIATIONS("Variations");

        private final String requestValue;

        ResponseGroupItemSearch(final String requestValue) {
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
     * Enum used to query item information (BroseNodeLookup)
     * <p>Specifies the types of values to return. You can specify multiple response groups in
     * one request by separating them with commas</p>
     */
    public enum ResponseGroupItemBrowseNode {

        BROWSE_NODE_INFO("BrowseNodeInfo"),
        MOST_GIFTED("MostGifted"),
        NEW_RELEASES("NewReleases"),
        MOST_WISHED_FOR("MostWishedFor"),
        TOP_SELLERS("TopSellers");

        private final String requestValue;

        ResponseGroupItemBrowseNode(final String requestValue) {
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
     * A positive integer assigned by Amazon that uniquely identifies a product category
     */
    public enum ItemBrowseNodeId {

        ALL(""),
        APPLIANCES("2619526011"),
        ARTS_CRAFTS_SEWING("2617942011"),
        AUTOMOTIVE("15690151"),
        BABY("165797011"),
        BEAUTY("11055981"),
        BOOKS("1000"),
        COLLECTIBLES("4991426011"),
        CLOTHING_SHOES_JEWELRY("7141124011"),
        CLOTHING_SHOES_JEWELRY_BABY("7147444011"),
        CLOTHING_SHOES_JEWELRY_BOYS("7147443011"),
        CLOTHING_SHOES_JEWELRY_GIRLS("7147442011"),
        CLOTHING_SHOES_JEWELRY_MEN("7147441011"),
        CLOTHING_SHOES_JEWELRY_WOMEN("7147440011"),
        ELECTRONICS("493964"),
        GIFT_CARDS("2864120011"),
        GROCERY("16310211"),
        HANDMADE("11260433011"),
        HEALTH_AND_PERSONAL_CARE("3760931"),
        HOME_KITCHEN("1063498"),
        INDUSTRIAL_SCIENTIFIC("16310161"),
        KINDLE_STORE("133141011"),
        LAWN_AND_GARDEN("3238155011"),
        LUGGAGE_AND_BAGS("9479199011"),
        MAGAZINE_SUBSCRIPTIONS("599872"),
        APPS_GAMES("2350150011"),
        MOVIES("2625374011"),
        DIGITAL_MUSIC("624868011"),
        CDS_VINYL("301668"),
        MUSICAL_INSTRUMENTS("11965861"),
        OFFICE_PRODUCTS("1084128"),
        PC_HARDWARE("541966"),
        PET_SUPPLIES("2619534011"),
        SOFTWARE("409488"),
        SPORTING_GOODS("3375301"),
        TOOLS_HOME_IMPROVEMENT("468240"),
        TOYS("165795011"),
        AMAZON_INSTANT_VIDEO("2858778011"),
        VEHICLES("10677470011"),
        VIDEO_GAMES("11846801"),
        WINE("2983386011"),
        CELL_PHONE_ACCESSORIES("2335753011");

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
