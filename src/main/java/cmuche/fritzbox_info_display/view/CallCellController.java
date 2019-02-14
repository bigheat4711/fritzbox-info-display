package cmuche.fritzbox_info_display.view;

import cmuche.fritzbox_info_display.model.Call;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CallCellController
{
  @FXML
  private Label lblExternal;

  @FXML
  private Label lblCity;

  @FXML
  private Label lblDate;

  @FXML
  private Label lblTime;

  @FXML
  private Label lblDuration;

  public void setCall(Call call)
  {
    lblDate.setText(String.format("%td. %tb", call.getDate(), call.getDate()));
    lblTime.setText(String.format("%tR", call.getDate()));

    if (call.getDuration() > 0) lblDuration.setText(call.getDuration() + "s");
    else lblDuration.setVisible(false);

    if (call.getExternal() == null) lblExternal.setText("Unbekannt");
    else
    {
      lblExternal.setText(call.getExternal().getNumber());
      if (call.getExternal().getCityCode() != null) lblCity.setText(call.getExternal().getCityCode().getCity());
    }

    if (call.getExternal() == null || call.getExternal().getCityCode() == null) lblCity.setVisible(false);
  }
}
