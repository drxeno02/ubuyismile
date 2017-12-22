package com.blog.ljtatum.ubuyismile.model;

import com.app.framework.utilities.FrameworkUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemModel {

    @SerializedName("ASIN")
    public String asin;
    @SerializedName("DetailPageURL")
    public String detailPageURL;
    @SerializedName("ItemLinks")
    public ArrayList<ItemLink> itemLinks;
    @SerializedName("ImageSets")
    public ArrayList<ImageSet> imageSets;
    @SerializedName("ItemAttributes")
    public ItemAttributes itemAttributes;

    public ItemModel() {
        itemLinks = new ArrayList<>();
        imageSets = new ArrayList<>();
        itemAttributes = new ItemAttributes();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Item");
        stringBuilder.append("\nASIN : ").append(asin);
        stringBuilder.append("\nDetailPageURL : ").append(detailPageURL);
        stringBuilder.append("\nItemLinks : ").append(!FrameworkUtils.checkIfNull(itemLinks) ?
                itemLinks.toString() : null);
        stringBuilder.append("\nImagetSets : ").append(!FrameworkUtils.checkIfNull(imageSets) ?
                imageSets.toString() : null);
        stringBuilder.append("\nItemAttributes : ").append(!FrameworkUtils.checkIfNull(itemAttributes) ?
                itemAttributes.toString() : null);
        return stringBuilder.toString();
    }

    public class ItemLink {

        @SerializedName("Description")
        public String description;
        @SerializedName("URL")
        public String url;

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n - Description : ").append(description);
            stringBuilder.append("\n - URL : ").append(url);
            return stringBuilder.toString();
        }

    }

    public class ImageSet {

        @SerializedName("Category")
        public String category;
        @SerializedName("SwatchImage")
        public ItemImage swatchImage;
        @SerializedName("ThumbnailImage")
        public ItemImage thumbnailImage;
        @SerializedName("TinyImage")
        public ItemImage tinyImage;
        @SerializedName("SmallImage")
        public ItemImage smallImage;
        @SerializedName("MediumImage")
        public ItemImage mediumImage;
        @SerializedName("LargeImage")
        public ItemImage largeImage;

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n - Category : ").append(category);
            stringBuilder.append("\n - SwatchImage : ").append(!FrameworkUtils.checkIfNull(swatchImage) ?
                    swatchImage.toString() : null);
            stringBuilder.append("\n - ThumbnailImage : ").append(!FrameworkUtils.checkIfNull(thumbnailImage) ?
                    thumbnailImage.toString() : null);
            stringBuilder.append("\n - TinyImage : ").append(!FrameworkUtils.checkIfNull(tinyImage) ?
                    tinyImage.toString() : null);
            stringBuilder.append("\n - SmallImage : ").append(!FrameworkUtils.checkIfNull(smallImage) ?
                    smallImage.toString() : null);
            stringBuilder.append("\n - MediumImage : ").append(!FrameworkUtils.checkIfNull(mediumImage) ?
                    mediumImage.toString() : null);
            stringBuilder.append("\n - LargeImage : ").append(!FrameworkUtils.checkIfNull(largeImage) ?
                    largeImage.toString() : null);
            return stringBuilder.toString();
        }

    }

    public class ItemImage {

        @SerializedName("URL")
        public String url;
        @SerializedName("Height")
        public int height;
        @SerializedName("Width")
        public int width;

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n - URL : ").append(url);
            stringBuilder.append("\n - Height : ").append(height);
            stringBuilder.append("\n - Width : ").append(width);
            return stringBuilder.toString();
        }

    }

    public class ItemAttributes {

        @SerializedName("Author")
        public String author;
        @SerializedName("Binding")
        public String binding;
        @SerializedName("EAN")
        public String ean;
        @SerializedName("ISBN")
        public String isbn;
        @SerializedName("Title")
        public String title;
        @SerializedName("ListPrice")
        public ListPrice listPrice;
        @SerializedName("Languages")
        public ArrayList<Language> languages = new ArrayList<>();

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n - Author : ").append(author);
            stringBuilder.append("\n - Binding : ").append(binding);
            stringBuilder.append("\n - EAN : ").append(ean);
            stringBuilder.append("\n - ISBN : ").append(isbn);
            stringBuilder.append("\n - Title : ").append(title);
            stringBuilder.append("\n - Languages : ").append(!FrameworkUtils.checkIfNull(languages) ?
                    languages.toString() : null);
            return stringBuilder.toString();
        }

    }

    public class Language {

        @SerializedName("Name")
        public String name;
        @SerializedName("Type")
        public String type;

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n - List Price");
            stringBuilder.append("\n  - Name : ").append(name);
            stringBuilder.append("\n  - Type : ").append(type);
            return stringBuilder.toString();
        }
    }

    public class ListPrice {

        @SerializedName("Amount")
        public int amount;
        @SerializedName("CurrencyCode")
        public String currencyCode;
        @SerializedName("FormattedPrice")
        public String formattedPrice;

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n - List Price");
            stringBuilder.append("\n  - Amount : ").append(amount);
            stringBuilder.append("\n  - Currency Code : ").append(currencyCode);
            stringBuilder.append("\n  - Formatted Price : ").append(formattedPrice);
            return stringBuilder.toString();
        }
    }

}
