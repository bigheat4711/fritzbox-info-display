package cmuche.fritzbox_info_display.tools;

import cmuche.fritzbox_info_display.enums.CallType;

import java.util.HashMap;
import java.util.Map;

public class ParseTool
{
  private static Map<String, CallType> callTypeMap;

  static
  {
    callTypeMap = new HashMap<>();
    callTypeMap.put("1", CallType.Inbound);
    callTypeMap.put("2", CallType.Missed);
    callTypeMap.put("3", CallType.Outbound);
  }

  public static String parseSip(String input)
  {
    return input.replaceAll("SIP: ", "");
  }

  public static CallType parseCallType(String input)
  {
    return callTypeMap.get(input);
  }
}
