package cmuche.fritzbox_info_display.enums;

public enum FbAction implements NamedEnum
{
  GetCallList("GetCallList"),
  DialNumber("X_AVM-DE_DialNumber"),
  DialHangup("X_AVM-DE_DialHangup");

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
