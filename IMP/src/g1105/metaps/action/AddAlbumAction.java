package g1105.metaps.action;

import g1105.metaps.dao.AlbumDAO;
import g1105.metaps.entity.Album;
import g1105.ps.service.UserService;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

public class AddAlbumAction implements SessionAware{
	private Map<String,Object> session;
	private UserService userService;
	private AlbumDAO albumDAO;
	private String albumName;
	private boolean operateSuccess = true;
	
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public boolean isOperateSuccess() {
		return operateSuccess;
	}

	public void setOperateSuccess(boolean operateSuccess) {
		this.operateSuccess = operateSuccess;
	}

	public String execute(){
		try{
			String userEmail = session.get("SessionName").toString();
			int userId = userService.getUserId(userEmail);
			Date date = new Date();
			Album album = new Album();
			album.setName(albumName);
			album.setUserId(userId);
			album.setCreateTime(date);
			albumDAO.addAlbum(album);
			
		}catch (Exception e) {
			operateSuccess = false;
			e.printStackTrace();
		}
		if(true){
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
		}
		
		return "result";
	}
}
