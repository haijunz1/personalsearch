package g1105.ps.action;

import g1105.metaps.dao.AlbumDAO;
import g1105.metaps.entity.Album;
import g1105.ps.constant.Constant;
import g1105.ps.entity.User;
import g1105.ps.service.UserService;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RegisterAction extends ActionSupport implements ModelDriven<User>,
		SessionAware
{
	private User user = new User();
	private UserService userService;
	private AlbumDAO albumDAO;
	private Map<String, Object> session;
	private String msg;
	private boolean success;

	public boolean isSuccess()
	{
		return success;
	}

	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	public User getModel()
	{
		return user;
	}

	public String getMsg()
	{
		return msg;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String execute()
	{
		try
		{
			String sessionValidationCode = (String) session
					.get("validationCode");

			if (!user.getValidationCode().equalsIgnoreCase(
					sessionValidationCode))
			{
				success = false;
				msg = "校验码错误!";
				return "reslut";
			}
			userService.addUser(user);
			
			/*为用户添加默认的手机相册和收藏相册*/
			Integer userId = userService.getUserId(user.getEmail());
			Date date = new Date();
			Album album = new Album();
			album.setUserId(userId);
			album.setCreateTime(date);
			album.setName(Constant.phoneAlbum);
			albumDAO.addAlbum(album);
			
			album.setName(Constant.collectionAlbum);
			albumDAO.addAlbum(album);
			/**/
			
			success = true;
			msg = "注册成功";
			session.put("SessionName", user.getEmail());
			return "reslut";
		}
		catch (Exception e)
		{
			success = false;
			e.printStackTrace();
			msg = "注册失败";
			return "reslut";
		}
		
	}


}
