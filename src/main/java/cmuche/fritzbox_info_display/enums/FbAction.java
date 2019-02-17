package cmuche.fritzbox_info_display.enums;

public enum FbAction implements NamedEnum
{
  GetCallList("GetCallList"),
  DialNumber("X_AVM-DE_DialNumber"),
  DialHangup("X_AVM-DE_DialHangup"),
  GetInfo("GetInfo"),
  GetHostsCount("GetHostNumberOfEntries"),
  GetHostInfo("GetGenericHostEntry"),
  GetDectHandsetList("GetDECTHandsetList"),
  GetDectHandsetInfo("GetDECTHandsetInfo"),
  GetNumberOfEntries("GetNumberOfEntries");

  private String id;

  FbAction(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return this.id;
  }
}
