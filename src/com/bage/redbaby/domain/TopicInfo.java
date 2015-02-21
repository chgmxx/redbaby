package com.bage.redbaby.domain;

import java.util.List;
/**
 * 促销快报
 * @author LiuDong
 *
 */
public class TopicInfo {
	private String response;
	private List<Topic> topic;
	public TopicInfo() {
		super();
	}
	public TopicInfo(String response, List<Topic> topic) {
		super();
		this.response = response;
		this.topic = topic;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public List<Topic> getTopic() {
		return topic;
	}
	public void setTopic(List<Topic> topic) {
		this.topic = topic;
	}
	@Override
	public String toString() {
		return "TopicInfo [response=" + response + ", topic=" + topic + "]";
	}
	
}
