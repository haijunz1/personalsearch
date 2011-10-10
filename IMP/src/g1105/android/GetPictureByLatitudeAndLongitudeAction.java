package g1105.android;

import g1105.ps.dao.PhotoDAO;
import g1105.ps.dao.UserDAO;
import g1105.ps.entity.Photo;
import g1105.ps.entity.User;

import java.util.List;

public class GetPictureByLatitudeAndLongitudeAction {
	private String latitude;
	private String longitude;
	private PhotoDAO photoDAO;
	private UserDAO userDAO;
	private List<Photo> photos;
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public PhotoDAO getPhotoDAO() {
		return photoDAO;
	}
	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	public String execute(){
		try{
			photos = photoDAO.getPhotoByLatitudeAndLongitude(latitude, longitude);
			for(Photo photo:photos){
				User user = new User();
				user.setId(photo.getOwnerId());
				user = userDAO.getUser(user);
				photo.setOwnerName(user.getName());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "result";
	}
}
