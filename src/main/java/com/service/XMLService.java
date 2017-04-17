package com.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bean.Comment;
import com.bean.Comments;
import com.bean.HotTips;
import com.bean.Video;
import com.bean.Videos;

@Service("xmlService")
public class XMLService implements Contant {
	
	/**
	 * 添加一条评论
	 * @param comment 
	 * @throws FileNotFoundException
	 */
	public void addComment(Comment comment) throws FileNotFoundException{
		
		Comments comments = new Comments();
		File file = new File( COMMENT_ROOT_PATH + comment.getVideoId() + FILESUX);
		   if (!file.exists()) {	
			   comments.addData(comment);		   
			   XmlBeanSwitch.beanToXML(comments, file);     
		   }else{			   
			    comments = (Comments) XmlBeanSwitch.XMLStringToBean(Comments.class, file);
			   comments.addData(comment);
			   file.delete();
			   XmlBeanSwitch.beanToXML(comments, file);   
		   }	 		   
	}
	/**
	 * 新添加一个video
	 * @param video
	 * @throws FileNotFoundException
	 */
	public void addVideo(Video video) throws FileNotFoundException{
		
		Videos videos = new Videos();
		File file = new File( VIDEO_ROOT_PATH + video.getColumn() + FILESUX);
		   if (!file.exists()) {
			   List<Video> list = new ArrayList<Video>();
			   list.add(video);
			   videos.setList(list);
			   XmlBeanSwitch.beanToXML(videos, file);     
		   }else{
			   videos =  (Videos) XmlBeanSwitch.XMLStringToBean(Videos.class, file);
			   List<Video> list = videos.getList();
			   list.add(video);
			   videos.setList(list);
			   file.delete();
			   
			   XmlBeanSwitch.beanToXML(videos, file);   
		   }	 		   
	}
	
	public void addVideos(Videos videos) throws FileNotFoundException{
		
		if(videos.getList().size()>0 ){
			Video video = videos.getList().get(0);
			File file = new File( VIDEO_ROOT_PATH + video.getColumn() + FILESUX);
			   if (!file.exists()) {				
				   XmlBeanSwitch.beanToXML(videos, file);     
			   }else{				  
				   file.delete();				   
				   XmlBeanSwitch.beanToXML(videos, file);   
			   }	 		   
		}
	
	}
	/**
	 * 获得xml文件中的对象
	 * @param videoId
	 * @return
	 * @throws FileNotFoundException
	 */
	public Comments getComment(String videoId) throws FileNotFoundException {

		Comments com = new Comments();
		File file = new File(COMMENT_ROOT_PATH + videoId + FILESUX);
		if (file.exists()) {
			com = (Comments) XmlBeanSwitch.XMLStringToBean(Comments.class, file);

		}
		return com;

	}
	
	public List<Video> getVideo(String column) throws FileNotFoundException {

		Videos videos = new Videos();

		File file = new File(VIDEO_ROOT_PATH  + column + FILESUX);
		if (file.exists()) {
			videos = (Videos) XmlBeanSwitch.XMLStringToBean(Videos.class, file);

		}

		return videos.getList();
	}
	
	
	public HotTips getHotTips(HotTips hotTips) throws FileNotFoundException {
		
		File file = new File(HotTips_ROOT_PATH  + hotTips.getFillMan() +"/"+hotTips.getHotTipsName() + FILESUX);
		if (file.exists()) {
			hotTips = (HotTips) XmlBeanSwitch.XMLStringToBean(HotTips.class, file);
		}else {
			//原题
			File nativef = new File(HotTips_ROOT_PATH  + hotTips.getHotTipsName() + FILESUX);
			hotTips = (HotTips) XmlBeanSwitch.XMLStringToBean(HotTips.class, nativef);
			
		}

		return hotTips;
	}
	
