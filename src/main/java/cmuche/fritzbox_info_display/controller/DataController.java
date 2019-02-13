package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.DataResponse;
import cmuche.fritzbox_info_display.tools.NetworkTool;

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

    return null;
  }
}
