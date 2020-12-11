package cmuche.fritzbox_info_display.model;

public class CityCode {
	private final String code;
	private final String city;

	public CityCode(String code, String city) {
		this.code = code;
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return city;
	}
}
