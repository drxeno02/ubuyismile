package com.blog.ljtatum.ubuyismile.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.amazon.framework.enums.Enum;
import com.app.amazon.framework.model.ItemId;
import com.app.amazon.framework.utils.AmazonProductAdvertisingApiRequestBuilder;
import com.app.amazon.framework.utils.AmazonWebServiceAuthentication;
import com.app.amazon.framework.utils.XMLParserUtils;
import com.app.framework.utilities.DeviceUtils;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.fragments.AboutFragment;
import com.blog.ljtatum.ubuyismile.fragments.PrivacyFragment;
import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.blog.ljtatum.ubuyismile.model.ItemModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private Activity mActivity;
    private AsyncTask mAsyncTask;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        // initialize views and listeners
        initializeViews();
        initializeHandlers();
        initializeListeners();

        AmazonWebServiceAuthentication authentication = AmazonWebServiceAuthentication.create(
                getResources().getString(R.string.amazon_tag),
                getResources().getString(R.string.amazon_access_key),
                getResources().getString(R.string.amazon_secret_key));

        // 076243631X
//        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder
//                .forItemLookup("B00W0TD6Y6", ItemId.Type.ISBN)
//                .includeInformationAbout(Enum.ItemInformation.ATTRIBUTES)
//                .includeInformationAbout(Enum.ItemInformation.IMAGES)
//                .includeInformationAbout(Enum.ItemInformation.EDITORIAL_REVIEW)
//                .includeInformationAbout(Enum.ItemInformation.REVIEWS)
//                .includeInformationAbout(Enum.ItemInformation.OFFERS)
//                .includeInformationAbout(Enum.ItemInformation.OFFER_FULL)
//                .includeInformationAbout(Enum.ItemInformation.OFFER_SUMMARY)
//                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, authentication);

        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder
                .forItemSearch("All")
                .includeInformationAbout(Enum.ItemInformation.ATTRIBUTES)
                .includeInformationAbout(Enum.ItemInformation.IMAGES)
                .includeInformationAbout(Enum.ItemInformation.EDITORIAL_REVIEW)
                .includeInformationAbout(Enum.ItemInformation.REVIEWS)
                .includeInformationAbout(Enum.ItemInformation.OFFERS)
                .includeInformationAbout(Enum.ItemInformation.OFFER_FULL)
                .includeInformationAbout(Enum.ItemInformation.OFFER_SUMMARY)
                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, authentication);

        mAsyncTask = new RequestTask().execute(requestUrl);
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        mContext = MainActivity.this;
        mActivity = MainActivity.this;

        // drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (FrameworkUtils.checkIfNull(getTopFragment())) {
                    // show keyboard
                    DeviceUtils.showKeyboard(mContext);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // hide keyboard
                DeviceUtils.hideKeyboard(mContext, getWindow().getDecorView().getWindowToken());
            }
        };
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        // navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {

    }

    /**
     * Method is used to enable/disable drawer
     *
     * @param isEnabled True to enable drawer interaction, otherwise disable interaction
     */
    public void toggleDrawerState(boolean isEnabled) {
        if (!FrameworkUtils.checkIfNull(mDrawerLayout)) {
            if (isEnabled) {
                // only unlock (enable) drawer interaction if it is disabled
                if (mDrawerLayout.getDrawerLockMode(GravityCompat.START) != DrawerLayout.LOCK_MODE_UNLOCKED) {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            } else {
                // only allow disabling of drawer interaction if the drawer is closed
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_browse:

                break;
            case R.id.nav_search:

                break;
            case R.id.nav_settings:

                break;
            case R.id.nav_all_good_deals:

                break;
            case R.id.nav_quick_search_books:

                break;
            case R.id.nav_quick_search_electronics:

                break;
            case R.id.nav_quick_search_food:

                break;
            case R.id.nav_quick_search_health_beauty:

                break;
            case R.id.nav_quick_search_movies:

                break;
            case R.id.nav_quick_search_video_games:

                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.nav_privacy:
                fragment = new PrivacyFragment();
                break;
            default:
                break;
        }
        // add fragment
        if (!FrameworkUtils.checkIfNull(fragment)) {
            addFragment(fragment);
        }

        // close drawer after selection
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    /**
     * embedded class to make HTTP requests to retrieve the default referee reward credit amount
     */
    @SuppressWarnings("deprecation")
    private static class RequestTask extends AsyncTask<String, String, String> {

        @NonNull
        @Override
        protected String doInBackground(String... urls) {
            String resResult = "";
            if (!FrameworkUtils.checkIfNull(urls)) {
                try {
                    URL url = new URL(urls[0]);
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
                    resResult = buffer.readLine();
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return resResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//            List<String> responseList = splitEqually(result, 650);
//            for (int i = 0; i < responseList.size(); i++) {
//                Logger.v("TEST", "" + responseList.get(i));
//            }

            ItemModel SAXParse = SAXParse(result);
            Logger.e("TEST", "SAX Parse: " + SAXParse.toString());

            List<String> responseList = splitEqually(SAXParse.toString(), 500);
            for (int i = 0; i < responseList.size(); i++) {
                Logger.v("TEST", "" + responseList.get(i));
            }
        }
    }

    public static List<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    private static ItemModel SAXParse(String result) {
        ItemModel item = new ItemModel();

        if (!FrameworkUtils.isStringEmpty(result)) {
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                SAXParserHandler userHandler = new SAXParserHandler();
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

    private static class SAXParserHandler extends DefaultHandler {

        private ItemModel item;
        private ItemModel.ItemLink tempItemLink;
        private ItemModel.ImageSet tempImageSet;
        private ItemModel.ItemImage tempItemImage;
        private ItemModel.ItemAttributes tempItemAttributes;
        private ItemModel.ListPrice tempListPrice;
        private ItemModel.Language tempLanguage;
        private StringBuilder stringBuilder;
        private boolean isAppendCharacters;

        private SAXParserHandler() {
            item = null;
            stringBuilder = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("Item")) {
                item = new ItemModel();
            } else if (qName.equalsIgnoreCase("ItemLink")) {
                tempItemLink = new ItemModel().new ItemLink();
            } else if (qName.equalsIgnoreCase("ImageSet")) {
                tempImageSet = new ItemModel().new ImageSet();
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
                tempItemImage = new ItemModel().new ItemImage();
            } else if (qName.equalsIgnoreCase("ItemAttributes")) {
                tempItemAttributes = new ItemModel().new ItemAttributes();
            } else if (qName.equalsIgnoreCase("ListPrice")) {
                tempListPrice = new ItemModel().new ListPrice();
            } else if (qName.equalsIgnoreCase("Languages")) {
                tempLanguage = new ItemModel().new Language();
            }

            // append characters when parsing a URL. This is because DefaultHandler splits strings
            // when escape characters (i.e. '&') are present
            isAppendCharacters = qName.toLowerCase().contains("url");
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            Logger.e("TEST", "qName= " + qName + " // stringBuilder.toString()= " + stringBuilder.toString());

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
}