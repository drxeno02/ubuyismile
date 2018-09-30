package com.app.amazon.framework.utils;

import android.util.Log;

import com.app.amazon.framework.enums.Enum;
import com.app.amazon.framework.model.ItemId;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leonard on 9/26/2017.
 */

public class AmazonProductAdvertisingApiRequestBuilder {
    private static final String TAG = AmazonProductAdvertisingApiRequestBuilder.class.getSimpleName();

    private static final String SERVICE = "AWSECommerceService";

    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String ROUTE = "/onca/xml";

    /**
     * Creates an {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID
     *
     * @param itemId     The ID of the item to get the information for
     * @param itemIdType The ID type of the given ID
     * @return A new {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID
     */
    public static AdvertisingApiItemLookupRequestBuilder forItemLookup(final String itemId, final ItemId.Type itemIdType) {
        return forItemLookup(ItemId.create(itemId, itemIdType));
    }

    /**
     * Creates an {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID
     *
     * @param itemId The ID of the item to get the information for
     * @return A new {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID
     */
    private static AdvertisingApiItemLookupRequestBuilder forItemLookup(final ItemId itemId) {
        return new AdvertisingApiItemLookupRequestBuilder(itemId);
    }

    /**
     * Creates an {@link AdvertisingApiItemSearchRequestBuilder} for creating an ItemSearch request for the item matching the given keywords
     *
     * @param keywords The keywords that shall be used to search for items
     * @return A new {@link AdvertisingApiItemSearchRequestBuilder} for creating an ItemSearch request for the item matching the given keywords
     */
    public static AdvertisingApiItemSearchRequestBuilder forItemSearch(final String keywords) {
        return new AdvertisingApiItemSearchRequestBuilder(keywords);
    }

    public static AdvertisingApiItemBrowseNodeRequestBuilder forItemBrowse(final Enum.ItemBrowseNodeId browseNodeId) {
        return new AdvertisingApiItemBrowseNodeRequestBuilder(browseNodeId);
    }

    /**
     * Method is used to create response group request (LOOKUP)
     *
     * @param responseGroup List of item information to query
     * @return ResponseGroup e.g. type of values to return
     */
    private static String createResponseGroupRequestLookup(final List<Enum.ResponseGroupItemLookup> responseGroup) {
        // add item attributes to response group if none was selected
        if (responseGroup.size() == 0) {
            responseGroup.add(Enum.ResponseGroupItemLookup.SMALL);
        }

        StringBuilder responseGroupRequestValue = new StringBuilder();
        for (int i = 0; i < responseGroup.size(); i++) {
            if (i != 0) {
                responseGroupRequestValue.append(",");
            }
            responseGroupRequestValue.append(responseGroup.get(i).toString());
        }
        return responseGroupRequestValue.toString();
    }

    /**
     * Method is used to create response group request (SEARCH)
     *
     * @param responseGroup List of item information to query
     * @return ResponseGroup e.g. type of values to return
     */
    private static String createResponseGroupRequestSearch(final List<Enum.ResponseGroupItemSearch> responseGroup) {
        // add item attributes to response group if none was selected
        if (responseGroup.size() == 0) {
            responseGroup.add(Enum.ResponseGroupItemSearch.SMALL);
        }

        StringBuilder responseGroupRequestValue = new StringBuilder();
        for (int i = 0; i < responseGroup.size(); i++) {
            if (i != 0) {
                responseGroupRequestValue.append(",");
            }
            responseGroupRequestValue.append(responseGroup.get(i).toString());
        }
        return responseGroupRequestValue.toString();
    }

    /**
     * Method is used to create response group request (BROWSE)
     *
     * @param responseGroup List of item information to query
     * @return ResponseGroup e.g. type of values to return
     */
    private static String createResponseGroupRequestBrowse(final List<Enum.ResponseGroupItemBrowseNode> responseGroup) {
        // add item attributes to response group if none was selected
        if (responseGroup.size() == 0) {
            responseGroup.add(Enum.ResponseGroupItemBrowseNode.BROWSE_NODE_INFO);
        }

        StringBuilder responseGroupRequestValue = new StringBuilder();
        for (int i = 0; i < responseGroup.size(); i++) {
            if (i != 0) {
                responseGroupRequestValue.append(",");
            }
            responseGroupRequestValue.append(responseGroup.get(i).toString());
        }
        return responseGroupRequestValue.toString();
    }

    /**
     * A builder that simplifies the creation of URLs for ItemSearch requests
     */
    public static final class AdvertisingApiItemSearchRequestBuilder {

        private static final String OPERATION = "ItemSearch";

        private final List<Enum.ResponseGroupItemSearch> alResponseGroup = new ArrayList<>();
        private final String keywords;

        private Enum.ItemCondition itemCondition = Enum.ItemCondition.ALL; // default
        private Enum.ItemCategory itemCategory = Enum.ItemCategory.ALL; // default
        private int maximumPrice = 0; // default
        private int minimumPrice = 0; // default


        private AdvertisingApiItemSearchRequestBuilder(final String keywords) {
            this.keywords = keywords;
        }

