package com.service;

import java.io.StringWriter;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JSONUtils {

	private static ObjectMapper objectMapper =  new ObjectMapper();

	static{
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);	
	}
	
	public static String fromObject(Object obj){
		StringWriter sw = new StringWriter();
		JsonGenerator jsonGenerator = null;
		try {
			objectMapper.disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(sw);
			jsonGenerator.writeObject(obj);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "";
	}

	public static <T> T toBean(String jsonStr,Class<T> clazz){
		T obj = null;
		try {
			//objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			obj = objectMapper.readValue(jsonStr, clazz);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static String xml2json(String xml) {  
		XMLSerializer xmlSerializer = new XMLSerializer();    
	    JSON json = xmlSerializer.read(xml);    
	    return json.toString();
    } 
	
	public static void main(String []args){
		
	}
}
