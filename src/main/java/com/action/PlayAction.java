package com.action;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bean.Comment;
import com.bean.Comments;
import com.bean.HotTips;
import com.bean.Video;
import com.bean.Videos;
import com.service.Contant;
import com.service.ModfiyXmlService;
import com.service.XMLService;

@Controller  
@RequestMapping("/play")
public class PlayAction {
	
	@Autowired
	XMLService xmlService;
	
	@Autowired
	ModfiyXmlService modxmlService;
	
	@RequestMapping(value="/getVideo",method=RequestMethod.GET)  
    public ModelAndView getVideo(@RequestParam("column")String column) { 
    	
		
		List<Video> vi = new ArrayList<Video>();
		try {
		    vi = xmlService.getVideo(column);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Map<String,Object> data = new HashMap<String,Object>();  
		     data.put("videoResult",vi);  

		return new ModelAndView("index",data);  
    } 
	/**
	 * @param videoName
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value="/toPlayTab",method = RequestMethod.GET)
		public ModelAndView toPlayTab(@RequestParam("videoname")String videoname,
				@RequestParam("videopath")String videopath) throws FileNotFoundException{
		
		Comments comments = xmlService.getComment(videoname);
		Map<String,Object> data = new HashMap<String,Object>();  
	     data.put("videopath",videopath);  
	     data.put("videoname",videoname); 
	     data.put("comments", comments.getList());
			return new ModelAndView("playTab",data);
	}
	/**
	 * @param model
	 * @param comment
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.GET)
	//@ResponseBody
	public @ResponseBody Map<String,Object> addComment(Comment comment) throws FileNotFoundException {
		 
		String uuid = UUID.randomUUID().toString(); 
		comment.setCommentId(uuid);
		comment.setCommenter(Contant.ANONYMOUS);
		comment.setCommentDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		xmlService.addComment(comment);

		return getComment(comment.getVideoId());

	}
	
	@RequestMapping(value = "/getComment", method = RequestMethod.GET)
	//@ResponseBody
	public @ResponseBody Map<String,Object> getComment(@RequestParam("videoId")String videoId ) throws FileNotFoundException {

		Comments comments =  xmlService.getComment(videoId);
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("comments", comments.getList());

		return data;

	}
	
	@RequestMapping(value="/zan",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> addZan(Video video){
		
		int returnZan = modxmlService.mod(video);
		Map<String,Object> data = new HashMap<String,Object>();  
	     data.put("returnZan",returnZan);  

	     return data;  
		
	}
	
	@RequestMapping(value="/deleteVideo",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> deleteVideo(Video video){
		
		Map<String,Object> data = new HashMap<String,Object>(); 
		List<Video> vi = new ArrayList<Video>();
		try {
		    vi = xmlService.getVideo(video.getColumn());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Video videos:vi){
			if(videos.getVideoName().equals(video.getVideoName())){
				vi.remove(videos);
				break;
			}
			
		}
		Videos videos = new Videos();
		videos.setList(vi);
		try {
			xmlService.addVideos(videos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data.put("returnDel","FAIL");
		}
		 
	     data.put("returnDel","OK");  
		return data;
		
	}
	
	
	
	@RequestMapping(value = "/addhotTips", method = RequestMethod.POST)
	public ModelAndView addHotTips(@RequestParam("contextAndanswer") String contextAndanswer,
			@RequestParam("fillMan") String fillMan, 
			@RequestParam("hotTipsName") String hotTipsName, 
			@RequestParam("hotTipsTitle") String hotTipsTitle, 
			@RequestParam("unCheckedBoxs") String unCheckedBoxs, 			
			@CookieValue("username") String cookie) {

		HotTips hottips = new HotTips();
		hottips.setFillMan(fillMan);
		hottips.setHotTipsName(hotTipsName);
		hottips.setHotTipsTitle(hotTipsTitle);
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		String[] answer = contextAndanswer.split(",");
		
		for(String temp:answer){
			
			map.put(temp, 1);
		}
		if (null!=unCheckedBoxs&&!unCheckedBoxs.equals("")){
			String[] unCheckedBoxss = unCheckedBoxs.split(",");
			for(String temp2:unCheckedBoxss){
				
				map.put(temp2, 0);
			}
		}
		hottips.setContextAndanswer(map);
		try {
			xmlService.addHotTips(hottips);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getHotTips(hottips, cookie);
	}

	/**
	 * 
	 * @param hottips
	 * @return
	 */
	@RequestMapping(value="/gethotTips",method = RequestMethod.GET)
	public ModelAndView getHotTips(HotTips hottips,@CookieValue("username") String cookie){

		
		HotTips result = new HotTips();
		hottips.setFillMan(cookie);
		Map<String,Integer> answer = new HashMap<String,Integer>();
		try {
			result = xmlService.getHotTips(hottips);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			 answer =  xmlService.countHotTips(hottips.getHotTipsName() + Contant.FILESUX);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> data = new HashMap<String,Object>();  
	     data.put("HottipsResult",result);
	     data.put("answer",answer);

	     return new ModelAndView("hotTips",data);  
	}

}
