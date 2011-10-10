package g1105.ps.servlet;

import g1105.ps.entity.User;
import g1105.ps.service.UserService;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class getSessionServlet  extends HttpServlet {
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		if (session == null){
			response.getWriter().write("nologin");
			return;
		}
		String userEmail = (String)session.getAttribute("SessionName");
		if(userEmail !=  null){
			response.getWriter().write(userEmail);			
		}
		else{
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {  
				for (Cookie cookie : cookies) {  
					if ("impCookie".equals(cookie.getName())){  
						String value = cookie.getValue();  
						if (StringUtils.isNotBlank(value)) {  
							String[] split = value.split("###");  
							String useremail = split[0];  
							String password = split[1];  
							User user = new User();
							user.setEmail(useremail);
							user.setPassword(password);
							if(userService.verifyUser(user)){
								HttpSession httpSession = request.getSession();
								httpSession.setAttribute("SessionName", user.getEmail());
								response.getWriter().write(useremail);								
							}
						}
					}  
				}              
			}
			response.getWriter().write("nologin");			
		}		
	}
	
}
