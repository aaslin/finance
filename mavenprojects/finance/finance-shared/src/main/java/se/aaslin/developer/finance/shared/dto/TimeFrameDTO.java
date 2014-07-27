package se.aaslin.developer.finance.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lars
 *
 */
public class TimeFrameDTO implements Serializable {
	
	private static final long serialVersionUID = 2477596692356599457L;

	private int id;
	private String tag;
	private Date start;
	private Date stop;
	
	public TimeFrameDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStop() {
		return stop;
	}

	public void setStop(Date stop) {
		this.stop = stop;
	}
}
