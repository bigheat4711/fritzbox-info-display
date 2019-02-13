package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.Credentials;
import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;

import java.util.Map;

public class FritzBoxController
{
  private FritzConnection connection;

  public FritzBoxController(Credentials credentials) throws Exception
  {
    connection = new FritzConnection(credentials.getIp(), credentials.getUsername(), credentials.getPassword());
    connection.init();
  }

  public Map<String, String> doRequest(FbService fbService, FbAction fbAction) throws Exception
  {
    Service service = connection.getService(fbService.getId());
    Action action = service.getAction(fbAction.getId());
    Response response = action.execute();

    return response.getData();
  }
}
