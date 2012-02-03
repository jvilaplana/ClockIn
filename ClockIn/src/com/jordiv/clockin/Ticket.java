package com.jordiv.clockin;

import java.util.Date;

public class Ticket {
	private long id;
	private Date date;
	private Boolean type;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getType() {
		return type;
	}
	public void setType(Boolean type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return date.toString() + " - " + type;
	}
}
