package com.kshitij.android.demoapp.model;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */
public class Chat {
	private String timestamp;
	private String msg_id;
	private String msg_data;
	private String msg_type;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getMsg_data() {
		return msg_data;
	}

	public void setMsg_data(String msg_data) {
		this.msg_data = msg_data;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

}
