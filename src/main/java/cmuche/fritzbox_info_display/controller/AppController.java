package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.Credentials;
import cmuche.fritzbox_info_display.model.DataResponse;
import org.apache.commons.collections4.MapUtils;

import java.util.Date;

public class AppController
{
  private Credentials credentials;

  private ViewController viewController;

  private boolean isRunning = false;
  private Thread updateDataThread;
  private Thread updateTimeThread;

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
          Thread.sleep(10000);
        }
      }
      catch (InterruptedException ex)
      {
        //okay
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    });

    updateTimeThread = new Thread(() ->
    {
      try
      {
        while (isRunning)
        {
          updateTime();
          Thread.sleep(1000);
        }
      }
      catch (InterruptedException ex)
      {
        //okay
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    });

  }

  private void updateTime()
  {
    Date thisDate = new Date();
    viewController.displayTime(thisDate);
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
    updateTimeThread.start();
    updateDataThread.start();
  }

  public void stop()
  {
    isRunning = false;
    updateTimeThread.interrupt();
    updateDataThread.interrupt();
  }
}
