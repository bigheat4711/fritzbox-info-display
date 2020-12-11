package cmuche.fritzbox_info_display.model;

public class Credentials {
	private final String ip;
	private final String username;
	private final String password;

	public Credentials(String ip, String username, String password) {
		this.ip = ip;
		this.username = username;
		this.password = password;
	}

	@Deprecated
	public static Credentials fromParameters(String[] params) {
		String ip = params[0];
		String username = params[1];
		String password = params[2];

		return new Credentials(ip, username, password);
	}

	public String getIp() {
		return ip;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
