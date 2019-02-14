package cmuche.fritzbox_info_display.view;

import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.tools.FormatTool;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CallCellController
{
  @FXML
  private ImageView imgType;

  @FXML
  private Label lblExternal;

  @FXML
  private Label lblInternal;

  @FXML
  private Label lblCity;

  @FXML
  private Label lblDate;

  @FXML
  private Label lblTime;

  @FXML
  private Label lblDuration;

  @FXML
  private ImageView imgInfoCity;

  public void setCall(Call call) throws IOException
  {
    BufferedImage image = ImageIO.read(CallCellController.class.getClassLoader().getResourceAsStream("icons/Call" + call.getType().name() + ".png"));
    imgType.setImage(SwingFXUtils.toFXImage(image, null));

    lblDate.setText(String.format("%td. %tb", call.getDate(), call.getDate()));
    lblTime.setText(String.format("%tR", call.getDate()));

    if (call.getDuration() > 0) lblDuration.setText(FormatTool.formatDuration(call.getDuration()));
    else lblDuration.setVisible(false);

    if (call.getExternal() == null) lblExternal.setText("Unbekannt");
    else
    {
      lblExternal.setText(call.getExternal().getNumber());
      if (call.getExternal().getCityCode() != null) lblCity.setText(call.getExternal().getCityCode().getCity());
    }

    if (call.getExternal() == null || call.getExternal().getCityCode() == null)
    {
      imgInfoCity.setVisible(false);
      lblCity.setVisible(false);
    }

    String internalString = call.getInternal().getNumber();
    if (call.getDevice() != null) internalString += " (" + call.getDevice() + ")";
    lblInternal.setText(internalString);
  }
}
