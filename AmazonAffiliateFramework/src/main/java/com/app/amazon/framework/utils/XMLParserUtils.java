package com.app.amazon.framework.utils;

import com.app.framework.utilities.FrameworkUtils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by jeremy on 10/20/17.
 */

public class XMLParserUtils {

    public static Node getNode(String tagName, NodeList nodes) {
        if (!FrameworkUtils.checkIfNull(nodes)) {
            for (int x = 0; x < nodes.getLength(); x++) {
                Node node = nodes.item(x);
                if (!FrameworkUtils.checkIfNull(node) &&
                        node.getNodeName().equalsIgnoreCase(tagName)) {
                    return node;
                }
            }
        }
        return null;
    }

    public static String getNodeValue(Node node) {
        if (!FrameworkUtils.checkIfNull(node)) {
            NodeList childNodes = node.getChildNodes();
            if (!FrameworkUtils.checkIfNull(childNodes)) {
                for (int x = 0; x < childNodes.getLength(); x++) {
                    Node data = childNodes.item(x);
                    if (!FrameworkUtils.checkIfNull(data) && data.getNodeType() == Node.TEXT_NODE) {
                        return data.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public static String getNodeValue(String tagName, NodeList nodes) {
        if (!FrameworkUtils.checkIfNull(nodes)) {
            for (int x = 0; x < nodes.getLength(); x++) {
                Node node = nodes.item(x);
                if (!FrameworkUtils.checkIfNull(node) &&
                        node.getNodeName().equalsIgnoreCase(tagName)) {
                    NodeList childNodes = node.getChildNodes();
                    if (!FrameworkUtils.checkIfNull(childNodes)) {
                        for (int y = 0; y < childNodes.getLength(); y++) {
                            Node data = childNodes.item(y);
                            if (!FrameworkUtils.checkIfNull(data) &&
                                    data.getNodeType() == Node.TEXT_NODE) {
                                return data.getNodeValue();
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public static String getNodeAttr(String attrName, Node node) {
        if (!FrameworkUtils.checkIfNull(node)) {
            NamedNodeMap attrs = node.getAttributes();
            if (!FrameworkUtils.checkIfNull(attrs)) {
                for (int y = 0; y < attrs.getLength(); y++) {
                    Node attr = attrs.item(y);
                    if (!FrameworkUtils.checkIfNull(attr) &&
                            attr.getNodeName().equalsIgnoreCase(attrName)) {
                        return attr.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public static String getNodeAttr(String tagName, String attrName, NodeList nodes) {
        if (!FrameworkUtils.checkIfNull(nodes)) {
            for (int x = 0; x < nodes.getLength(); x++) {
                Node node = nodes.item(x);
                if (!FrameworkUtils.checkIfNull(node) &&
                        node.getNodeName().equalsIgnoreCase(tagName)) {
                    NodeList childNodes = node.getChildNodes();
                    if (!FrameworkUtils.checkIfNull(childNodes)) {
                        for (int y = 0; y < childNodes.getLength(); y++) {
                            Node data = childNodes.item(y);
                            if (!FrameworkUtils.checkIfNull(data) &&
                                    data.getNodeType() == Node.ATTRIBUTE_NODE &&
                                    data.getNodeName().equalsIgnoreCase(attrName)) {
                                return data.getNodeValue();
                            }
                        }
                    }
                }
            }
        }
        return "";
    }
}
