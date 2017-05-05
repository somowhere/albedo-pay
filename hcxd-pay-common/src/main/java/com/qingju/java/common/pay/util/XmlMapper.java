package com.qingju.java.common.pay.util;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lijie on 2017/4/28.
 */
public class XmlMapper {
    /**
     * conver simple xml to map
     * @param mingwen
     * @return
     */
    public static Map xmlToMap(String mingwen) {
        mingwen = mingwen.replaceAll("(?<=>)\\s+(?=<)", "");
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder;
        Document doc;
        Map dataMap = new HashMap();
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(new ByteArrayInputStream(mingwen.getBytes()));
            NodeList allNodes = doc.getFirstChild().getChildNodes();
            Node node;
            int i = 0;
            while (i < allNodes.getLength()) {
                node = allNodes.item(i);
                if (node instanceof Element) {
                    dataMap.put(node.getNodeName(), node.getTextContent());
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * conver deep xml to map
     * @param xml
     * @return
     */
    public static Map<String, Object> xmlToJson(String xml) {
        Map<String, Object> rtnMap = new HashMap<>();
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document doc = null;
        try {
            doc = builder.build(new StringReader(xml));
            // 得到根节点
            org.jdom.Element root = doc.getRootElement();
            String rootName = root.getName();
            rtnMap.put("root.name", rootName);
            // 调用递归函数，得到所有最底层元素的名称和值，加入map中
            convert(root, rtnMap, rootName);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rtnMap;
    }

    public static void convert(org.jdom.Element e, Map<String, Object> map, String lastname) {
        if (e.getAttributes().size() > 0) {
            Iterator it_attr = e.getAttributes().iterator();
            while (it_attr.hasNext()) {
                org.jdom.Attribute attribute = (org.jdom.Attribute) it_attr.next();
                String attrname = attribute.getName();
                String attrvalue = e.getAttributeValue(attrname);
                // map.put( attrname, attrvalue);
                map.put(lastname + "." + attrname, attrvalue); // key 根据根节点 进行生成
            }
        }
        List children = e.getChildren();
        Iterator it = children.iterator();
        while (it.hasNext()) {
            org.jdom.Element child = (org.jdom.Element) it.next();
            /* String name = lastname + "." + child.getName(); */
            String name = child.getName();
            // 如果有子节点，则递归调用
            if (child.getChildren().size() > 0) {
                convert(child, map, lastname + "." + child.getName());
            } else {
                // 如果没有子节点，则把值加入map
                map.put(name, child.getText());
                // 如果该节点有属性，则把所有的属性值也加入map
                if (child.getAttributes().size() > 0) {
                    Iterator attr = child.getAttributes().iterator();
                    while (attr.hasNext()) {
                        org.jdom.Attribute attribute = (org.jdom.Attribute) attr.next();
                        String attrname = attribute.getName();
                        String attrvalue = child.getAttributeValue(attrname);
                        map.put(lastname + "." + child.getName() + "." + attrname, attrvalue);
                    }
                }
            }
        }
    }


    public static String map2SimplXml(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer("<xml>\n");
        if (map == null || map.isEmpty()) {
            return "";
        }
        map.forEach((k, v) -> sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">"));
        sb.append("</xml>");
        return sb.toString();
    }
}
