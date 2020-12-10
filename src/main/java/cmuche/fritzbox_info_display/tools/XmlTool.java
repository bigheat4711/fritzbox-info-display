package cmuche.fritzbox_info_display.tools;

import cmuche.fritzbox_info_display.interfaces.XmlAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlTool {
	public static Document getXmlDocument(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource inputSource = new InputSource(new StringReader(xml));
		Document doc = builder.parse(inputSource);
		doc.getDocumentElement().normalize();

		return doc;
	}

	public static String getNodeContent(Element node, String tagName) {
		return node.getElementsByTagName(tagName).item(0).getTextContent();
	}

	public static void doForEachChild(Document doc, String tagName, XmlAction xmlAction) {
		NodeList callListNodes = doc.getElementsByTagName(tagName);
		for (int i = 0; i < callListNodes.getLength(); i++) {
			Element thisNode = (Element) callListNodes.item(i);
			xmlAction.run(thisNode);
		}
	}
}
