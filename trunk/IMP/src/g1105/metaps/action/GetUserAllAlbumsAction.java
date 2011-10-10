package g1105.metaps.action;


import g1105.metaps.dao.AlbumDAO;
import g1105.metaps.entity.Album;
import g1105.ps.service.UserService;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class GetUserAllAlbumsAction implements SessionAware{
	private Map<String, Object> session;
	private UserService userService;
	private AlbumDAO albumDAO;
	private List<Album> albums;
	
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	public String execute(){
		try{
			String userEmail = session.get("SessionName").toString();
			Integer userId = userService.getUserId(userEmail);
			albums = albumDAO.getUserAllAlbums(userId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "result";
	}

}
