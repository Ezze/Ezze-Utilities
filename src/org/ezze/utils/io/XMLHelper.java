package org.ezze.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Collects a set of static methods to read and write XML files.
 *
 * @author Dmitriy Pushkov
 * @version 0.0.6
 */
public class XMLHelper {

    /**
     * Reads specified XML file.
     *
     * @param xmlFileName
     *      XML file's name
     * @return
     *      Instance of {@link org.w3c.dom.Document} on success, null otherwise
     */
    public static Document readXMLDocument(String xmlFileName) {

        return readXMLDocument(xmlFileName, null, null);
    }

    /**
     * Reads specified XML file.
     *
     * @param xmlFileName
     *      XML file's name
     * @param createEmptyDocument
     *      If this flag is set to true then empty {@link org.w3c.dom.Document} will be created
     * @return
     *      Instance of {@link org.w3c.dom.Document} on success, null otherwise
     */
    public static Document readXMLDocument(String xmlFileName, Boolean createEmptyDocument) {

        return readXMLDocument(xmlFileName, createEmptyDocument, null);
    }

    /**
     * Reads specified XML file.
     *
     * @param xmlFileName
     *      XML file's name
     * @param createEmptyDocument
     *      If this flag is set to true then empty {@link org.w3c.dom.Document} will be created
     * @param rootTagName
     *      Name of document's root element node
     * @return
     *      Instance of {@link org.w3c.dom.Document} on success, null otherwise
     */
    public static Document readXMLDocument(String xmlFileName, Boolean createEmptyDocument, String rootTagName) {

        // Checking input parameters
        if (xmlFileName == null)
            return null;

        if (createEmptyDocument == null)
            createEmptyDocument = false;

        // Retrieving instance of Document Builder Factory and creating new Document Builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {

            db = dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {

            return null;
        }

        if (db == null)
            return null;

        // Checking XML file's existance
        File xmlFile = new File(xmlFileName);
        if (!xmlFile.exists() || !xmlFile.isFile()) {

            if (createEmptyDocument)
            {
                Document xmlDocument = db.newDocument();
                if (rootTagName != null && !rootTagName.isEmpty())
                    xmlDocument.appendChild(xmlDocument.createElement(rootTagName));
                
                return xmlDocument;
            }

            return null;
        }

        // Parsing XML file
        Document xmlDocument = null;
        
        try {
            
            xmlDocument = db.parse(xmlFile);
        }
        catch (SAXException ex) {
            
        }
        catch (IOException ex) {
            
        }

        // Return parsing results
        return xmlDocument;
    }

    /**
     * Reads XML DOM from input stream.
     *
     * @param inputStream
     *      XML input stream
     * @return
     *      Instance of {@link org.w3c.dom.Document} on success, null otherwise
     */
    public static Document readXMLDocument(InputStream inputStream) {

        if (inputStream == null)
            return null;

        // Retrieving instance of Document Builder Factory and creating new Document Builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {

            db = dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {

            return null;
        }

        if (db == null)
            return null;

        // Parsing XML from input stream
        Document xmlDocument = null;
        try {

            xmlDocument = db.parse(inputStream);
        }
        catch (SAXException ex) {

        }
        catch (IOException ex) {
            
        }

        // Return parsing results
        return xmlDocument;
    }

    /**
     * Reads XML DOM from input source.
     *
     * @param inputSource
     *      XML input source
     * @return
     *      Instance of {@link org.w3c.dom.Document} on success, null otherwise
     */
    public static Document readXMLDocument(InputSource inputSource) {

        if (inputSource == null)
            return null;

        // Retrieving instance of Document Builder Factory and creating new Document Builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {

            db = dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {

            return null;
        }

        if (db == null)
            return null;

        // Parsing XML from input stream
        Document xmlDocument = null;
        try {

            xmlDocument = db.parse(inputSource);
        }
        catch (SAXException ex) {

        }
        catch (IOException ex) {

        }

        // Return parsing results
        return xmlDocument;
    }

