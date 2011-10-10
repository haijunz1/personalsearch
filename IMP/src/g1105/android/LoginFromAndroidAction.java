package g1105.android;

import g1105.ps.entity.User;
import g1105.ps.service.UserService;

public class LoginFromAndroidAction {
	private String email;
	private String password;
	private UserService userService;
	private boolean success;
	private Integer userId;
	private String userName;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String execute(){
		if(email != null){
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			if(userService.verifyUser(user)){
				success = true;
				
				User user1 = new User();
				user1.setEmail(email);
				user1 = userService.getUser(user1);
				userId = user1.getId();
				userName = user1.getName();
			}
			else{
				success = false;
			}
		}
		return "result";
	}

}
