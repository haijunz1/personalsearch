package g1105.ps.action;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements SessionAware{
	private Map<String,Object> session;
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}
	public String execute(){
		session.remove("SessionName");
		deleteCookie();
		return "result";
	}
	
	private void deleteCookie(){
		HttpServletRequest request = (HttpServletRequest) ActionContext  
		             .getContext().get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ActionContext  
		                         .getContext().get(StrutsStatics.HTTP_RESPONSE); 
		Cookie[] cookies=request.getCookies();
		for (int i = 0; i < cookies.length; i++){
			String  value=cookies[0].getName();
			if(value.equals("impCookie"))      
			{      
				cookies[i].setMaxAge(0);      
				response.addCookie(cookies[i]);    
			} 
		}
	}
}
