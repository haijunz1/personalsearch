package g1105.metaps.key;

import java.io.Serializable;

public class User_likedPicturePK implements Serializable {
	private Integer userId;
	private String pictureId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	public boolean equals(Object obj){
		return (obj instanceof User_likedPicturePK)
			&&(this.getUserId().equals(((User_likedPicturePK)obj).getUserId()))
			&&(this.getPictureId().equals(((User_likedPicturePK)obj).getPictureId()));
	}
	public int hashCode(){
		return this.getUserId().hashCode() ^ this.getPictureId().hashCode();
	}

}
