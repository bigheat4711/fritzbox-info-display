package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.DataResponse;
import cmuche.fritzbox_info_display.tools.NetworkTool;
import cmuche.fritzbox_info_display.tools.XmlTool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;

public class DataController
{
  private FritzBoxController fritzBoxController;

  public DataController(FritzBoxController fritzBoxController)
  {
    this.fritzBoxController = fritzBoxController;
  }

  public DataResponse collectData() throws Exception
  {
    Map<String, String> callListRequest = fritzBoxController.doRequest(FbService.OnTel, FbAction.GetCallList);
    String callListUrl = callListRequest.get("NewCallListURL");
    String callListXml = NetworkTool.getFileContents(callListUrl);

    System.out.println(callListXml);

    Document callListDoc = XmlTool.getXmlDocument(callListXml);
    NodeList callListNodes = callListDoc.getElementsByTagName("Call");
    for (int i = 0; i < callListNodes.getLength(); i++)
    {
      Element callListItem = (Element) callListNodes.item(i);
      String caller = callListItem.getElementsByTagName("Caller").item(0).getTextContent();
      String called = callListItem.getElementsByTagName("Called").item(0).getTextContent();
      String device = callListItem.getElementsByTagName("Device").item(0).getTextContent();
      String date = callListItem.getElementsByTagName("Date").item(0).getTextContent();
    }

    return null;
  }
}
