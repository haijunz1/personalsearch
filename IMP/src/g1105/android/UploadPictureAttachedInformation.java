package g1105.android;

import org.hibernate.dialect.IngresDialect;

import g1105.metaps.dao.AlbumDAO;
import g1105.metaps.dao.Album_PictureDAO;
import g1105.metaps.dao.TagDAO;
import g1105.metaps.dao.User_tagDAO;
import g1105.metaps.entity.Album;
import g1105.metaps.entity.Album_picture;
import g1105.metaps.entity.Tag;
import g1105.metaps.entity.User_tag;
import g1105.metaps.key.Album_picturePK;
import g1105.metaps.key.User_tagPK;
import g1105.ps.constant.Constant;
import g1105.ps.dao.PhotoDAO;
import g1105.ps.entity.Photo;

public class UploadPictureAttachedInformation {
	private String picId;
	private String tags;
	private String ownerId;
	private String longitude;
	private String latitude;
//	private String createdTime;
	private PhotoDAO photoDAO;
	private AlbumDAO albumDAO;
	private Album_PictureDAO albumPictureDAO;
	private TagDAO tagDAO;
	private User_tagDAO userTagDAO;
	private Boolean success = false;
	
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	public PhotoDAO getPhotoDAO() {
		return photoDAO;
	}
	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
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
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String execute(){
		Photo photo = new Photo();
		
		String[] tagArray;
		if(!tags.equals("null")){
			
			
			tagArray = tags.split(Constant.tagSeperator);
			String strTags = "";
			for(String tagName:tagArray){
				strTags += tagName + " ";
				
				Tag tag = new Tag();
				tag.setTagName(tagName);
				tagDAO.addOrUpdateTag(tag);
				Integer tagId = tagDAO.getId(tag);
				
				User_tagPK userTagPK = new User_tagPK();
				userTagPK.setTagId(tagId);
				userTagPK.setUserId(Integer.parseInt(ownerId));
				
				User_tag userTag = new User_tag();
				userTag.setUserTagPK(userTagPK);
				userTagDAO.addOrUpdate(userTag);
			}
			photo.setTags(strTags);
		}
		photo.setId(picId);
		photo.setPath(Constant.flickRefer+picId);
		photo.setOwnerId(Integer.parseInt(ownerId));
		photo.setLongitude(longitude);
		photo.setLatitude(latitude);
//		photo.setUploadDate(createdTime);
		photoDAO.addPhoto(photo);
		
		Album album = new Album();
		album.setUserId(Integer.parseInt(ownerId));
		album.setName(Constant.phoneAlbum);
		album = albumDAO.getAlbum(album).get(0);
		
		Integer albumId = album.getId();
		Album_picturePK albumPicturePK = new Album_picturePK();
		albumPicturePK.setAlbumId(albumId);
		albumPicturePK.setPictureId(picId);
		Album_picture albumPicture = new Album_picture();
		albumPicture.setAlbumPicturePK(albumPicturePK);
		albumPictureDAO.addPictureToAlbum(albumPicture);
		
		
		
		

		success = true;
		return "result";
	}
	
}
