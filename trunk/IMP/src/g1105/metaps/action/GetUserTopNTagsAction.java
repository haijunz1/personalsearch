package g1105.metaps.action;

import g1105.metaps.dao.TagDAO;
import g1105.metaps.dao.User_tagDAO;
import g1105.metaps.entity.Tag;
import g1105.ps.constant.Constant;
import g1105.ps.service.UserService;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class GetUserTopNTagsAction implements SessionAware{
	private Map<String, Object> session;
	private UserService userService;
	private User_tagDAO userTagDAO;
	private TagDAO tagDAO;
	private int topN = Constant.topN;
	private List<Tag> topNTags;
	
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setUserTagDAO(User_tagDAO userTagDAO) {
		this.userTagDAO = userTagDAO;
	}
	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}
	public int getTopN() {
		return topN;
	}
	public void setTopN(int topN) {
		this.topN = topN;
	}
	public List<Tag> getTopNTags() {
		return topNTags;
	}
	public void setTopNTags(List<Tag> topNTags) {
		this.topNTags = topNTags;
	}

	public String execute(){
		try{
			String userEmail = session.get("SessionName").toString();
			Integer userId = userService.getUserId(userEmail);
		
			/*从user_tag表中找到用户的前topN个标签，这些标签没有tagName项，故还需要在tag表中找到对应的tagName项*/
			topNTags = userTagDAO.getUserTopNTagIds(userId,topN);
			for(int i=0; i<topNTags.size(); i++){
				Tag tag = topNTags.get(i);
				tag.setTagName(tagDAO.getTag(tag));
				topNTags.set(i, tag);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "result";
	}

}
