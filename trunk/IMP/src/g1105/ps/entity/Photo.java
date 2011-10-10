package g1105.ps.entity;

import java.util.Date;

public class Photo {
	private String id;
	private String tags;
	private String path;
	private String latitude;
	private String longitude;
	private String source;
	private String name;
	private Integer ownerId;
	private Date uploadDate;
	
	private String ownerName; //为了处理手机端问题而加的，在数据库表中没有该项
	
	private String nprank;
	private String prank;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getNprank() {
		return nprank;
	}

	public void setNprank(String nprank) {
		this.nprank = nprank;
	}

	public String getPrank() {
		return prank;
	}

	public void setPrank(String prank) {
		this.prank = prank;
	}
}