        /**
         * Sets the {@link Enum.ItemCondition} to filter the result of the returned items
         *
         * @param itemCondition The {@link Enum.ItemCondition} to filter the result of the returned items
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}
         */
        public AdvertisingApiItemSearchRequestBuilder filterByCondition(final Enum.ItemCondition itemCondition) {
            this.itemCondition = itemCondition;
            return this;
        }

        /**
         * Adds the given {@link Enum.ResponseGroupItemSearch} to the response group
         *
         * @param itemInformation The {@link Enum.ResponseGroupItemSearch} that shall be added to the response group
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}
         */
        public AdvertisingApiItemSearchRequestBuilder includeInformationAbout(final Enum.ResponseGroupItemSearch itemInformation) {
            alResponseGroup.add(itemInformation);
            return this;
        }

        /**
         * Specifies the {@link Enum.ItemCategory} that will be searched
         *
         * @param itemCategory The {@link Enum.ItemCategory} that will be searched
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}
         */
        public AdvertisingApiItemSearchRequestBuilder filterByCategory(final Enum.ItemCategory itemCategory) {
            this.itemCategory = itemCategory;
            return this;
        }

        /**
         * Specifies the maximum item price in the response. Prices appear in the lowest currency denomination
         * For example, 3241 is $32.41
         *
         * @param maximumPrice The maximum item price in the lowest currency denomination
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}
         */
        public AdvertisingApiItemSearchRequestBuilder filterByMaximumPrice(final int maximumPrice) {
            this.maximumPrice = maximumPrice;
            return this;
        }

        /**
         * Specifies the minimum item price in the response. Prices appear in the lowest currency denomination
         * For example, 3241 is $32.41.
         *
         * @param minimumPrice The minimum item price in the lowest currency denomination
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}
         */
        public AdvertisingApiItemSearchRequestBuilder filterByMinimumPrice(final int minimumPrice) {
            this.minimumPrice = minimumPrice;
            return this;
        }

