package g1105.android;

import g1105.ps.constant.Constant;
import g1105.ps.dao.PhotoDAO;
import g1105.ps.entity.Photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class DownloadPictureContentToAndroidAction {
	private String picId;
	private PhotoDAO photoDAO;
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public PhotoDAO getPhotoDAO() {
		return photoDAO;
	}
	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}
	public String execute(){
		Photo photo = new Photo();
		photo.setId(picId);
		photo = photoDAO.getPhoto(photo);
		String picPath = photo.getPath();
		picPath = picPath.split("images")[1];
		File file = new File(Constant.downloadDir + picPath);
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] picData = new byte[(int) file.length()];
			fileInputStream.read(picData);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getOutputStream().write(picData);
			response.getOutputStream().close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
