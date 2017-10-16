package com.blog.ljtatum.ubuyismile.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.app.amazon.framework.enums.Enum;
import com.app.amazon.framework.model.ItemId;
import com.app.amazon.framework.utils.AmazonProductAdvertisingApiRequestBuilder;
import com.app.amazon.framework.utils.AmazonWebServiceAuthentication;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.logger.Logger;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends BaseActivity {

    private AsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AmazonWebServiceAuthentication authentication = AmazonWebServiceAuthentication.create("drxeno02-20", "AKIAJVWJJT4YYSPVVDWQ", "s/l4sdka9svv6difLhgYEnBQQRdNofgSVG9TSwWI");


         final String requestUrl = AmazonProductAdvertisingApiRequestBuilder
                .forItemLookup("076243631X", ItemId.Type.ISBN)
                .createRequestUrlFor(Enum.AmazonWebServiceLocation.COM, authentication);

        Log.e("TEST", "request url= " + requestUrl);
        mAsyncTask = new RequestTask().execute(requestUrl);

    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";

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

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            Document doc = null;
            try {
                db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(result));
                 doc = db.parse(is);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!FrameworkUtils.checkIfNull(doc)) {
                NodeList node_item_lookup_res = doc.getElementsByTagName("ItemLookupResponse");

                for (int i = 0; i < node_item_lookup_res.getLength(); i++) {
                    Element element_items = (Element) node_item_lookup_res.item(i);

                    NodeList node_item = element_items.getElementsByTagName("Items");
                    Element element_item = (Element) node_item.item(i);

                    NodeList node_asin = element_item.getElementsByTagName("ASIN");
                    Element element_asin = (Element) node_asin.item(i);
                    Logger.e("TEST", "asin= " + getCharacterDataFromElement(element_asin));

                    NodeList node_detail_page_url = element_item.getElementsByTagName("DetailPageURL");
                    Element element_detail_page_url = (Element) node_detail_page_url.item(i);
                    Logger.e("TEST", "detail page url= " + getCharacterDataFromElement(element_detail_page_url));

                    NodeList node_attributes = element_item.getElementsByTagName("ItemAttributes");
                    Element element_attributes = (Element) node_attributes.item(i);
                    NodeList node_author = element_attributes.getElementsByTagName("Author");
                    Element element_author = (Element) node_author.item(i);
                    Logger.e("TEST", "author= " + getCharacterDataFromElement(element_author));

                }
            }
        }
    }


}
