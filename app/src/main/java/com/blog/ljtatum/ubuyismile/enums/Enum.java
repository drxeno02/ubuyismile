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
}
