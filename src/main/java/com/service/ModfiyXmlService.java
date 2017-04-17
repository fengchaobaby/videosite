package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.bean.Comment;
import com.bean.Video;


@Service("modxmlService")
public class ModfiyXmlService implements Contant{
	
	public int mod(Video video) {
        InputStream is = null;
        Integer temp = 0;
        try {
        	File file = new File( VIDEO_ROOT_PATH + video.getColumn() + FILESUX);
        	is =  new FileInputStream(file);

           // is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
            //创建文档处理对象
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            //通过DocumentBuilder创建doc的文档对象
            Document doc = db.parse(is);
            //创建XPath
            XPath xpath = XPathFactory.newInstance().newXPath();
            Transformer tran = TransformerFactory.newInstance().newTransformer();
            tran.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            tran.setOutputProperty(OutputKeys.INDENT, "yes");
            //第一个参数就是xpath,第二参数就是文档
            /*
             *<bookstore>
             *<book category="WEB">
             *<title lang="en">Learning XML</title>
             *<author>Erik T. Ray</author>
             *<year>2003</year>
             *<price>39.95</price>
             *</book>
             *</bookstore>
             **/
            StringBuffer sbf = new StringBuffer();
            sbf.append( "//list[videoName='");
            sbf.append(video.getVideoName());
            sbf.append( "']");
            
            NodeList list = (NodeList)xpath.evaluate(sbf.toString(), doc,XPathConstants.NODESET);
            //获取price节点
            Element be = (Element)list.item(0);
            Element e = (Element)(be.getElementsByTagName("zanNum").item(0));
             temp = Integer.valueOf(e.getTextContent()) + 1;
            e.setTextContent(temp.toString());
            Result result = new StreamResult(new PrintWriter(file));
            //通过tranformer修改节点
            tran.transform(new DOMSource(doc), result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is!=null) is.close();
            } catch (IOException e) {
            	 e.printStackTrace();
            }
        }
		return temp;
    }
	
	
}
