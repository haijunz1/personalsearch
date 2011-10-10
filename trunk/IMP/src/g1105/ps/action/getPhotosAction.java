package g1105.ps.action;

import java.net.URLDecoder;
import java.util.List;

import g1105.ps.dao.PhotoDAO;
import g1105.ps.entity.Photo;
import g1105.ps.entity.Query;
import g1105.ps.utils.TranCharset;

public class getPhotosAction{
	private PhotoDAO photoDAO;
	private List<Photo> photos;
	private String queryString;
	private String ajax="false";
	private boolean success;

	public boolean isSuccess()
	{
		return success;
	}
	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public String execute()
	{
		try
		{
			this.queryString = new String(this.queryString.getBytes("iso8859-1"), "utf-8");
			Query query = new Query(this.queryString);
			photos = photoDAO.getPhotoByQuery(query,40);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		success=true;
		if (ajax.equals("true")){
			return "ajaxsuccess";
		}
		else{
			return "success";
		}
		
		
	}
	public String getQueryString() {		
		return queryString;
	}
	public String getAjax() {
		return ajax;
	}
	public void setAjax(String ajax) {
		this.ajax = ajax;
	}
}
