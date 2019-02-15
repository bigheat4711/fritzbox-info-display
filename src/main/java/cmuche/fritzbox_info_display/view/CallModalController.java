package cmuche.fritzbox_info_display.view;

import cmuche.fritzbox_info_display.App;
import cmuche.fritzbox_info_display.model.Call;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CallModalController
{
  @FXML
  private Label lblExternal;

  @FXML
  private Label lblDate;

  @FXML
  private Label lblTime;

  @FXML
  private Button btnRedial;

  @FXML
  private Button btnCancelRedial;

  @FXML
  private ProgressIndicator pgiDialing;

  private boolean isDialing = false;

  private Call call;

  public void setCall(Call call)
  {
    this.call = call;

    btnCancelRedial.setVisible(false);
    pgiDialing.setVisible(false);

    lblDate.setText(String.format("%td. %tb", call.getDate(), call.getDate()));
    lblTime.setText(String.format("%tR", call.getDate()));

    if (call.getExternal() == null)
    {

      lblExternal.setText("Unbekannt");
      btnRedial.setVisible(false);
    } else lblExternal.setText(call.getExternal().getNumber());
  }

  @FXML
  private void clickRedial(MouseEvent event) throws Exception
  {
    if (isDialing) return;

    new Thread(() ->
    {
      try
      {
        isDialing = true;
        pgiDialing.setVisible(true);
        App.getAppController().redial(call);
        pgiDialing.setVisible(false);
        btnRedial.setVisible(false);
        btnRedial.setMinHeight(0);
        btnRedial.setPrefHeight(0);
        btnCancelRedial.setVisible(true);
        btnCancelRedial.setMinHeight(80);
        btnCancelRedial.setPrefHeight(80);
        isDialing = false;
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }).start();
  }

  @FXML
  private void clickCancelRedial(MouseEvent event) throws Exception
  {
    if (isDialing) return;

    new Thread(() ->
    {
      try
      {
        isDialing = true;
        pgiDialing.setVisible(true);
        App.getAppController().cancelRedial();
        pgiDialing.setVisible(false);
        btnCancelRedial.setVisible(false);
        btnCancelRedial.setMinHeight(0);
        btnCancelRedial.setPrefHeight(0);
        btnRedial.setVisible(true);
        btnRedial.setMinHeight(80);
        btnRedial.setPrefHeight(80);
        isDialing = false;
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }).start();
  }

  @FXML
  private void clickCancel(MouseEvent event)
  {
    ((Node) (event.getSource())).getScene().getWindow().hide();
  }
}
