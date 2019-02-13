package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.interfaces.XmlAction;
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

    Document callListDoc = XmlTool.getXmlDocument(callListXml);
    XmlTool.doForEachChild(callListDoc, "Call", node ->
    {
      String typeString = XmlTool.getNodeContent(node, "Type");
      String callerString = XmlTool.getNodeContent(node, "Caller");
      String calledString = XmlTool.getNodeContent(node, "Called");
      String deviceString = XmlTool.getNodeContent(node, "Device");
      String dateString = XmlTool.getNodeContent(node, "Date");
      String durationString = XmlTool.getNodeContent(node, "Duration");
    });

    return null;
  }
}
