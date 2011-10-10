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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class GetAlbumAllPicturesAction implements SessionAware{
	private Map<String,Object> session;
	private UserService userService;
	private AlbumDAO albumDAO;
	private Album_PictureDAO albumPictureDAO;
	private PhotoDAO photoDAO;
	private Integer albumId;
	private List<Photo> photos;
	
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
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


	public Integer getAlbumId() {
		return albumId;
	}


	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}


	public List<Photo> getPhotos() {
		return photos;
	}


	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}


	/*给定 albumId查询其所有picture*/
	public String execute(){
		try{
			/*如果每个给定albumId,默认为phoneAlbum的Id*/
			if(albumId == null){
				String userEmail = session.get("SessionName").toString();
				int userId = userService.getUserId(userEmail);
				Album album = new Album();
				album.setUserId(userId);
				album.setName(Constant.phoneAlbum);
				List<Album> albums = albumDAO.getAlbum(album);
				albumId = albums.get(0).getId();
			}
			
			photos = new ArrayList<Photo>();
			Album_picture albumPicture = new Album_picture();
			Album_picturePK albumPicturePK = new Album_picturePK();
			albumPicturePK.setAlbumId(albumId);
			albumPicture.setAlbumPicturePK(albumPicturePK);
			
			List<Album_picture> albumPictures = albumPictureDAO.getAlbumPictures(albumPicture);
			for(int i=0; i<albumPictures.size(); i++){
				String picId = albumPictures.get(i).getAlbumPicturePK().getPictureId();
				Photo photo = new Photo();
				photo.setId(picId);
				photo = photoDAO.getPhoto(photo);
				photos.add(photo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "result";
	}
	
}
