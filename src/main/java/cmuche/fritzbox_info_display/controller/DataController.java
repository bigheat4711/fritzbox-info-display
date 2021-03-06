package cmuche.fritzbox_info_display.controller;

import cmuche.fritzbox_info_display.enums.*;
import cmuche.fritzbox_info_display.model.*;
import cmuche.fritzbox_info_display.tools.NetworkTool;
import cmuche.fritzbox_info_display.tools.ParseTool;
import cmuche.fritzbox_info_display.tools.XmlTool;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataController {
	private final FritzBoxController fritzBoxController;

	public DataController(FritzBoxController fritzBoxController) {
		this.fritzBoxController = fritzBoxController;
	}


	public DataResponse collectData() throws Exception {
		DataResponse dataResponse = new DataResponse();

		List<Call> calls = collectCallList();
		dataResponse.setCalls(calls);

		List<Host> hosts = collectHosts();
		dataResponse.setHosts(hosts);

		List<Phone> phones = collectPhones();
		dataResponse.setPhones(phones);

		Map<String, String> info = fritzBoxController.doRequest(FbService.IpConnection, FbAction.GetInfo);
		String connectionStatusString = info.get("NewConnectionStatus");
		ConnectionStatus connectionStatus = ParseTool.parseConnectionStatus(connectionStatusString);
		String externalIp = ParseTool.parseNullableString(info.get("NewExternalIPAddress"));

		dataResponse.setExternalIp(externalIp);
		dataResponse.setConnectionStatus(connectionStatus);

		return dataResponse;
	}

	private List<Host> collectHosts() throws Exception {
		List<Host> hosts = new ArrayList<>();

		Map<String, String> hostsCountResult = fritzBoxController.doRequest(FbService.Hosts, FbAction.GetHostsCount);
		int countHosts = Integer.parseInt(hostsCountResult.get("NewHostNumberOfEntries"));

		for (int i = 0; i < countHosts; i++) {
			Map<String, Object> args = new HashMap<>();
			args.put("NewIndex", i);
			Map<String, String> hostInfo = fritzBoxController.doRequest(FbService.Hosts, FbAction.GetHostInfo, args);

			boolean active = hostInfo.get("NewActive").equals("1");
			if (!active)
				continue;

			String name = hostInfo.get("NewHostName");
			String mac = hostInfo.get("NewMACAddress");
			String ip = hostInfo.get("NewIPAddress");
			String ifaceString = hostInfo.get("NewInterfaceType");
			HostInterface iface = HostInterface.getById(ifaceString);

			Host host = new Host(name, ip, mac, iface);
			hosts.add(host);
		}

		return hosts;
	}

	private List<Phone> collectPhones() throws Exception {
		List<Phone> phones = new ArrayList<>();

		Map<String, String> dectIdsResult = fritzBoxController.doRequest(FbService.OnTel, FbAction.GetDectHandsetList);
		String dectIdList = dectIdsResult.get("NewDectIDList");
		List<String> dectIds = Arrays.asList(dectIdList.split(",")).stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

		for (String id : dectIds) {
			Map<String, Object> args = new HashMap<>();
			args.put("NewDectID", Integer.valueOf(id));
			Map<String, String> handsetInfoResult = fritzBoxController.doRequest(FbService.OnTel, FbAction.GetDectHandsetInfo, args);

			String name = handsetInfoResult.get("NewHandsetName");
			Phone phone = new Phone(name);
			phones.add(phone);
		}

		return phones;
	}

	private List<Call> collectCallList() throws Exception {
		List<Call> calls = new ArrayList<>();

		Map<String, String> callListRequest = fritzBoxController.doRequest(FbService.OnTel, FbAction.GetCallList);
		String callListUrl = callListRequest.get("NewCallListURL");
		String callListXml = NetworkTool.getFileContents(callListUrl);

		Document callListDoc = XmlTool.getXmlDocument(callListXml);
		XmlTool.doForEachChild(callListDoc, "Call", node ->
		{
			String typeString = XmlTool.getNodeContent(node, "Type");
			String callerString = XmlTool.getNodeContent(node, "Caller");
			String calledString = XmlTool.getNodeContent(node, "Called");
			String deviceString = XmlTool.getNodeContent(node, "Device");
			String dateString = XmlTool.getNodeContent(node, "Date");
			String durationString = XmlTool.getNodeContent(node, "Duration");

			String callerNumber = ParseTool.parseNullableString(callerString);
			CallType callType = ParseTool.parseCallType(typeString);
			PhoneNumber internal = new PhoneNumber(ParseTool.parseSip(calledString));
			PhoneNumber external = callerNumber == null ? null : new PhoneNumber(ParseTool.parseSip(callerString));
			Date date = ParseTool.parseDate(dateString);
			int duration = ParseTool.parseDuration(durationString);
			String device = ParseTool.parseNullableString(deviceString);

			//special treatment for TAM (device='er')
			if (deviceString.equals("er")) {
				duration = 0;
				device = null;
				callType = CallType.Tam;
			}

			Call call = new Call(callType, internal, external, duration, device, date);
			calls.add(call);
		});
		return calls;
	}
}
