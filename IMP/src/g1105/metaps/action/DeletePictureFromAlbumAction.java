package g1105.metaps.action;

import g1105.metaps.dao.AlbumDAO;
import g1105.metaps.dao.Album_PictureDAO;
import g1105.metaps.entity.Album;
import g1105.metaps.entity.Album_picture;
import g1105.metaps.key.Album_picturePK;
import g1105.ps.constant.Constant;
import g1105.ps.service.UserService;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;



public class DeletePictureFromAlbumAction implements SessionAware{
	private Map<String,Object> session;
	private Integer albumId;
	private String pictureId;
	private UserService userService;
	private AlbumDAO albumDAO;
	private Album_PictureDAO albumPictureDAO;
	private boolean ok = false;
	
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public Album_PictureDAO getAlbumPictureDAO() {
		return albumPictureDAO;
	}

	public void setAlbumPictureDAO(Album_PictureDAO albumPictureDAO) {
		this.albumPictureDAO = albumPictureDAO;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
	/*传入albumId和pictureId*/
	public String execute() {
		try{
			String userEmail = session.get("SessionName").toString();
			Integer userId  = userService.getUserId(userEmail);
			Album album = albumDAO.getAlbumById(albumId);//根据albumId 返回整条记录
			/*判断当前用户是否为该album的拥有者*/
			if(!album.getUserId().equals(userId)){
				ok = false;
				return "result";
			}
			
			Album_picture albumPicture = new Album_picture();
			Album_picturePK albumPicturePK = new Album_picturePK();
			albumPicturePK.setAlbumId(albumId);
			albumPicturePK.setPictureId(pictureId);
			albumPicture.setAlbumPicturePK(albumPicturePK);
			
			albumPictureDAO.deletePictureFromAlbum(albumPicture);
			ok = true;
		}catch (Exception e) {
			e.printStackTrace();
			ok = false;
		}
		return "result";
	}
}
