package cmuche.fritzbox_info_display.tools;

import cmuche.fritzbox_info_display.model.CityCode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CityCodeTool {
	private static final List<CityCode> codes;

	static {
		codes = new ArrayList<>();
		loadList();
	}

	private static void loadList() {
		try {
			URL url = Resources.getResource("NVONB.INTERNET.20190212.ONB.csv");
			String text = Resources.toString(url, Charsets.UTF_8);
			String[] lines = text.split("\r\n");

			boolean firstLine = true;
			for (String line : lines) {
				if (firstLine) {
					firstLine = false;
					continue;
				}

				String[] lineParts = line.split(";");

				if (lineParts.length != 3) continue;
				if (!lineParts[2].equals("1")) continue;

				String code = "0" + lineParts[0];
				String city = lineParts[1];
				CityCode cityCode = new CityCode(code, city);
				codes.add(cityCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

    /*codes.add(new CityCode("01570", "E-Plus"));
    codes.add(new CityCode("01573", "E-Plus"));
    codes.add(new CityCode("01575", "E-Plus"));
    codes.add(new CityCode("01577", "E-Plus"));
    codes.add(new CityCode("01578", "E-Plus"));
    codes.add(new CityCode("0163", "E-Plus"));
    codes.add(new CityCode("0177", "E-Plus"));
    codes.add(new CityCode("0178", "E-Plus"));
    codes.add(new CityCode("01590", "O2"));
    codes.add(new CityCode("0176", "O2"));
    codes.add(new CityCode("0179", "O2"));
    codes.add(new CityCode("01520", "Vodafone"));
    codes.add(new CityCode("01522", "Vodafone"));
    codes.add(new CityCode("01523", "Vodafone"));
    codes.add(new CityCode("01525", "Vodafone"));
    codes.add(new CityCode("0162", "Vodafone"));
    codes.add(new CityCode("0172", "Vodafone"));
    codes.add(new CityCode("0173", "Vodafone"));
    codes.add(new CityCode("0174", "Vodafone"));
    codes.add(new CityCode("01511", "Deutsche Telekom"));
    codes.add(new CityCode("01512", "Deutsche Telekom"));
    codes.add(new CityCode("01514", "Deutsche Telekom"));
    codes.add(new CityCode("01515", "Deutsche Telekom"));
    codes.add(new CityCode("01516", "Deutsche Telekom"));
    codes.add(new CityCode("01517", "Deutsche Telekom"));
    codes.add(new CityCode("0160", "Deutsche Telekom"));
    codes.add(new CityCode("0170", "Deutsche Telekom"));
    codes.add(new CityCode("0171", "Deutsche Telekom"));
    codes.add(new CityCode("0175", "Deutsche Telekom"));*/
	}

	public static CityCode getCityCodeForNumber(String number) {
		for (CityCode code : codes)
			if (number.startsWith(code.getCode())) return code;

		return null;
	}
}
