package g1105.metaps.action;

import g1105.metaps.dao.AlbumDAO;
import g1105.metaps.dao.Album_PictureDAO;
import g1105.metaps.entity.Album;
import g1105.metaps.entity.Album_picture;
import g1105.metaps.key.Album_picturePK;
import g1105.ps.constant.Constant;
import g1105.ps.dao.PhotoDAO;
import g1105.ps.entity.Photo;
import g1105.ps.service.UserService;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class AddPictureToAlbumAction implements SessionAware{
	private Map<String,Object> session;
	private Integer albumId;
	private String pictureId;
	private UserService userService;
	private AlbumDAO albumDAO;
	private Album_PictureDAO albumPictureDAO;
	private PhotoDAO photoDAO;
	private boolean ok = false;
	
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AlbumDAO getAlbumDAO() {
		return albumDAO;
	}

	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public Album_PictureDAO getAlbumPictureDAO() {
		return albumPictureDAO;
	}

	public void setAlbumPictureDAO(Album_PictureDAO albumPictureDAO) {
		this.albumPictureDAO = albumPictureDAO;
	}

	public PhotoDAO getPhotoDAO() {
		return photoDAO;
	}

	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/*传入albumId和pictureId*/
	public String execute() {
		/*先确定albumId和pictureId在数据库中存在。不过可以不判断albumId是否存在，因为album_picture有外键约束*/
		try{
			String userEmail = session.get("SessionName").toString();
			Integer userId = userService.getUserId(userEmail);
			if(albumId == null){
				String albumName = Constant.collectionAlbum;
				Album album = new Album();
				album.setName(albumName);
				album.setUserId(userId);
				List<Album> albums = albumDAO.getAlbum(album);
				albumId = albums.get(0).getId();
			}
			else{
				Album album = albumDAO.getAlbumById(albumId);//根据albumId 返回整条记录
				if(!album.getUserId().equals(userId)){
					ok = false;
					return "result";
				}
			}
			Photo photo = new Photo();
			photo.setId(pictureId);
			photo = photoDAO.getPhoto(photo);
			if(photo == null){
				ok = false;
				return "result";
			}
			else{
				Album_picture albumPicture = new Album_picture();
				Album_picturePK albumPicturePK = new Album_picturePK();
				albumPicturePK.setAlbumId(albumId);
				albumPicturePK.setPictureId(pictureId);
				albumPicture.setAlbumPicturePK(albumPicturePK);
				
				albumPictureDAO.addPictureToAlbum(albumPicture);
				ok = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			ok = false;
		}
		return "result";
		
	}

}
