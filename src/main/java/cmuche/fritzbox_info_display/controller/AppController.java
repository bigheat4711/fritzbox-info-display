package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.model.Credentials;
import cmuche.fritzbox_info_display.model.DataResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppController
{
  private Credentials credentials;
  private FritzBoxController fritzBoxController;

  private ViewController viewController;

  private boolean isRunning = false;
  private Thread updateDataThread;
  private Thread updateTimeThread;

  public AppController(Credentials credentials) throws Exception
  {
    this.credentials = credentials;
    fritzBoxController = new FritzBoxController(credentials);
    setupThreads();
  }

  private void setupThreads()
  {
    updateDataThread = new Thread(() ->
    {
      while (isRunning)
      {
        try
        {
          updateData();
          Thread.sleep(10 * 60 * 1000);

        }
        catch (InterruptedException ex)
        {
          //okay
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }
    });

    updateTimeThread = new Thread(() ->
    {
      try
      {
        while (isRunning)
        {
          updateTime();
          Thread.sleep(60 * 1000);
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
    DataController dataController = new DataController(fritzBoxController);
    DataResponse data = dataController.collectData();
    viewController.displayData(data);
  }

  public void redial(Call call) throws Exception
  {
    Map<String, Object> args = new HashMap<>();
    args.put("NewX_AVM-DE_PhoneNumber", call.getExternal().getNumber());
    fritzBoxController.doRequest(FbService.Voip, FbAction.DialNumber, args);
  }

  public void cancelRedial() throws Exception
  {
    fritzBoxController.doRequest(FbService.Voip, FbAction.DialHangup);
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