    /**
     * Writes specified XML document to specified XML file.
     *
     * @param xmlDocument
     *      XML document to save
     * @param xmlFileName
     *      XML destination file
     * @return
     *      {@code true} if XML has been successfully written, {@code false} otherwise
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean, java.lang.String)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean, java.lang.String, java.lang.Integer)
     */
    public static boolean writeXMLDocument(Document xmlDocument, String xmlFileName) {

        return writeXMLDocument(xmlDocument, xmlFileName, null, null, null);
    }

    /**
     * Writes specified XML document to specified XML file.
     *
     * @param xmlDocument
     *      XML document to save
     * @param xmlFileName
     *      XML destination file
     * @param useTemporaryFile
     *      Write to temporary XML file first or not (true by default)
     * @return
     *      {@code true} if XML has been successfully written, {@code false} otherwise
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean, java.lang.String)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean, java.lang.String, java.lang.Integer)
     */
    public static boolean writeXMLDocument(Document xmlDocument, String xmlFileName, Boolean useTemporaryFile) {

        return writeXMLDocument(xmlDocument, xmlFileName, useTemporaryFile, null, null);
    }

    /**
     * Writes specified XML document to specified XML file.
     *
     * @param xmlDocument
     *      XML document to save
     * @param xmlFileName
     *      XML destination file
     * @param useTemporaryFile
     *      Write to temporary XML file first or not (true by default)
     * @param charset
     *      XML charset encoding (UTF-8 by default)
     * @return
     *      {@code true} if XML has been successfully written, {@code false} otherwise
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean, java.lang.String, java.lang.Integer)
     */
    public static boolean writeXMLDocument(Document xmlDocument, String xmlFileName, Boolean useTemporaryFile, String charset) {

        return writeXMLDocument(xmlDocument, xmlFileName, useTemporaryFile, charset, null);
    }

    /**
     * Writes specified XML document to specified XML file.
     * 
     * @param xmlDocument
     *      XML document to save
     * @param xmlFileName
     *      XML destination file
     * @param useTemporaryFile
     *      Write to temporary XML file first or not (true by default)
     * @param charset
     *      XML charset encoding (UTF-8 by default)
     * @param indentSize
     *      Tabs indent size in space characters (4 by default)
     * @return
     *      {@code true} if XML has been successfully written, {@code false} otherwise
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean)
     * @see #writeXMLDocument(org.w3c.dom.Document, java.lang.String, java.lang.Boolean, java.lang.String)
     */
    public static boolean writeXMLDocument(Document xmlDocument, String xmlFileName, Boolean useTemporaryFile, String charset, Integer indentSize) {

        // Checking input parameters
        if (xmlDocument == null || xmlFileName == null)
            return false;

        if (useTemporaryFile == null)
            useTemporaryFile = true;

        if (charset == null)
            charset = "UTF-8";

        if (indentSize == null)
            indentSize = new Integer(4);

        // Retrieving document's root element
        Element rootElement = xmlDocument.getDocumentElement();
        if (rootElement == null)
            return false;

        // Cleaning indent text nodes of root element
        if (rootElement.hasChildNodes()) {

            Node childNode = rootElement.getFirstChild();
            do {

                Node nextChildNode = childNode.getNextSibling();
                if (childNode.getNodeType() == Node.TEXT_NODE)
                    rootElement.removeChild(childNode);
                childNode = nextChildNode;
            }
            while (childNode != null);
        }

        // Normalizing XML document
        xmlDocument.normalize();

        // Saving XML file
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute("indent-number", indentSize);       // setting tabs' size in space characters
        Transformer t = null;

        try {

            t = tf.newTransformer();
        }
        catch (TransformerConfigurationException ex) {

            return false;
        }

        if (t == null)
            return false;

        t.setOutputProperty(OutputKeys.INDENT, "yes");          // requiring tabs (indent) usage
        DOMSource domSource = new DOMSource(xmlDocument);

        String outputFileName = xmlFileName + (useTemporaryFile ? "~" : "");

        // Creating new output stream for temporary XML file
        FileOutputStream outputStream = null;
        try {

            outputStream = new FileOutputStream(outputFileName);
        }
        catch (FileNotFoundException ex) {

            return false;
        }

        if (outputStream == null)
            return false;
        
        StreamResult streamResult = null;
        try {

            // Creating resulting stream with UTF-8 charset
            streamResult = new StreamResult(new OutputStreamWriter(outputStream, charset));
        }
        catch (UnsupportedEncodingException ex) {

            return false;
        }

        if (streamResult == null)
            return false;

        try {

            // Writing temporary XML
            t.transform(domSource, streamResult);
        }
        catch (TransformerException ex) {

            return false;
        }

        try {

            // Closing output stream
            outputStream.close();
            outputStream = null;
        }
        catch (IOException ex) {

            return false;
        }

        if (!useTemporaryFile)
            return true;

        // Temporary file has been successfully saved here, replacing the original one
        boolean isTemporaryXMLRenamed = false;

        File xmlFile = new File(xmlFileName);
        File temporaryXMLFile = new File(outputFileName);

        // Removing backed up XML file if it's present
        File backupXMLFile = new File((xmlFileName.toLowerCase().endsWith(".xml") ?
                xmlFileName.substring(0, xmlFileName.length() - 4) : xmlFileName) + ".backup.xml");
        if (backupXMLFile.exists() && backupXMLFile.isFile())
            backupXMLFile.delete();

        // Checking original XML file's existance
        if (xmlFile.exists() && xmlFile.isFile()) {

            // Backing up current xml file
            if (xmlFile.renameTo(backupXMLFile)) {

                // Trying to rename temporary xml file to original one
                isTemporaryXMLRenamed = temporaryXMLFile.renameTo(xmlFile);
                if (!isTemporaryXMLRenamed) {

                    // Restoring backed up xml file
                    backupXMLFile.renameTo(xmlFile);
                }

                // Removing backup
                backupXMLFile.delete();
            }
        }
        else {

            // Renaming temporary XML file to original one
            isTemporaryXMLRenamed = temporaryXMLFile.renameTo(xmlFile);
        }

        return isTemporaryXMLRenamed;
    }

