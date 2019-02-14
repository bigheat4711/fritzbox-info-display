package cmuche.fritzbox_info_display.enums;

public enum HostInterface
{
  Ethernet("Ethernet"), WiFi("802.11"), HomePlug("HomePlug");

  private String id;

  HostInterface(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public static HostInterface getById(String id)
  {
    for (HostInterface iface : HostInterface.values())
    {
      if (iface.getId().equals(id)) return iface;
    }

    return null;
  }

}
