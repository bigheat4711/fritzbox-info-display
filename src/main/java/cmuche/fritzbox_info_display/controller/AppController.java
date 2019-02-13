package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.Credentials;
import cmuche.fritzbox_info_display.model.DataResponse;
import org.apache.commons.collections4.MapUtils;

public class AppController
{
  private Credentials credentials;

  private ViewController viewController;

  private boolean isRunning = false;
  private Thread updateDataThread;

  public AppController(Credentials credentials) throws Exception
  {
    this.credentials = credentials;
    setupThreads();
  }

  private void setupThreads()
  {
    updateDataThread = new Thread(() ->
    {
      try
      {
        while (isRunning)
        {
          updateData();
          Thread.sleep(100000);
        }
      }
      catch (Exception ex)
      {
        //okay
      }
    });

  }

  private void updateData() throws Exception
  {
    FritzBoxController fritzBoxController = new FritzBoxController(credentials);
    DataController dataController = new DataController(fritzBoxController);
    DataResponse data = dataController.collectData();
    viewController.displayData(data);
  }

  public void start(ViewController viewController)
  {
    this.viewController = viewController;
    isRunning = true;
    updateDataThread.start();
  }

  public void stop()
  {
    isRunning = false;
    updateDataThread.interrupt();
  }
}
