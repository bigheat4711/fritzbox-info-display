package cmuche.fritzbox_info_display.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CallModalController
{
  @FXML
  private void clickCancel(MouseEvent event) throws IOException
  {
    System.out.println("cancel");
    ((Node)(event.getSource())).getScene().getWindow().hide();
  }
}
