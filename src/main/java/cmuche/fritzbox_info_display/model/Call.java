package cmuche.fritzbox_info_display.model;

import cmuche.fritzbox_info_display.enums.CallType;

import java.util.Date;

public class Call
{
  private CallType type;
  private PhoneNumber internal;
  private PhoneNumber external;
  private String device;
  private Date date;
}
