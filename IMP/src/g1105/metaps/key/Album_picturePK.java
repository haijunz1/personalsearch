package g1105.metaps.key;

import java.io.Serializable;

public class Album_picturePK implements Serializable{
	private Integer albumId;
	private String pictureId;
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
	public boolean equals(Object obj){
		return (obj instanceof Album_picturePK)
			&&(this.getAlbumId().equals(((Album_picturePK)obj).getAlbumId()))
			&&(this.getPictureId().equals(((Album_picturePK)obj).getPictureId()));
	}
	public int hashCode(){
		return this.getAlbumId().hashCode() ^ this.getPictureId().hashCode();
	}
}
