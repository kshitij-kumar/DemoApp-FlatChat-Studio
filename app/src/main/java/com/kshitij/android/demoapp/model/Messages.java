package com.kshitij.android.demoapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */
public class Messages {

	private List<Chat> chats = new ArrayList<Chat>();;

	public List<Chat> getChats() {
		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}
}
