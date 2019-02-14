package cmuche.fritzbox_info_display.enums;

public enum ConnectionStatus
{
  Unconfigured("Nicht konfiguriert"),
  Connecting("Verbinden"),
  Authenticating("Authentifizieren"),
  PendingDisconnect("Warte auf Trennung"),
  Disconnecting("Trennen"),
  Disconnected("Nicht verbunden"),
  Connected("Verbunden");

  private String display;

  ConnectionStatus(String display)
  {
    this.display = display;
  }

  public String getDisplay()
  {
    return display;
  }
}
