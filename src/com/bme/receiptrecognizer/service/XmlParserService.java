package com.bme.receiptrecognizer.service;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bme.receiptrecognizer.model.ClientSettings;
import com.bme.receiptrecognizer.model.Receipt;
import com.bme.receiptrecognizer.model.XmlChar;

public class XmlParserService {
	public Receipt parsexml(String string) {
		Receipt receipt = new Receipt();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			receipt.setXmlUrl(string);
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(string);
			NodeList pageNode = doc.getElementsByTagName("page");
			receipt.setImgWidth(Integer.parseInt(((Element) pageNode.item(0)).getAttribute("width")));
			receipt.setImgHeight(Integer.parseInt(((Element) pageNode.item(0)).getAttribute("height")));
			NodeList nList = doc.getElementsByTagName("line");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				int lineId = Integer.parseInt(eElement.getAttribute("baseline"));

				NodeList cElementList = eElement.getElementsByTagName("charParams");
				for (int k = 0; k < cElementList.getLength(); k++) {
					XmlChar xmlChar = new XmlChar();
					xmlChar.setLineId(lineId);
					xmlChar.setS(cElementList.item(k).getTextContent());
					xmlChar.setL(Integer.parseInt(((Element) cElementList.item(k)).getAttribute("l")));
					xmlChar.setT(Integer.parseInt(((Element) cElementList.item(k)).getAttribute("t")));
					xmlChar.setR(Integer.parseInt(((Element) cElementList.item(k)).getAttribute("r")));
					xmlChar.setB(Integer.parseInt(((Element) cElementList.item(k)).getAttribute("b")));
					xmlChar.setSuspicious(((Element) cElementList.item(k)).hasAttribute("suspicious"));
					receipt.getChars().add(xmlChar);
				}

			}
			// System.out.println(sb.toString());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receipt;
	}
}
