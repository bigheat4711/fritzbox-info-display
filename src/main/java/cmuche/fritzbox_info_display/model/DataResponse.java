package cmuche.fritzbox_info_display.model;

import java.util.List;

public class DataResponse
{
  public List<Call> calls;

  public DataResponse(List<Call> calls)
  {
    this.calls = calls;
  }
}
