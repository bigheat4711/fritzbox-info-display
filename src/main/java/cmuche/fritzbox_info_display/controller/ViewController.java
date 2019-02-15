package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.HostInterface;
import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.model.DataResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;

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

  @FXML
  private Label lblHostsEthernet;

  @FXML
  private Label lblHostsWifi;

  @FXML
  private ProgressIndicator pgiLoading;

  public void displayData(DataResponse dataResponse)
  {
    Platform.runLater(() ->
    {
      lblConnectionStatus.setText(dataResponse.getConnectionStatus().getDisplay());
      lblExternalIp.setText(dataResponse.getExternalIp());

      int countHostsEthernet = (int) dataResponse.getHosts().stream().filter(x -> x.getIface() == HostInterface.Ethernet).count();
      int countHostsWifi = (int) dataResponse.getHosts().stream().filter(x -> x.getIface() == HostInterface.WiFi).count();

      lblHostsEthernet.setText(String.valueOf(countHostsEthernet));
      lblHostsWifi.setText(String.valueOf(countHostsWifi));

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
}
