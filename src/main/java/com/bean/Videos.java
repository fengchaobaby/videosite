package com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "DataValue")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(name = "DataValue",namespace="http://vo.video.com/")
public class Videos implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3629310060522559595L;
	private List<Video> list;

	public List<Video> getList() {
		return list;
	}

	public void setList(List<Video> list) {
		this.list = list;
	}
	
	
	
}
