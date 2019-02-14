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

  @FXML
  private Label lblConnectionStatus;

  @FXML
  private Label lblExternalIp;

  public void displayData(DataResponse dataResponse)
  {
    Platform.runLater(() ->
    {
      lblConnectionStatus.setText(dataResponse.getConnectionStatus());
      lblExternalIp.setText(dataResponse.getExternalIp());


      lstCalls.getItems().clear();
      int callCount = 0;
      int callMax = 7;
      for (Call call : dataResponse.getCalls())
      {
        if (callCount >= callMax) break;
        lstCalls.getItems().add(call);
        callCount++;
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