        /**
         * Creates the signed request http-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @return The created signed request url
         */
        public String createRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                          final AmazonWebServiceAuthentication authentication) {
            // return signed request url
            return createRequestUrlFor(serviceLocation, authentication, HTTP_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @return The created signed request url
         */
        public String createSecureRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                                final AmazonWebServiceAuthentication authentication) {
            // return signed request url
            return createRequestUrlFor(serviceLocation, authentication, HTTPS_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @param protocol        HTTPS protocol
         * @return The created signed request url
         */
        private String createRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                           final AmazonWebServiceAuthentication authentication, final String protocol) {

            final Map<String, String> requestParams = new LinkedHashMap<>();
            requestParams.put("AWSAccessKeyId", authentication.getAwsAccessKey());
            requestParams.put("AssociateTag", authentication.getAssociateTag());
            requestParams.put("Condition", itemCondition.toString());
            requestParams.put("SearchIndex", itemCategory.toString());
            requestParams.put("Keywords", keywords);
            requestParams.put("Operation", OPERATION);
            requestParams.put("Service", SERVICE);
            requestParams.put("ResponseGroup", createResponseGroupRequestSearch(alResponseGroup));
            requestParams.put("Timestamp", authentication.getTimestamp());
            if (maximumPrice != -1) {
                requestParams.put("MaximumPrice", "" + maximumPrice);
            }
            if (minimumPrice != -1) {
                requestParams.put("MinimumPrice", "" + minimumPrice);
            }
            Log.d(TAG, "created url = " + RequestUrlUtils.createSignedRequestUrl(protocol,
                    serviceLocation.toString(), ROUTE, requestParams, authentication.getAwsSecretKey()));
            return RequestUrlUtils.createSignedRequestUrl(protocol, serviceLocation.toString(), ROUTE,
                    requestParams, authentication.getAwsSecretKey());
        }
    }

    /**
     * A builder that simplifies the creation of URLs for ItemLookup requests
     */
    public static final class AdvertisingApiItemLookupRequestBuilder {

        private static final String OPERATION = "ItemLookup";

        private final List<Enum.ResponseGroupItemLookup> responseGroup = new ArrayList<>();
        private final ItemId itemId;

        private Enum.ItemCondition itemCondition = Enum.ItemCondition.ALL;

        /**
         * Method is used to set item id for lookup
         *
         * @param itemId The ID of the item to get the information for
         */
        private AdvertisingApiItemLookupRequestBuilder(final ItemId itemId) {
            this.itemId = itemId;
        }

        /**
         * Adds the given {@link Enum.ResponseGroupItemLookup} to the response group
         *
         * @param itemInformation The {@link Enum.ResponseGroupItemLookup} that shall be added to the response group
         * @return The current {@link AdvertisingApiItemLookupRequestBuilder}
         */
        public AdvertisingApiItemLookupRequestBuilder includeInformationAbout(final Enum.ResponseGroupItemLookup itemInformation) {
            responseGroup.add(itemInformation);
            return this;
        }

        /**
         * Sets the {@link Enum.ItemCondition} to filter the result of the returned items
         *
         * @param itemCondition The {@link Enum.ItemCondition} to filter the result of the returned items
         * @return The current {@link AdvertisingApiItemLookupRequestBuilder}
         */
        public AdvertisingApiItemLookupRequestBuilder filterByCondition(final Enum.ItemCondition itemCondition) {
            this.itemCondition = itemCondition;
            return this;
        }

        /**
         * Creates the signed request http-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @return The created signed request url
         */
        public String createRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                          final AmazonWebServiceAuthentication authentication) {
            // return signed request url
            return createRequestUrlFor(serviceLocation, authentication, HTTP_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @return The created signed request url
         */
        public String createSecureRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                                final AmazonWebServiceAuthentication authentication) {
            // return signed request url
            return createRequestUrlFor(serviceLocation, authentication, HTTPS_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @param protocol        HTTP protocol
         * @return The created signed request url
         */
        private String createRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                           final AmazonWebServiceAuthentication authentication, final String protocol) {

            final Map<String, String> requestParams = new LinkedHashMap<>();
            requestParams.put("Service", SERVICE);
            requestParams.put("Operation", OPERATION);
            requestParams.put("ResponseGroup", createResponseGroupRequestLookup(responseGroup));
            requestParams.put("SearchIndex", "All");
            requestParams.put("IdType", itemId.getType().toString());
            requestParams.put("ItemId", itemId.getValue());
            requestParams.put("AWSAccessKeyId", authentication.getAwsAccessKey());
            requestParams.put("AssociateTag", authentication.getAssociateTag());
            requestParams.put("Timestamp", authentication.getTimestamp());

            Log.d(TAG, "created url = " + RequestUrlUtils.createSignedRequestUrl(protocol,
                    serviceLocation.toString(), ROUTE, requestParams, authentication.getAwsSecretKey()));
            return RequestUrlUtils.createSignedRequestUrl(protocol, serviceLocation.toString(), ROUTE,
                    requestParams, authentication.getAwsSecretKey());
        }
    }

    /**
     * A builder that simplifies the creation of URLs for ItemLookup requests
     */
    public static final class AdvertisingApiItemBrowseNodeRequestBuilder {

        private static final String OPERATION = "BrowseNodeLookup";

        private final List<Enum.ResponseGroupItemBrowseNode> responseGroup = new ArrayList<>();
        private final Enum.ItemBrowseNodeId browseNodeId;

        /**
         * Method is used to set item id for lookup
         *
         * @param browseNodeId A positive integer that uniquely identifies a product group,
         *                     such as Literature & Fiction: (17), Medicine: (13996),
         *                     and Mystery & Thrillers: (18)
         */
        private AdvertisingApiItemBrowseNodeRequestBuilder(final Enum.ItemBrowseNodeId browseNodeId) {
            this.browseNodeId = browseNodeId;
        }

        /**
         * Adds the given {@link Enum.ResponseGroupItemBrowseNode} to the response group
         *
         * @param itemInformation The {@link Enum.ResponseGroupItemBrowseNode} that shall be added to the response group
         * @return The current {@link AdvertisingApiItemLookupRequestBuilder}
         */
        public AdvertisingApiItemBrowseNodeRequestBuilder includeInformationAbout(final Enum.ResponseGroupItemBrowseNode itemInformation) {
            responseGroup.add(itemInformation);
            return this;
        }

        /**
         * Creates the signed request http-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @return The created signed request url
         */
        public String createRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                          final AmazonWebServiceAuthentication authentication) {
            // return signed request url
            return createRequestUrlFor(serviceLocation, authentication, HTTP_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @return The created signed request url
         */
        public String createSecureRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                                final AmazonWebServiceAuthentication authentication) {
            // return signed request url
            return createRequestUrlFor(serviceLocation, authentication, HTTPS_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}
         *
         * @param serviceLocation The location of the Amazon service that shall be used
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used
         * @param protocol        HTTP protocol
         * @return The created signed request url
         */
        private String createRequestUrlFor(final Enum.AmazonWebServiceLocation serviceLocation,
                                           final AmazonWebServiceAuthentication authentication, final String protocol) {

            final Map<String, String> requestParams = new LinkedHashMap<>();
            requestParams.put("Service", SERVICE);
            requestParams.put("Operation", OPERATION);
            requestParams.put("ResponseGroup", createResponseGroupRequestBrowse(responseGroup));
            requestParams.put("SearchIndex", "All");
            requestParams.put("BrowseNodeId", browseNodeId.toString());
            requestParams.put("AWSAccessKeyId", authentication.getAwsAccessKey());
            requestParams.put("AssociateTag", authentication.getAssociateTag());
            requestParams.put("Timestamp", authentication.getTimestamp());

            Log.d(TAG, "created url = " + RequestUrlUtils.createSignedRequestUrl(protocol,
                    serviceLocation.toString(), ROUTE, requestParams, authentication.getAwsSecretKey()));
            return RequestUrlUtils.createSignedRequestUrl(protocol, serviceLocation.toString(), ROUTE,
                    requestParams, authentication.getAwsSecretKey());
        }
    }
}
