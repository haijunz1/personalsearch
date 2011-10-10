package g1105.metaps.key;

import java.io.Serializable;
import org.apache.commons.*;

public class User_tagPK implements Serializable{
	private Integer userId;
	private Integer tagId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public boolean equals(Object obj){
		return (obj instanceof User_tagPK)
			&&(this.getTagId().equals(((User_tagPK)obj).getTagId()))
			&&(this.getUserId().equals(((User_tagPK)obj).getUserId()));
	}
	public int hashCode(){
		return this.getTagId().hashCode() ^ this.getUserId().hashCode();
	}
}
