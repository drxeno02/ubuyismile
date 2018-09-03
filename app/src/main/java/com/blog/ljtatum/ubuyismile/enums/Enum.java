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
     * Enum is used to set item type
     */
    public enum ItemType {
        SEARCH("Search"),
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
     * Enum is used to set item search category. The different type of searches fall into these
     * categories. This does not include what can be searched via auto search. This sets up the
     * filters for auto search
     */
    public enum SearchCategory {
        ALL("all"),
        ALL_GOOD_DEALS("all_good_deals"), // these are all items on sale
        BOOKS("books"),
        ELECTRONICS("electronics"),
        FOOD("food"),
        HEALTH_BEAUTY("health_beauty"),
        MOVIES("movies"),
        VIDEO_GAMES("video_games"),
        CHABLEE("chablee");

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
