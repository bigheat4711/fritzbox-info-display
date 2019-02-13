package cmuche.fritzbox_info_display.enums;

public enum FbAction implements NamedEnum
{
  GetCallList("GetCallList");

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
