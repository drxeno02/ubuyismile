package com.blog.ljtatum.ubuyismile.enums;

/**
 * Created by LJTat on 12/31/2017.
 */

public class Enum {

    /**
     * Enum used to categorize items
     */
    public enum ItemLabel {
        NONE("None"),
        NEW("New"),
        SALE("Sale"),
        FEATURED("Featured"),
        MOST_POPULAR("Most Popular"),
        ALMOST_GONE("Almost Gone"),
        TOP_SELLER("Top Seller"),
        SUPER_HOT("Super Hot"),
        LEONARD_FAVORITE("Leonard's Favorite");

        private final String requestValue;

        ItemLabel(final String requestValue) {
            this.requestValue = requestValue;
        }

        @Override
        public String toString() {
            return requestValue;
        }
    }

    /**
     * Method is used to set item type
     */
    public enum ItemType {
        BROWSE("Browse"),
        AMAZON("Amazon"),
        CHABLEE("Chablee");

        private final String itemType;

        ItemType(final String itemType) {
            this.itemType = itemType;
        }

        @Override
        public String toString() {
            return itemType;
        }
    }

    /**
     * Method is used to set item type
     */
    public enum SearchCategory {
        ALL("all"),
        ALL_GOOD_DEALS("all_good_deals"), // these are all items on sale
        BOOKS("books"),
        ELECTRONICS("electronics"),
        FOOD("food"),
        HEALTH_BEAUTY("health_beauty"),
        MOVIES("movies"),
        VIDEO_GAMES("video_games");

        private final String searchCategory;

        SearchCategory(final String searchCategory) {
            this.searchCategory = searchCategory;
        }

        @Override
        public String toString() {
            return searchCategory;
        }
    }
}
