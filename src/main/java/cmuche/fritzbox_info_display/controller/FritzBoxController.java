package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.tr064.Action;
import cmuche.fritzbox_info_display.tr064.FritzConnection;
import cmuche.fritzbox_info_display.tr064.Response;
import cmuche.fritzbox_info_display.tr064.Service;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class FritzBoxController {
	private final FritzConnection connection;

	public FritzBoxController(FritzConnection connection) throws Exception {
		this.connection = connection;
		this.connection.init();
	}

	public void authStuff() throws IOException {
		String serviceName = "X_AVM-DE_Auth:1";
		Service service = connection.getService(serviceName);
		Set<String> actionNames = service.getActions()
				.keySet();

		Response getInfo = service.getAction("GetInfo").execute();
		//Response getState = service.getAction("GetState").execute();

		System.out.println("done-auth");
	}

	public Optional<Integer> securityPort() throws IOException {
		String service = "DeviceInfo:1";
		String action = "GetSecurityPort";
		String key = "NewSecurityPort";

		return doRequest(service, action, key)
				.map(Integer::parseInt);
	}

	public Optional<String> internalUser() throws IOException {
		String service = "LANConfigSecurity:1";
		String action = "X_AVM-DE_GetCurrentUser";
		String key = "NewSecurityPort";

		String s = doRequest(service, action);
		return Optional.empty();
	}

	private String doRequest(String service, String action) throws IOException {
		Service s = connection.getService(service);
		Action a = s.getAction(action);
		Response r = a.execute();
		Map<String, String> data = r.getData();
		System.out.println(data);
		String s1 = data.entrySet()
				.stream()
				.findFirst()
				.map(Map.Entry::getValue)
				.orElseThrow();
		return s1;
	}

	private Optional<String> doRequest(String service, String action, String key) throws IOException {
		Service s = connection.getService(service);
		Action a = s.getAction(action);
		Response r = a.execute();
		return Optional.of(r.getData())
				.map(e -> e.get(key));
	}

	public Map<String, String> doRequest(FbService fbService, FbAction fbAction) throws Exception {
		return doRequest(fbService, fbAction, null);
	}

	public Map<String, String> doRequest(FbService fbService, FbAction fbAction, Map<String, Object> args) throws Exception {
		Service service = connection.getService(fbService.getId());
		Action action = service.getAction(fbAction.getId());
		Response response = action.execute(args);

		return response.getData();
	}
}
