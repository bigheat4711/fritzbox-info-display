package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.model.DataResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Date;

public class ViewController
{
  @FXML
  private Label lblTime;

  @FXML
  private Label lblDate;

  public void displayData(DataResponse dataResponse)
  {

  }

  public void displayTime(Date date)
  {
    Platform.runLater(() ->
    {
      lblTime.setText(String.format("%tR", date));
      lblDate.setText(String.format("%tA, %td. %tB %tY", date, date, date, date));
    });
  }
}
