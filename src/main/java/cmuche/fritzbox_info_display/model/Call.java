package cmuche.fritzbox_info_display.model;

import cmuche.fritzbox_info_display.enums.CallType;

import java.util.Date;

public class Call {
	private CallType type;
	private PhoneNumber internal;
	private PhoneNumber external;
	private int duration;
	private String device;
	private Date date;

	public Call(CallType type, PhoneNumber internal, PhoneNumber external, int duration, String device, Date date) {
		this.type = type;
		this.internal = internal;
		this.external = external;
		this.duration = duration;
		this.device = device;
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("%s call on %tF %tR took %d second(s): Internal: %s, External: %s", type, date, date, duration, internal, external);
	}

	public CallType getType() {
		return type;
	}

	public PhoneNumber getInternal() {
		return internal;
	}

	public PhoneNumber getExternal() {
		return external;
	}

	public int getDuration() {
		return duration;
	}

	public String getDevice() {
		return device;
	}

	public Date getDate() {
		return date;
	}
}
