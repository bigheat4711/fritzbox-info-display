package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.model.DataResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewController
{
  @FXML
  private Label lblTime;

  @FXML
  private Label lblDate;

  public void displayData(DataResponse data)
  {
    Platform.runLater(() ->
    {
      lblTime.setText("Foo");
      lblDate.setText("Bar");
    });
  }

}
