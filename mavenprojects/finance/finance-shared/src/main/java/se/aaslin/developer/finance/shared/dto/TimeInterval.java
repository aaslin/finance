package se.aaslin.developer.finance.shared.dto;

import java.io.Serializable;


public class TimeInterval implements Serializable {
	private static final long serialVersionUID = -614965438078636926L;
	
	private String name;
	private TimeFrameDTO start;
	private TimeFrameDTO stop;
	
	public TimeInterval() {
	}

	public TimeInterval(String name, TimeFrameDTO start, TimeFrameDTO stop) {
		this.name = name;
		this.start = start;
		this.stop = stop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TimeFrameDTO getStart() {
		return start;
	}

	public void setStart(TimeFrameDTO start) {
		this.start = start;
	}

	public TimeFrameDTO getStop() {
		return stop;
	}

	public void setStop(TimeFrameDTO stop) {
		this.stop = stop;
	}
}