	public void addHotTips(HotTips hotTips) throws FileNotFoundException {

		if (null != hotTips) {
			
			String fillMan = "";
			if(null!=hotTips.getFillMan()){
				
				fillMan = hotTips.getFillMan();
				File directory = new File(HotTips_ROOT_PATH + fillMan);
				if(!directory.exists()){
					try {
						directory.mkdirs();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
			File file = new File(HotTips_ROOT_PATH + fillMan +"/"+hotTips.getHotTipsName() + FILESUX);
			if (!file.exists()) {
				XmlBeanSwitch.beanToXML(hotTips, file);
			} else {
				file.delete();
				XmlBeanSwitch.beanToXML(hotTips, file);
			}
		}

	}
	
	/**
	 * 统计热帖
	 * @param tipsName
	 * @return
	 * @throws FileNotFoundException
	 */
	public Map<String,Integer> countHotTips(String tipsName) throws FileNotFoundException {
	
		Map<String,Integer> count = new HashMap<String,Integer>();
		List<HotTips> hlist = new ArrayList<HotTips>();
		HotTips hotTips = new HotTips();
		File file = new File(HotTips_ROOT_PATH);
		List<File> list = new ArrayList<File>();
		ergodic(file, list);
		
		 for(File b:list){
			 if (b.getName().equals(tipsName)){
				 hotTips = (HotTips) XmlBeanSwitch.XMLStringToBean(HotTips.class,b);
				 hlist.add(hotTips);				 				 
			 }
			 
		 }
		 
		 for(HotTips temp: hlist){
			 Map<String,Integer> tempMap = temp.getContextAndanswer();
			 addTo(count,tempMap);
		 }

		return count;
	}
	
	public Map<String,Integer> addTo(Map<String,Integer> target, Map<String,Integer>plus) {
        Object[] os = plus.keySet().toArray();
        String key = "";
        for (int i=0; i<os.length; i++) {
            key = (String)os[i];
            if (target.containsKey(key)) 
                target.put(key, target.get(key) + plus.get(key));
            else
                target.put(key, plus.get(key));
        }
        return target;
    }

	
	/**
	 * 
	 * @param file
	 * @param resultFileName
	 * @return
	 */
	private List<File> ergodic(File file,List<File> resultFileName){
        File[] files = file.listFiles();
        if(files==null)return resultFileName;// 判断目录下是不是空的
        for (File f : files) {
            if(f.isDirectory()){// 判断是否文件夹
                ergodic(f,resultFileName);// 调用自身,查找子目录
            }else
                resultFileName.add(f);
        }
        return  resultFileName;
    }
	
	
	 public static void main(String[] args) throws FileNotFoundException {
		 
		/* Comments comments = new Comments();
		 Comment comm = new Comment();
		 comm.setCommentContent("asdf");
		 comm.setCommentId("bbb");
		 comm.setCommentDate(new Date());
		 ArrayList<Comment> list = new ArrayList<Comment>();
		 list.add(comm);
		 comments.setList(list);
		 comments.setVideoId("test");
		 XMLService.addComment(comments);
		  
		 
		 Comments newcom = (Comments) getComment("test");
		 System.out.println(newcom.getVideoId());
		 }*/
		 
		/* Videos videos = new Videos();
		 List<Video> listVid = new ArrayList<Video>();
		 Video video = new Video();
		 video.setColumn("gaoxiao");
		 video.setNozanNum(0);
		 video.setReleaseTime("time");
		 video.setVideoName("茬儿二中");
		 video.setVideoPath("/static/media/1.mp4");
		 video.setZanNum(0);*/
		 /*HotTips a = new HotTips();
		 a.setHotTipsName("football");
		 Map b = new HashMap();
		 b.put("不能", 0);
		 b.put("能", 0);
		 a.setContextAndanswer(b);
		 XMLService xml = new XMLService();
		 xml.addHotTips(a);*/
		 File file = new File("D:\\hottips\\testaaa");
		 if(!file.exists()){
				try {
					file.mkdirs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		 
		
}
}