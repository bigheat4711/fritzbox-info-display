package cmuche.fritzbox_info_display;

import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;
import org.apache.commons.collections4.MapUtils;

public class App
{
  public static void main(String[] args) throws Exception
  {
    String ip = args[0];
    String username = args[1];
    String password = args[2];

    FritzConnection fc = new FritzConnection(ip, username, password);
    fc.init();
    //fc.printInfo();

    //MapUtils.debugPrint(System.out, "services",fc.getServices());

    Service service = fc.getService("X_AVM-DE_OnTel:1");

    //MapUtils.debugPrint(System.out, "actions",service.getActions());

    Action action = service.getAction("GetCallList");
    Response response = action.execute();

    MapUtils.debugPrint(System.out, "response", response.getData());
  }
}
