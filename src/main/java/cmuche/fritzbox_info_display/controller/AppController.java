package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.model.Call;
import cmuche.fritzbox_info_display.model.DataResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppController {
	private final int INTERVAL_DATA = 10 * 60 * 1000;
	private final int INTERVAL_TIME = 60 * 1000;

	private final FritzBoxController fritzBoxController;

	//private ViewController viewController;

	private boolean isRunning = false;
	private Thread updateDataThread;
	private Thread updateTimeThread;

	public AppController(FritzBoxController fritzBoxController) {
		this.fritzBoxController = fritzBoxController;
		setupThreads();
	}

	private void setupThreads() {
		updateDataThread = new Thread(() ->
		{
			while (isRunning) {
				try {
					//viewController.displayLoading(true);
					updateData();
				} catch (Exception ex) {
					//viewController.diplayError(ex);
					ex.printStackTrace();
				} finally {
					//viewController.displayLoading(false);
					try {
						Thread.sleep(INTERVAL_DATA);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		});

		updateTimeThread = new Thread(() ->
		{
			try {
				while (isRunning) {
					updateTime();
					Thread.sleep(INTERVAL_TIME);
				}
			} catch (InterruptedException ex) {
				//okay
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

	}

	private void updateTime() {
		Date thisDate = new Date();
		//viewController.displayTime(thisDate);
	}

	private void updateData() throws Exception {
		DataController dataController = new DataController(fritzBoxController);
		DataResponse data = dataController.collectData();
		//viewController.displayData(data);
	}

	public void redial(Call call) throws Exception {
		Map<String, Object> args = new HashMap<>();
		args.put("NewX_AVM-DE_PhoneNumber", call.getExternal().getNumber());
		fritzBoxController.doRequest(FbService.Voip, FbAction.DialNumber, args);
	}

	public void cancelRedial() throws Exception {
		fritzBoxController.doRequest(FbService.Voip, FbAction.DialHangup);
	}

	public void start(Object viewController) {
		//this.viewController = viewController;

		isRunning = true;
		updateTimeThread.start();
		updateDataThread.start();
	}

	public void stop() {
		isRunning = false;
		updateTimeThread.interrupt();
		updateDataThread.interrupt();
	}
}
