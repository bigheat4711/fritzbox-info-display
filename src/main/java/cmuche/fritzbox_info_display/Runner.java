package cmuche.fritzbox_info_display;

import cmuche.fritzbox_info_display.controller.FritzBoxController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {
	private final FritzBoxController fritzBoxController;

	public Runner(FritzBoxController fritzBoxController) {
		this.fritzBoxController = fritzBoxController;
	}

	@Override
	public void run(ApplicationArguments args) {
		//fritzBoxController.authStuff();
		fritzBoxController.bytesStuff();
	}
}
