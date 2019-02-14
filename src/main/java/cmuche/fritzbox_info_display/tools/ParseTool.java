package cmuche.fritzbox_info_display.tools;

public class ParseTool
{
  public static String parseSip(String input)
  {
    return input.replaceAll("SIP: ", "");
  }
}
