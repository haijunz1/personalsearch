package g1105.metaps.action;



import g1105.metaps.dao.TagDAO;
import g1105.metaps.dao.User_tagDAO;
import g1105.metaps.entity.Tag;
import g1105.metaps.entity.User_tag;
import g1105.metaps.key.User_tagPK;
import g1105.ps.dao.PhotoDAO;
import g1105.ps.service.UserService;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class AddTagToPictureAction implements SessionAware{
	private Map<String,Object> session;
	private String picId;
	private String tag;
	private boolean ok = true;
	private PhotoDAO photoDAO;
	private UserService userService;
	private TagDAO tagDAO;
	private User_tagDAO userTagDAO;
	
	
	public Map<String, Object> getSession() {
		return session;
	}


	public void setSession(Map<String, Object> session) {
		this.session = session;
	}


	public String getPicId() {
		return picId;
	}


	public void setPicId(String picId) {
		this.picId = picId;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public boolean isOk() {
		return ok;
	}


	public void setOk(boolean ok) {
		this.ok = ok;
	}


	public PhotoDAO getPhotoDAO() {
		return photoDAO;
	}


	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public TagDAO getTagDAO() {
		return tagDAO;
	}


	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}


	public User_tagDAO getUserTagDAO() {
		return userTagDAO;
	}


	public void setUserTagDAO(User_tagDAO userTagDAO) {
		this.userTagDAO = userTagDAO;
	}


	/**/
	public String execute(){
		try{
			Tag tagEntity = new Tag();
			tagEntity.setTagName(tag);
			User_tag userTag = new User_tag();
			User_tagPK userTagPK = new User_tagPK();
			
			/*把标签加到对应的图片记录中*/
			photoDAO.addTagToPhoto(picId, tag);
			
			/*如果标签不在tag表中，则加入；否则，更新tag表中相应标签的weight值*/
			tagDAO.addOrUpdateTag(tagEntity);
			
			/*更改user_tag表中，相应记录的weight值*/
			String userEmail = session.get("SessionName").toString();
			Integer userId = userService.getUserId(userEmail);
			Integer tagId = tagDAO.getId(tagEntity);
			userTagPK.setUserId(userId);
			userTagPK.setTagId(tagId);
			userTag.setUserTagPK(userTagPK);
			userTagDAO.addOrUpdate(userTag);
		}catch (Exception e) {
			e.printStackTrace();
			ok = false;
		}
		return "result";
	}
}
