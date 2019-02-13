package cmuche.fritzbox_info_display.tools;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlTool
{
  public static Document getXmlDocument(String xml) throws Exception
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputSource inputSource = new InputSource(new StringReader(xml));
    Document doc = builder.parse(inputSource);
    doc.getDocumentElement().normalize();

    return doc;
  }
}
