package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.CallType;
import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.model.DataResponse;
import cmuche.fritzbox_info_display.model.PhoneNumber;
import cmuche.fritzbox_info_display.tools.NetworkTool;
import cmuche.fritzbox_info_display.tools.ParseTool;
import cmuche.fritzbox_info_display.tools.XmlTool;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    List<Call> calls = new ArrayList<>();

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

      String callerNumber = ParseTool.parseNullableString(callerString);
      CallType callType = ParseTool.parseCallType(typeString);

      //special treatment for TAM (device='er')
      if (deviceString.equals("er"))
      {
        deviceString = "AB";
        callType = CallType.Tam;
      }

      PhoneNumber internal = new PhoneNumber(ParseTool.parseSip(calledString));
      PhoneNumber external = callerNumber == null ? null : new PhoneNumber(callerString);
      Date date = ParseTool.parseDate(dateString);
      int duration = ParseTool.parseDuration(durationString);
      String device = ParseTool.parseNullableString(deviceString);

      Call call = new Call(callType, internal, external, duration, device, date);
      System.out.println(call);
      calls.add(call);
    });

    DataResponse dataResponse = new DataResponse(calls);

    return dataResponse;
  }
}
