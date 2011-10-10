package g1105.metaps.action;

import g1105.metaps.dao.AlbumDAO;
import g1105.metaps.entity.Album;
import g1105.ps.service.UserService;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class DeleteAlbumAction implements SessionAware{
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
			Album album = new Album();
			album.setName(albumName);
//			album.setId(Integer.parseInt(albumName));
			album.setUserId(userId);
			List<Album> albums = albumDAO.getAlbum(album);
			for(Album album1:albums){
				albumDAO.deleteAlbum(album1);
			}
		}catch (Exception e) {
			operateSuccess = false;
			e.printStackTrace();
		}
		return "result";
	}
}
