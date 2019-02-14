package cmuche.fritzbox_info_display.enums;

public enum FbService implements NamedEnum
{
  OnTel("X_AVM-DE_OnTel:1"),
  Voip("X_VoIP:1");

  private String id;

  FbService(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return this.id;
  }
}
