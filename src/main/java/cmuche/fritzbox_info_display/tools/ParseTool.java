package cmuche.fritzbox_info_display.tools;

import cmuche.fritzbox_info_display.enums.CallType;
import cmuche.fritzbox_info_display.enums.ConnectionStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParseTool {
	private static final Map<String, CallType> callTypeMap;

	static {
		callTypeMap = new HashMap<>();
		callTypeMap.put("1", CallType.Inbound);
		callTypeMap.put("2", CallType.Missed);
		callTypeMap.put("3", CallType.Outbound);
	}

	public static String parseSip(String input) {
		return input.replaceAll("SIP: ", "");
	}

	public static CallType parseCallType(String input) {
		return callTypeMap.get(input);
	}

	public static Date parseDate(String input) {
		try {
			SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yy HH:mm");
			Date date = parser.parse(input);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int parseDuration(String input) {
		String[] parts = input.split(":");
		int duration = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
		if (duration <= 1) return 0;
		return duration;
	}

	public static String parseNullableString(String input) {
		if (input == null) return null;
		if (input.isEmpty()) return null;
		return input;
	}

	public static ConnectionStatus parseConnectionStatus(String input) {
		return ConnectionStatus.valueOf(input);
	}
}