    /**
     * Retrieves top element of XML document.
     *
     * @param xmlDocument
     *      XML document's instance
     * @return
     *      Instance of top XML element of {@code null}
     */
    public static Element getDocumentElement(Document xmlDocument) {

        if (xmlDocument == null)
            return null;

        return xmlDocument.getDocumentElement();
    }

    /**
     * Retrieves children count of specified XML element node.
     *
     * @param xmlElement
     *      DOM element node
     * @param childElementName
     *      Tag name of children element nodes
     * @return
     *      Count of children
     */
    public static int getChildrenCount(Element xmlElement, String childElementName) {

        if (xmlElement == null || childElementName == null)
            return 0;

        NodeList xmlChildrenList = xmlElement.getElementsByTagName(childElementName);
        return xmlChildrenList.getLength();
    }

    /**
     * Retrieves XML element node's first child element.
     * 
     * @param xmlElement
     *      DOM element node
     * @return
     *      First child element or {@code null} if not child is found
     */
    public static Element getChildElement(Element xmlElement) {

        return getChildElement(xmlElement, 0);
    }

    /**
     * Retrieves XML element node's child element with specified {@code childElementIndex}.
     * 
     * @param xmlElement
     *      DOM element node
     * @param childElementIndex
     *      Index of child element to retrieve
     * @return
     *      Child DOM element node or {@code null} if no child is found
     */
    public static Element getChildElement(Element xmlElement, int childElementIndex) {

        if (xmlElement == null)
            return null;
        
        NodeList xmlChildrenList = xmlElement.getChildNodes();
        if (childElementIndex < 0 || childElementIndex >= xmlChildrenList.getLength())
            return null;

        int elementNodesCounter = 0;
        int nodesCounter = 0;
        while (nodesCounter < xmlChildrenList.getLength()) {

            Node xmlNode = xmlChildrenList.item(0);
            if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
                
                if (childElementIndex == elementNodesCounter)
                    return (Element)xmlNode;

                elementNodesCounter++;
            }
            
            nodesCounter++;
        }

