package cmuche.fritzbox_info_display.model;

import java.util.List;

public class DataResponse
{
  private List<Call> calls;
  private String externalIp;
  private String connectionStatus;

  public List<Call> getCalls()
  {
    return calls;
  }

  public void setCalls(List<Call> calls)
  {
    this.calls = calls;
  }

  public String getExternalIp()
  {
    return externalIp;
  }

  public void setExternalIp(String externalIp)
  {
    this.externalIp = externalIp;
  }

  public String getConnectionStatus()
  {
    return connectionStatus;
  }

  public void setConnectionStatus(String connectionStatus)
  {
    this.connectionStatus = connectionStatus;
  }
}
