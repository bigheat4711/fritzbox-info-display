package cmuche.fritzbox_info_display;

import cmuche.fritzbox_info_display.controller.FritzBoxController;
import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.Credentials;
import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;
import org.apache.commons.collections4.MapUtils;

public class App
{
  public static void main(String[] args) throws Exception
  {
    Credentials credentials = Credentials.fromParameters(args);
    FritzBoxController fbController = new FritzBoxController(credentials);
    MapUtils.debugPrint(System.out, "response", fbController.doRequest(FbService.OnTel, FbAction.GetCallList));
  }
}
