package com.bean;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "DataValue")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(name = "DataValue",namespace="http://vo.video.com/")
public class HotTips implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6618500390222327467L;
	
	private String fillMan;
	private String hotTipsName;
	private String hotTipsTitle;
	private Map<String,Integer> contextAndanswer;
	
	
	
	public String getHotTipsTitle() {
		return hotTipsTitle;
	}
	public void setHotTipsTitle(String hotTipsTitle) {
		this.hotTipsTitle = hotTipsTitle;
	}
	public String getFillMan() {
		return fillMan;
	}
	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}
	public String getHotTipsName() {
		return hotTipsName;
	}
	public void setHotTipsName(String hotTipsName) {
		this.hotTipsName = hotTipsName;
	}
	public Map<String, Integer> getContextAndanswer() {
		return contextAndanswer;
	}
	public void setContextAndanswer(Map<String, Integer> contextAndanswer) {
		this.contextAndanswer = contextAndanswer;
	}
	
	
	
}
