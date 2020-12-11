package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.FbAction;
import cmuche.fritzbox_info_display.enums.FbService;
import cmuche.fritzbox_info_display.tr064.Action;
import cmuche.fritzbox_info_display.tr064.FritzConnection;
import cmuche.fritzbox_info_display.tr064.Response;
import cmuche.fritzbox_info_display.tr064.Service;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FritzBoxController {
	private final FritzConnection connection;

	public FritzBoxController(FritzConnection connection) throws Exception {
		this.connection = connection;
		this.connection.init();
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
