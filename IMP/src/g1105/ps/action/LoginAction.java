package g1105.ps.action;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import g1105.ps.entity.User;
import g1105.ps.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User>,
		SessionAware, ServletRequestAware, ServletResponseAware {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private User user = new User();
	private UserService userService;
	private Map<String, Object> session;
	private String msg;
	private boolean success;
	
	/*added by lrs 2011.10.3*/
	private String email;
	private String password;
	

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

	public boolean isSuccess() {
		return success;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public User getModel() {
		return user;
	}

	public String getMsg() {
		return msg;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String execute() {
		try {
			String sessionValidationCode = (String) session
					.get("validationCode");

			if (!user.getValidationCode().equalsIgnoreCase(
					sessionValidationCode)) {
				success = false;
				msg = "校验码错误!";
				return "result";
			}
			if (userService.verifyUser(user)) {
				success = true;
				int time = 60 * 10;
				switch (user.getKeepLoginTime()) {
				case 1:
					time = 24 * 3600;
					break;
				case 2:
					time = 24 * 3600 * 7;
					break;
				case 3:
					time = 24 * 3600 * 30;
					break;
				case 4:
					time = 24 * 3600 * 365;
					break;
				}
				if (time > 0) {
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(time);
					session.setAttribute("SessionName", user.getEmail());
					Cookie cookie = new Cookie("impCookie", user.getEmail()
							+ "###" + user.getPassword());
					cookie.setMaxAge(time);
					response.addCookie(cookie);
				}
				session.put("user", user);
				msg = "登录成功！";
				success = true;
				return "result";
			} else {
				success = false;
				msg = "登录失败！";
				return "result";
			}

		} catch (Exception e) {
			success = false;
			msg = "登录失败";
			return "result";
		}
	}
}
