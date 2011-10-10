package g1105.ps.interceptor;

import g1105.ps.dao.UserDAO;
import g1105.ps.entity.User;
import g1105.ps.service.UserService;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, Object> session;
	private UserService userService;
	
	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		Map session = ai.getInvocationContext().getSession();
		String userEmail  = (String) session.get("activeUser");
		if(userEmail!=null && userEmail.length()>0){
			return ai.invoke();
		}
		else{
			ActionContext  ac = ai.getInvocationContext();
			ac.put("popedom", "not login!");
			return "notLogin";
		}
	}

}
