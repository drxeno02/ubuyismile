package com.blog.ljtatum.ubuyismile.saxparse;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.model.AmazonResponseModel;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by LJTat on 12/22/2017.
 */

public class SAXParseHandler extends DefaultHandler {

    private AmazonResponseModel item;
    private AmazonResponseModel.ItemLink tempItemLink;
    private AmazonResponseModel.ImageSet tempImageSet;
    private AmazonResponseModel.ItemImage tempItemImage;
    private AmazonResponseModel.ItemAttributes tempItemAttributes;
    private AmazonResponseModel.ListPrice tempListPrice;
    private AmazonResponseModel.Language tempLanguage;
    private StringBuilder stringBuilder;
    private boolean isAppendCharacters;

    private SAXParseHandler() {
        item = null;
        stringBuilder = new StringBuilder();
    }

    /**
     * Method is used to retrieve an AmazonResponseModel {@link AmazonResponseModel}
     *
     * @param result AWS response
     * @return AmazonResponseModel {@link AmazonResponseModel}
     */
    public static AmazonResponseModel SAXParse(String result) {
        AmazonResponseModel item = new AmazonResponseModel();

        if (!FrameworkUtils.isStringEmpty(result)) {
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                SAXParseHandler userHandler = new SAXParseHandler();
                saxParser.parse(new InputSource(new StringReader(result)), userHandler);
                item = userHandler.item;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Item")) {
            item = new AmazonResponseModel();
        } else if (qName.equalsIgnoreCase("ItemLink")) {
            tempItemLink = new AmazonResponseModel().new ItemLink();
        } else if (qName.equalsIgnoreCase("ImageSet")) {
            tempImageSet = new AmazonResponseModel().new ImageSet();
            if (!FrameworkUtils.checkIfNull(attributes)) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    if (attributes.getQName(i).equalsIgnoreCase("Category")) {
                        tempImageSet.category = attributes.getValue(i);
                        break;
                    }
                }
            }
        } else if (!FrameworkUtils.checkIfNull(tempImageSet) &&
                (qName.equalsIgnoreCase("SwatchImage") ||
                        qName.equalsIgnoreCase("ThumbnailImage") ||
                        qName.equalsIgnoreCase("TinyImage") ||
                        qName.equalsIgnoreCase("SmallImage") ||
                        qName.equalsIgnoreCase("MediumImage") ||
                        qName.equalsIgnoreCase("LargeImage"))) {
            tempItemImage = new AmazonResponseModel().new ItemImage();
        } else if (qName.equalsIgnoreCase("ItemAttributes")) {
            tempItemAttributes = new AmazonResponseModel().new ItemAttributes();
        } else if (qName.equalsIgnoreCase("ListPrice")) {
            tempListPrice = new AmazonResponseModel().new ListPrice();
        } else if (qName.equalsIgnoreCase("Languages")) {
            tempLanguage = new AmazonResponseModel().new Language();
        }

        // append characters when parsing a URL. This is because DefaultHandler splits strings
        // when escape characters (i.e. '&') are present
        isAppendCharacters = qName.toLowerCase().contains("url");
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!FrameworkUtils.checkIfNull(item)) {
            if (qName.equalsIgnoreCase("ASIN")) {
                item.asin = stringBuilder.toString();
            } else if (qName.equalsIgnoreCase("DetailPageURL")) {
                item.detailPageURL = stringBuilder.toString();
            } else if (qName.equalsIgnoreCase("ItemLink")) {
                item.itemLinks.add(tempItemLink);
                tempItemLink = null;
            } else if (!FrameworkUtils.checkIfNull(tempItemLink)) {
                if (qName.equalsIgnoreCase("Description")) {
                    tempItemLink.description = stringBuilder.toString();
                } else if (qName.equalsIgnoreCase("URL")) {
                    tempItemLink.url = stringBuilder.toString();
                }
            } else if (qName.equalsIgnoreCase("ImageSet")) {
                item.imageSets.add(tempImageSet);
                tempImageSet = null;
            } else if (!FrameworkUtils.checkIfNull(tempImageSet) &&
                    (qName.equalsIgnoreCase("SwatchImage") ||
                            qName.equalsIgnoreCase("ThumbnailImage") ||
                            qName.equalsIgnoreCase("TinyImage") ||
                            qName.equalsIgnoreCase("SmallImage") ||
                            qName.equalsIgnoreCase("MediumImage") ||
                            qName.equalsIgnoreCase("LargeImage"))) {
                if (qName.equalsIgnoreCase("SwatchImage")) {
                    tempImageSet.swatchImage = tempItemImage;
                } else if (qName.equalsIgnoreCase("ThumbnailImage")) {
                    tempImageSet.thumbnailImage = tempItemImage;
                } else if (qName.equalsIgnoreCase("TinyImage")) {
                    tempImageSet.tinyImage = tempItemImage;
                } else if (qName.equalsIgnoreCase("SmallImage")) {
                    tempImageSet.smallImage = tempItemImage;
                } else if (qName.equalsIgnoreCase("MediumImage")) {
                    tempImageSet.mediumImage = tempItemImage;
                } else if (qName.equalsIgnoreCase("LargeImage")) {
                    tempImageSet.largeImage = tempItemImage;
                }
                tempItemImage = null;
            } else if (!FrameworkUtils.checkIfNull(tempItemImage)) {
                if (qName.equalsIgnoreCase("URL")) {
                    tempItemImage.url = stringBuilder.toString();
                } else if (qName.equalsIgnoreCase("Height")) {
                    tempItemImage.height = Integer.parseInt(stringBuilder.toString());
                } else if (qName.equalsIgnoreCase("Width")) {
                    tempItemImage.width = Integer.parseInt(stringBuilder.toString());
                }
            } else if (qName.equalsIgnoreCase("ItemAttributes")) {
                item.itemAttributes = tempItemAttributes;
                tempItemAttributes = null;
            } else if (!FrameworkUtils.checkIfNull(tempItemAttributes)) {
                if (qName.equalsIgnoreCase("Author")) {
                    tempItemAttributes.author = stringBuilder.toString();
                } else if (qName.equalsIgnoreCase("Binding")) {
                    tempItemAttributes.binding = stringBuilder.toString();
                } else if (qName.equalsIgnoreCase("EAN")) {
                    tempItemAttributes.ean = stringBuilder.toString();
                } else if (qName.equalsIgnoreCase("ISBN")) {
                    tempItemAttributes.isbn = stringBuilder.toString();
                } else if (qName.equalsIgnoreCase("Title")) {
                    tempItemAttributes.title = stringBuilder.toString();
                } else if (qName.equalsIgnoreCase("ListPrice")) {
                    tempItemAttributes.listPrice = tempListPrice;
                    tempListPrice = null;
                } else if (!FrameworkUtils.checkIfNull(tempListPrice)) {
                    if (qName.equalsIgnoreCase("Amount")) {
                        tempListPrice.amount = Integer.parseInt(stringBuilder.toString());
                    } else if (qName.equalsIgnoreCase("CurrencyCode")) {
                        tempListPrice.currencyCode = stringBuilder.toString();
                    } else if (qName.equalsIgnoreCase("FormattedPrice")) {
                        tempListPrice.formattedPrice = stringBuilder.toString();
                    }
                } else if (qName.equalsIgnoreCase("Language")) {
                    tempItemAttributes.languages.add(tempLanguage);
                    tempImageSet = null;
                } else if (!FrameworkUtils.checkIfNull(tempLanguage)) {
                    if (qName.equalsIgnoreCase("Name")) {
                        tempLanguage.name = stringBuilder.toString();
                    } else if (qName.equalsIgnoreCase("Type")) {
                        tempLanguage.type = stringBuilder.toString();
                    }
                }
            }
        }
        stringBuilder.setLength(0);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (!isAppendCharacters) {
            stringBuilder.setLength(0);
        }
        stringBuilder.append(ch, start, length);
    }
}
