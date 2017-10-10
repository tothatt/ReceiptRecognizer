package com.bme.receiptrecognizer.service;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bme.receiptrecognizer.model.Receipt;
import com.bme.receiptrecognizer.model.XmlChar;

public class XmlWriterService {

	public void updateXmlFile(Receipt receipt) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(receipt.getXmlUrl());

			NodeList nodeList = doc.getElementsByTagName("charParams");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node currentNode = nodeList.item(i);
				if (currentNode.hasAttributes()) {
					Attr attrB = (Attr) currentNode.getAttributes().getNamedItem("b");
					Attr attrR = (Attr) currentNode.getAttributes().getNamedItem("r");
					for (XmlChar xmlChar : receipt.getChars()) {
						if (Integer.valueOf(attrB.getValue()) == xmlChar.getB()
								&& Integer.valueOf(attrR.getValue()) == xmlChar.getR()) {
							currentNode.setTextContent(xmlChar.getS());

						}
					}
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(receipt.getXmlUrl()));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}