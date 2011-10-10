package g1105.metaps.entity;

import g1105.metaps.key.User_tagPK;

public class User_tag {
	private User_tagPK userTagPK;
	private Integer weight;
	public User_tagPK getUserTagPK() {
		return userTagPK;
	}
	public void setUserTagPK(User_tagPK userTagPK) {
		this.userTagPK = userTagPK;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}
