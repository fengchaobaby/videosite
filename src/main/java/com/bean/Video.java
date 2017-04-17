package com.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Video")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(name = "Video",namespace="http://vo.video.com/")
public class Video implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1124645839749653757L;
	private String videoPath;  
	private String videoName;  
	private String releaseTime;  
	private String column;  
	private int zanNum; 
	private int nozanNum; 
	
	
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
	public int getZanNum() {
		return zanNum;
	}
	public void setZanNum(int zanNum) {
		this.zanNum = zanNum;
	}
	public int getNozanNum() {
		return nozanNum;
	}
	public void setNozanNum(int nozanNum) {
		this.nozanNum = nozanNum;
	}
	
	

}
