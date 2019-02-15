package cmuche.fritzbox_info_display;

import cmuche.fritzbox_info_display.controller.AppController;
import cmuche.fritzbox_info_display.controller.ViewController;
import cmuche.fritzbox_info_display.model.Credentials;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
  private static ViewController viewController;
  private static AppController appController;

  private static boolean isLive;

  public static void main(String[] args) throws Exception
  {
    isLive = args.length >= 4 && args[3].equals("live");

    Credentials credentials = Credentials.fromParameters(args);
    appController = new AppController(credentials);

    launch();
  }

  @Override
  public void stop()
  {
    appController.stop();
    Platform.exit();
  }

  @Override
  public void start(Stage primaryStage) throws Exception
  {
    FXMLLoader loader = new FXMLLoader(ViewController.class.getClassLoader().getResource("fx/Main.fxml"));
    Parent root = loader.load();

    viewController = loader.getController();
    appController.start(viewController);

    Stage stg = new Stage();
    stg.setTitle("FRITZ!Box Info Display");
    Scene scn = new Scene(root);

    if (isLive)
    {
      scn.setCursor(Cursor.NONE);
      stg.setFullScreen(true);
    }

    stg.setScene(scn);
    stg.show();
  }
}
