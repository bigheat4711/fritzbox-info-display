package cmuche.fritzbox_info_display.view;

import cmuche.fritzbox_info_display.App;
import cmuche.fritzbox_info_display.controller.ViewController;
import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.tools.FormatTool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CallCellController {
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

	private Call call;

	public void setCall(Call call) {
		this.call = call;
		imgType.setImage(new Image(CallCellController.class.getClassLoader().getResourceAsStream("icons/Call" + call.getType().name() + ".png")));

		lblDate.setText(String.format("%td. %tb", call.getDate(), call.getDate()));
		lblTime.setText(String.format("%tR", call.getDate()));

		if (call.getDuration() > 0) lblDuration.setText(FormatTool.formatDuration(call.getDuration()));
		else lblDuration.setVisible(false);

		if (call.getExternal() == null) lblExternal.setText("Unbekannt");
		else {
			lblExternal.setText(call.getExternal().getNumber());
			if (call.getExternal().getCityCode() != null) lblCity.setText(call.getExternal().getCityCode().getCity());
		}

		if (call.getExternal() == null || call.getExternal().getCityCode() == null) {
			imgInfoCity.setVisible(false);
			lblCity.setVisible(false);
		}

		String internalString = call.getInternal().getNumber();
		if (call.getDevice() != null) internalString += " (" + call.getDevice() + ")";
		lblInternal.setText(internalString);
	}

	@FXML
	private void clickCall(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(ViewController.class.getClassLoader().getResource("fx/CallModal.fxml"));
		Parent root = loader.load();
		Stage stg = new Stage();
		Scene scn = new Scene(root);

		if (App.isLive()) {
			scn.setCursor(Cursor.NONE);
		}

		stg.setScene(scn);

		CallModalController contr = loader.getController();
		contr.setCall(call);


		stg.initModality(Modality.WINDOW_MODAL);
		stg.initStyle(StageStyle.UNDECORATED);
		stg.setX(((Node) event.getSource()).getScene().getWindow().getX() + 30);
		stg.setY(((Node) event.getSource()).getScene().getWindow().getY() + 30);
		stg.initOwner(((Node) event.getSource()).getScene().getWindow());
		stg.show();
	}
}
