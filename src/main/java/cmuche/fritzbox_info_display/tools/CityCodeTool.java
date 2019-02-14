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

    codes.add(new CityCode("01570", "E-Plus"));
    codes.add(new CityCode("01573", "E-Plus"));
    codes.add(new CityCode("01575", "E-Plus"));
    codes.add(new CityCode("01577", "E-Plus"));
    codes.add(new CityCode("01578", "E-Plus"));
    codes.add(new CityCode("0163", "E-Plus"));
    codes.add(new CityCode("0177", "E-Plus"));
    codes.add(new CityCode("0178", "E-Plus"));
    codes.add(new CityCode("01590", "O2"));
    codes.add(new CityCode("0176", "O2"));
    codes.add(new CityCode("0179", "O2"));
    codes.add(new CityCode("01520", "Vodafone"));
    codes.add(new CityCode("01522", "Vodafone"));
    codes.add(new CityCode("01523", "Vodafone"));
    codes.add(new CityCode("01525", "Vodafone"));
    codes.add(new CityCode("0162", "Vodafone"));
    codes.add(new CityCode("0172", "Vodafone"));
    codes.add(new CityCode("0173", "Vodafone"));
    codes.add(new CityCode("0174", "Vodafone"));
    codes.add(new CityCode("01511", "Deutsche Telekom"));
    codes.add(new CityCode("01512", "Deutsche Telekom"));
    codes.add(new CityCode("01514", "Deutsche Telekom"));
    codes.add(new CityCode("01515", "Deutsche Telekom"));
    codes.add(new CityCode("01516", "Deutsche Telekom"));
    codes.add(new CityCode("01517", "Deutsche Telekom"));
    codes.add(new CityCode("0160", "Deutsche Telekom"));
    codes.add(new CityCode("0170", "Deutsche Telekom"));
    codes.add(new CityCode("0171", "Deutsche Telekom"));
    codes.add(new CityCode("0175", "Deutsche Telekom"));
  }

  public static CityCode getCityCodeForNumber(String number)
  {
    for (CityCode code : codes)
      if (number.startsWith(code.getCode())) return code;

    return null;
  }
}
