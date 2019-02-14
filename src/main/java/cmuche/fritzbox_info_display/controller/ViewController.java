package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.model.DataResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Date;

public class ViewController
{
  @FXML
  private Label lblTime;

  @FXML
  private Label lblDate;

  @FXML
  private ListView lstCalls;

  public void displayData(DataResponse dataResponse)
  {
    Platform.runLater(() ->
    {
      lstCalls.getItems().clear();
      for (Call call : dataResponse.calls)
      {
        lstCalls.getItems().add(call);
      }
    });
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
