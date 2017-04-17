package com.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.bean.Video;

public class XmlBeanSwitch {
 
    public static void beanToXML(Object obj, File file) throws FileNotFoundException {  
    
        try {

            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING,"utf-8");
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);//是否省略xml头信息（<?xml version="1.0" encoding="gb2312" standalone="yes"?>）
            marshaller.marshal(obj, new FileOutputStream(file));  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
  
    }  
      
    public static Object XMLStringToBean(Class<?> clazz,File file){  
        
    	Object obj  = new Object();
    	try {  
            JAXBContext context = JAXBContext.newInstance(clazz);  
            Unmarshaller unmarshaller = context.createUnmarshaller();           
            obj = unmarshaller.unmarshal(file);
           
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }
		return  obj;
          
    } 
    
   
    
    public static void main(String[] args ){
    	
    	String path = "D:/aaa.xml";
    	List list = new ArrayList();
    	Video vi = new Video();
    	vi.setNozanNum(1);
    	
    	vi.setVideoName("test");
    	vi.setVideoPath("path");
    	vi.setZanNum(1);
    	list.add(vi);
    	/*try {
			XmlBeanSwitch.beanToXML(vi, path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	
    }
} 