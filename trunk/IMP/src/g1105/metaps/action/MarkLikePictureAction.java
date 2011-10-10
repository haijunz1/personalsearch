package g1105.metaps.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import g1105.metaps.dao.User_likedPictureDAO;
import g1105.metaps.entity.User_likedPicture;
import g1105.metaps.key.User_likedPicturePK;
import g1105.ps.service.UserService;

public class MarkLikePictureAction implements SessionAware{
	private Map<String,Object> session;
	private UserService userService;
	private User_likedPictureDAO userLikedPictureDAO;
	private String pictureId;
	private boolean ok = true;
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public User_likedPictureDAO getUserLikedPictureDAO() {
		return userLikedPictureDAO;
	}
	public void setUserLikedPictureDAO(User_likedPictureDAO userLikedPictureDAO) {
		this.userLikedPictureDAO = userLikedPictureDAO;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String execute(){
		try{
			String userEmail = session.get("SessionName").toString();
			Integer userId = userService.getUserId(userEmail);
			
			User_likedPicturePK userLikedPicturePK = new User_likedPicturePK();
			userLikedPicturePK.setUserId(userId);
			userLikedPicturePK.setPictureId(pictureId);
			
			User_likedPicture userLikedPicture = new User_likedPicture();
			userLikedPicture.setUserLikedPicturePK(userLikedPicturePK);
			
			userLikedPictureDAO.addARecord(userLikedPicture);
		}catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		return "result";
	}

}
