package cmuche.fritzbox_info_display.model;

import cmuche.fritzbox_info_display.enums.ConnectionStatus;

import java.util.List;

public class DataResponse
{
  private List<Call> calls;
  private List<Host> hosts;
  private List<Phone> phones;
  private String externalIp;
  private ConnectionStatus connectionStatus;

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

  public ConnectionStatus getConnectionStatus()
  {
    return connectionStatus;
  }

  public void setConnectionStatus(ConnectionStatus connectionStatus)
  {
    this.connectionStatus = connectionStatus;
  }

  public List<Host> getHosts()
  {
    return hosts;
  }

  public void setHosts(List<Host> hosts)
  {
    this.hosts = hosts;
  }

  public List<Phone> getPhones()
  {
    return phones;
  }

  public void setPhones(List<Phone> phones)
  {
    this.phones = phones;
  }
}
