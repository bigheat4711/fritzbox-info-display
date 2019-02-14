package cmuche.fritzbox_info_display.tools;

public class FormatTool
{
  public static String formatDuration(int input)
  {
    if (input < 60) return input + "s";
    return (input / 60) + "m";
  }
}
