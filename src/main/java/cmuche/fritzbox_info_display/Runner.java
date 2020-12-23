package cmuche.fritzbox_info_display;

import cmuche.fritzbox_info_display.controller.FritzBoxController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class Runner implements ApplicationRunner {
	private final FritzBoxController fritzBoxController;

	public Runner(FritzBoxController fritzBoxController) {
		this.fritzBoxController = fritzBoxController;
	}

	@Override
	public void run(ApplicationArguments args) throws IOException {
		//fritzBoxController.authStuff();
		Optional<Integer> securityPort = fritzBoxController.securityPort();
		securityPort.ifPresent(e -> System.out.println("securityPort: " + e));
		Optional<String> s = fritzBoxController.internalUser();
		System.out.println("Done");
	}
}
