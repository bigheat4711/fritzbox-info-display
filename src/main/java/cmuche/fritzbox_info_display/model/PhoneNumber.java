package cmuche.fritzbox_info_display.model;

import cmuche.fritzbox_info_display.tools.CityCodeTool;

public class PhoneNumber
{
  private String number;
  private CityCode cityCode;

  public PhoneNumber(String number)
  {
    this.number = number;
    if (number != null) cityCode = CityCodeTool.getCityCodeForNumber(number);
  }

  @Override
  public String toString()
  {
    return number + ((cityCode != null) ? " [" + cityCode + "]" : "");
  }

  public String getNumber()
  {
    String num = number;
    if (cityCode != null) num = num.replace(cityCode.getCode(), getCityCode().getCode() + " ");
    return num;
  }

  public CityCode getCityCode()
  {
    return cityCode;
  }
}
