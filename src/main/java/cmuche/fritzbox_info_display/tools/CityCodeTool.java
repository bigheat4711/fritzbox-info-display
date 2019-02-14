package cmuche.fritzbox_info_display.tools;

import cmuche.fritzbox_info_display.model.CityCode;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityCodeTool
{
  private static List<CityCode> codes;

  static
  {
    codes = new ArrayList<>();
    loadList();
  }

  private static void loadList()
  {
    URL url = CityCodeTool.class.getClassLoader().getResource("NVONB.INTERNET.20190212.ONB.csv");
    try
    {
      List<String> lines = FileUtils.readLines(new File(url.getFile()), "UTF-8");
      boolean firstLine = true;
      for (String line : lines)
      {
        if (firstLine)
        {
          firstLine = false;
          continue;
        }

        String[] lineParts = line.split(";");

        if (lineParts.length != 3) continue;
        if (!lineParts[2].equals("1")) continue;

        String code = "0" + lineParts[0];
        String city = lineParts[1];
        CityCode cityCode = new CityCode(code, city);
        codes.add(cityCode);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static CityCode getCityCodeForNumber(String number)
  {
    for (CityCode code : codes)
      if (number.startsWith(code.getCode())) return code;

    return null;
  }
}
