package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.CallType;
import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.DataResponse;
import cmuche.fritzbox_info_display.model.PhoneNumber;
import cmuche.fritzbox_info_display.tools.NetworkTool;
import cmuche.fritzbox_info_display.tools.ParseTool;
import cmuche.fritzbox_info_display.tools.XmlTool;
import org.w3c.dom.Document;

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

      CallType callType = ParseTool.parseCallType(typeString);
      PhoneNumber internal = new PhoneNumber(ParseTool.parseSip(calledString));
      PhoneNumber external = new PhoneNumber(callerString);

      System.out.println(callType);
      System.out.println(internal);
      System.out.println(external);
      System.out.println();
    });

    return null;
  }
}
