package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.HostInterface;
import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.model.DataResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

import java.util.Date;

public class ViewController
{
  @FXML
  private VBox boxError;

  @FXML
  private Label lblErrorName;

  @FXML
  private Label lblErrorDescription;

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

  @FXML
  private Label lblHostsEthernet;

  @FXML
  private Label lblHostsWifi;

  @FXML
  private Label lblPhones;

  @FXML
  private ProgressIndicator pgiLoading;

  @FXML
  public void initialize()
  {
    boxError.setMinHeight(0);
    boxError.setPrefHeight(0);
    boxError.setVisible(false);
  }

  public void displayData(DataResponse dataResponse)
  {
    Platform.runLater(() ->
    {
      boxError.setMinHeight(0);
      boxError.setPrefHeight(0);
      boxError.setVisible(false);

      lblConnectionStatus.setText(dataResponse.getConnectionStatus().getDisplay());
      lblExternalIp.setText(dataResponse.getExternalIp());

      int countHostsEthernet = (int) dataResponse.getHosts().stream().filter(x -> x.getIface() == HostInterface.Ethernet).count();
      int countHostsWifi = (int) dataResponse.getHosts().stream().filter(x -> x.getIface() == HostInterface.WiFi).count();
      int countPhones = (int) dataResponse.getPhones().stream().count();

      lblHostsEthernet.setText(String.valueOf(countHostsEthernet));
      lblHostsWifi.setText(String.valueOf(countHostsWifi));
      lblPhones.setText(String.valueOf(countPhones));

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

  public void displayLoading(boolean isLoading)
  {
    Platform.runLater(() -> pgiLoading.setVisible(isLoading));
  }

  public void diplayError(Exception exception)
  {
    Platform.runLater(() ->
    {
      lstCalls.getItems().clear();
      boxError.setMinHeight(45);
      boxError.setPrefHeight(45);
      boxError.setVisible(true);
      lblErrorName.setText(exception.getClass().getName());
      lblErrorDescription.setText(exception.getMessage());
    });
  }
}