        return null;
    }

    /**
     * Retrieves first child element node with tag name {@code childElementName} of specified element node.
     * 
     * @param xmlElement
     *      DOM element node
     * @param childElementName
     *      Tag name of child element to retrieve
     * @return
     *      Child DOM element node or {@code null} if no child is found
     */
    public static Element getChildElement(Element xmlElement, String childElementName) {

        return getChildElement(xmlElement, childElementName, 0);
    }

    /**
     * Retrieves child element node with tag name {@code childElementName}
     * and order index {@code childElementIndex} of specified element node.
     *
     * @param xmlElement
     *      DOM element node
     * @param childElementName
     *      Tag name of child element to retrieve
     * @param childElementIndex
     *      Order index of child element to retrieve
     * @return
     *      Child DOM element node or {@code null} if no child is found
     */
    public static Element getChildElement(Element xmlElement, String childElementName, int childElementIndex) {

        if (xmlElement == null || childElementName == null)
            return null;

        NodeList xmlChildrenList = xmlElement.getElementsByTagName(childElementName);
        if (childElementIndex < 0 || childElementIndex >= xmlChildrenList.getLength())
            return null;

        return (Element)xmlChildrenList.item(childElementIndex);
    }

    /**
     * Retrieves element node's child element node with tag name {@code childElementName}
     * and value {@code attributeValue} of specified attribute {@code attributeName}.
     *
     * @param xmlElement
     *      DOM element node
     * @param childElementName
     *      Tag name of child DOM element node to retrieve
     * @param attributeName
     *      Child attribute's name
     * @param attributeValue
     *      Child attribute's value
     * @return
     *      Child DOM element node or {@code null} if no child is found
     */
    public static Element getChildElementWithAttribute(Element xmlElement, String childElementName, String attributeName, String attributeValue) {

        if (xmlElement == null || childElementName == null)
            return null;

        NodeList xmlChildrenList = xmlElement.getElementsByTagName(childElementName);
        
        int childIndex = 0;
        while (childIndex < xmlChildrenList.getLength()) {

            Element xmlChildElement = (Element)xmlChildrenList.item(childIndex);
            if (xmlChildElement.hasAttribute(attributeName) && xmlChildElement.getAttribute(attributeName).equals(attributeValue))
                return xmlChildElement;
            childIndex++;
        }
        
        return null;
    }

        /**
     * Appends child element node to XML document.
     *
     * @param xmlDocument
     *      Instance of XML document
     * @param childElementName
     *      New child element node's tag name
     * @return
     *      Appended child element node
     */
    public static Element addChildElement(Document xmlDocument, String childElementName) {

        return addChildElement(xmlDocument, getDocumentElement(xmlDocument), childElementName);
    }

    /**
     * Appends child element node to existing DOM element node.
     *
     * @param xmlDocument
     *      Instance of XML document used to create new element node
     * @param xmlElement
     *      Existing DOM element node
     * @param childElementName
     *      New child element node's tag name
     * @return
     *      Appended child element node
     */
    public static Element addChildElement(Document xmlDocument, Element xmlElement, String childElementName) {

        if (xmlDocument == null || xmlElement == null || childElementName == null)
            return null;

        Element childElement = xmlDocument.createElement(childElementName);
        return (Element)xmlElement.appendChild(childElement);
    }

    /**
     * Retrieves DOM element node's text value.
     * 
     * @param xmlElement
     *      DOM element node
     * @return
     *      Text value or {@code null} if DOM element doesn't exist
     */
    public static String getElementText(Element xmlElement) {

        if (xmlElement == null)
            return null;

        return xmlElement.getTextContent();
    }

    /**
     * Retrieves DOM element node's text value.
     * 
     * @param xmlElement
     *      DOM element node
     * @param defaultValue
     *      Default value to return in the case of DOM element doesn't exist
     * @return
     *      Text value
     */
    public static String getElementText(Element xmlElement, String defaultValue) {

        String text = getElementText(xmlElement);
        return text != null ? text : defaultValue;
    }

    /**
     * Parses DOM element node's text value to intValue.
     *
     * @param xmlElement
     *      DOM element node
     * @return
     *      Parsed intValue of text value or {@code null}
     *      if DOM element doesn't exist or text value cannot be parsed
     */
    public static Integer getElementInteger(Element xmlElement) {

        String text = getElementText(xmlElement);
        if (text == null)
            return null;

        try {

            return Integer.parseInt(text);
        }
        catch (NumberFormatException ex) {

        }

        return null;
    }

    /**
     * Parses DOM element node's text value to intValue.
     * 
     * @param xmlElement
     *      DOM element node
     * @param defaultValue
     *      Default value to return in the case of DOM element doesn't exist
     *      or text value cannot be parsed
     * @return
     *      Parsed intValue of text value or {@code null}
     *      if DOM element doesn't exist of text value cannot be parsed
     */
    public static Integer getElementInteger(Element xmlElement, Integer defaultValue) {

        Integer integerValue = getElementInteger(xmlElement);
        return integerValue != null ? integerValue : defaultValue;
    }

    /**
     * Parses DOM element node's text value to long.
     *
     * @param xmlElement
     *      DOM element node
     * @return
     *      Parsed long of text value or {@code null}
     *      if DOM element doesn't exist or text value cannot be parsed
     */
    public static Long getElementLong(Element xmlElement) {

        String text = getElementText(xmlElement);
        if (text == null)
            return null;

        try {

            return Long.parseLong(text);
        }
        catch (NumberFormatException ex) {

        }

        return null;
    }

    /**
     * Parses DOM element node's text value to long.
     *
     * @param xmlElement
     *      DOM element node
     * @param defaultValue
     *      Default value to return in the case of DOM element doesn't exist
     *      or text value cannot be parsed
     * @return
     *      Parsed long of text value or {@code null}
     *      if DOM element doesn't exist or text value cannot be parsed
     */
    public static Long getElementLong(Element xmlElement, Long defaultValue) {

        Long longValue = getElementLong(xmlElement);
        return longValue != null ? longValue : defaultValue;
    }

    /**
     * Parses DOM element node's text value to double.
     *
     * @param xmlElement
     *      DOM element node
     * @return
     *      Parsed double of text value or {@code null}
     *      if DOM element doesn't exist or text value cannot be parsed
     */
    public static Double getElementDouble(Element xmlElement) {

        String text = getElementText(xmlElement);
        if (text == null)
            return null;

        try {

            return Double.parseDouble(text);
        }
        catch (NumberFormatException ex) {

        }

        return null;
    }

    /**
     * Parses DOM element node's text value to double.
     *
     * @param xmlElement
     *      DOM element node
     * @param defaultValue
     *      Default value to return in the case of DOM element doesn't exist
     *      or text value cannot be parsed
     * @return
     *      Parsed double of text value or {@code null}
     *      if DOM element doesn't exist or text value cannot be parsed
     */
    public static Double getElementDouble(Element xmlElement, Double defaultValue) {

        Double doubleValue = getElementDouble(xmlElement);
        return doubleValue != null ? doubleValue: defaultValue;
    }

    /**
     * Parses DOM element node's text value to boolean.
     *
     * @param xmlElement
     *      DOM element node
     * @return
     *      Parsed boolean of text value or {@code null}
     *      if DOM element doesn't exist or text value cannot be parsed
     */
    public static Boolean getElementBoolean(Element xmlElement) {

        String text = getElementText(xmlElement);
        if (text == null)
            return null;

        text = text.toLowerCase();
        if (text.equals("true") || text.equals("yes") || text.equals("1"))
            return Boolean.TRUE;
        else if (text.equals("false") || text.equals("no") || text.equals("0"))
            return Boolean.FALSE;

        try {

            return Boolean.parseBoolean(text);
        }
        catch (Exception ex) {

        }

        return null;
    }

    /**
     * Parses DOM element node's text value to boolen.
     * 
     * @param xmlElement
     *      DOM element node
     * @param defaultValue
     *      Default value to return in the case of DOM element doesn't exist
     *      or text value cannot be parsed
     * @return
     *      Parsed boolean of text value or {@code null}
     *      if DOM element doesn't exist or text value cannot be parsed
     */
    public static Boolean getElementBoolean(Element xmlElement, Boolean defaultValue) {

        Boolean booleanValue = getElementBoolean(xmlElement);
        return booleanValue != null ? booleanValue : defaultValue;
    }

    /**
     * Retrieves DOM element node's specified attribute's value.
     * 
     * @param xmlElement
     *      DOM element node
     * @param attributeName
     *      Attribute's name
     * @return
     *      Attribute's value or empty string if attribute is not set
     */
    public static String getElementAttribute(Element xmlElement, String attributeName) {

        if (xmlElement == null || attributeName == null)
            return null;

        return xmlElement.getAttribute(attributeName);
    }

    /**
     * Retrieves DOM element node's specified attribute's value.
     *
     * @param xmlElement
     *      DOM element node
     * @param attributeName
     *      Attribute's name
     * @param defaultValue
     *      Attribute's default value returned in the case of attribute cannot be retrieved
     * @return
     *      Attribute's value or empty string if attribute is not set
     */
    public static String getElementAttribute(Element xmlElement, String attributeName, String defaultValue) {

        String attributeValue = getElementAttribute(xmlElement, attributeName);
        return attributeValue != null ? attributeValue : defaultValue;
    }
    
    /**
     * Retrieves DOM element node's specified attribute's integer value.
     * 
     * @param xmlElement
     *      DOM element node
     * @param attributeName
     *      Attribute's name
     * @return 
     *      Attribute's integer value
     */
    public static Integer getElementAttributeInteger(Element xmlElement, String attributeName) {
        
        String attributeValue = getElementAttribute(xmlElement, attributeName);
        if (attributeValue == null)
            return null;
        
        try {
            
            return Integer.parseInt(attributeValue);
        }
        catch (Exception ex) {
            
        }
        
        return null;
    }
    
    /**
     * Retrieves DOM element node's specified attribute's integer value.
     *
     * @param xmlElement
     *      DOM element node
     * @param attributeName
     *      Attribute's name
     * @param defaultValue
     *      Attribute's default value returned in the case of attribute cannot be retrieved
     * @return
     *      Attribute's integer value
     */
    public static Integer getElementAttributeInteger(Element xmlElement, String attributeName, Integer defaultValue) {

        Integer attributeValue = getElementAttributeInteger(xmlElement, attributeName);
        return attributeValue != null ? attributeValue : defaultValue;
    }

    /**
     * Retrieves DOM element node's specified attribute's boolean value.
     *
     * @param xmlElement
     *      DOM element node
     * @param attributeName
     *      Attribute's name
     * @return
     *      Attribute's boolean value
     */
    public static Boolean getElementAttributeBoolean(Element xmlElement, String attributeName) {

        String attributeValue = getElementAttribute(xmlElement, attributeName);
        if (attributeValue == null)
            return null;

        try {

            return Boolean.parseBoolean(attributeValue);
        }
        catch (Exception ex) {

        }

        return null;
    }

    /**
     * Retrieves DOM element node's specified attribute's boolean value.
     *
     * @param xmlElement
     *      DOM element node
     * @param attributeName
     *      Attribute's name
     * @param defaultValue
     *      Attribute's default value returned in the case of attribute cannot be retrieved
     * @return
     *      Attribute's boolean value
     */
    public static Boolean getElementAttributeBoolean(Element xmlElement, String attributeName, Boolean defaultValue) {

        Boolean attributeValue = getElementAttributeBoolean(xmlElement, attributeName);
        return attributeValue != null ? attributeValue : defaultValue;
    }

    /**
     * Sets DOM element node's text value.
     * 
     * @param xmlElement
     *      DOM element node
     * @param text
     *      Text value
     */
    public static void setElementText(Element xmlElement, Object text) {

        if (xmlElement == null || text == null)
            return;

        xmlElement.setTextContent(text.toString());
    }

    /**
     * Sets DOM element node's specified attribute's value.
     * 
     * @param xmlElement
     *      DOM element node
     * @param attributeName
     *      Attribute's name
     * @param attributeValue
     *      Attribute's value
     */
    public static void setElementAttribute(Element xmlElement, String attributeName, Object attributeValue) {

        if (xmlElement == null || attributeName == null || attributeValue == null)
            return;

        xmlElement.setAttribute(attributeName, attributeValue.toString());
    }
}