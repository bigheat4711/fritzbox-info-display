package cmuche.fritzbox_info_display;

import cmuche.fritzbox_info_display.controller.AppController;
import cmuche.fritzbox_info_display.model.Credentials;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {
	private final Credentials credentials;

	public Runner(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		AppController appController = new AppController(credentials);
		//DeviceInfo_Service deviceInfo_service = new DeviceInfo_Service();
		//DeviceInfo getInfo = deviceInfo_service.getGetInfo();
		//System.out.println(getInfo.getDeviceLog());
	}
}
