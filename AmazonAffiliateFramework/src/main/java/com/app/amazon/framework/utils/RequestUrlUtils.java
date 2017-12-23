package com.app.amazon.framework.utils;

import android.util.Log;

import com.app.framework.utilities.FrameworkUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by leonard on 9/26/2017.
 */

public class RequestUrlUtils {
    private static final String REQ = "req-";

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String UTF8_CHARSET = "UTF-8";

    /**
     * Constructor
     */
    private RequestUrlUtils() {
        //no instance
    }

    /**
     * Method is used to create signed request URL
     *
     * @param protocol
     * @param amazonServiceUrl
     * @param route
     * @param requestParams
     * @param secretKey
     * @return
     */
    protected static String createSignedRequestUrl(final String protocol, final String amazonServiceUrl, final String route,
                                                   final Map<String, String> requestParams, final String secretKey) {

        final String canonicalizeRequestParams = RequestUrlUtils.canonicalizeParameters(requestParams);
        final String signature = RequestUrlUtils.createSignature(amazonServiceUrl, route, canonicalizeRequestParams, secretKey);
        Log.d(REQ, (protocol + amazonServiceUrl + route + "?" + canonicalizeRequestParams + "&Signature=" + signature));
        return protocol + amazonServiceUrl + route + "?" + canonicalizeRequestParams + "&Signature=" + signature;
    }

    /**
     * Method is used to create signed request URL
     *
     * @param amazonServiceUrl
     * @param route
     * @param requestPairs
     * @param secretKey
     * @return
     */
    private static String createSignature(final String amazonServiceUrl, final String route,
                                          final String requestPairs, final String secretKey) {
        // create the string upon which the signature is calculated
        final String toSign = "GET" + "\n"
                + amazonServiceUrl + "\n"
                + route + "\n"
                + requestPairs;

        // get the signature
        final String hmac = hmac(toSign, secretKey);
        return percentEncodeRfc3986(hmac);
    }

    private static String hmac(final String stringToSign, final String awsSecretKey) {
        String signature;
        final byte[] data;
        final byte[] rawHmac;
        try {
            data = stringToSign.getBytes(UTF8_CHARSET);
            final Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            final byte[] secretKeyBytes = awsSecretKey.getBytes(UTF8_CHARSET);
            final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);
            rawHmac = mac.doFinal(data);
            final Base64.Encoder encoder = Base64.getEncoder();
            signature = new String(encoder.encode(rawHmac));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(UTF8_CHARSET + " is not supported!", e);
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(HMAC_SHA256_ALGORITHM + " is not supported!", e);
        } catch (final InvalidKeyException e) {
            throw new RuntimeException("Private Key is Invalid!", e);
        }
        return signature;
    }

    private static String canonicalizeParameters(final Map<String, String> parameters) {
        // The parameters need to be processed in lexicographical order, so we'll
        // use a TreeMap implementation for that.
        final TreeMap<String, String> sortedParamMap = new TreeMap<>(parameters);

        if (sortedParamMap.isEmpty()) {
            return "";
        }

        final StringBuilder buffer = new StringBuilder();
        final Iterator<Map.Entry<String, String>> paramIterator = sortedParamMap.entrySet().iterator();

        while (paramIterator.hasNext()) {
            final Map.Entry<String, String> keyValuePair = paramIterator.next();
            buffer.append(percentEncodeRfc3986(keyValuePair.getKey()));
            buffer.append("=");
            buffer.append(percentEncodeRfc3986(keyValuePair.getValue()));
            if (paramIterator.hasNext()) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    private static String percentEncodeRfc3986(final String s) {
        if (!FrameworkUtils.isStringEmpty(s)) {
            String out;
            try {
                out = URLEncoder.encode(s, UTF8_CHARSET)
                        .replace("+", "%20")
                        .replace("*", "%2A")
                        .replace("%7E", "~");
            } catch (final UnsupportedEncodingException e) {
                out = s;
            }
            return out;
        }
        return "";
    }
}
