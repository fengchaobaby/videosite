package com.bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "DataValue")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(name = "DataValue",namespace="http://vo.video.com/")
public class Comments implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4383569855957432546L;
	
	@XmlElement(name="commentList")
	private ArrayList<Comment> list = new ArrayList<Comment>();
	public ArrayList<Comment> getList() {
		return list;
	}

	public void setList(ArrayList<Comment> list) {
		this.list = list;
	}
	
	public void addData(Comment comment){
		
		this.list.add(comment);
	}
	
	public int getSize(){
		
		return this.list.size();
	}
}
