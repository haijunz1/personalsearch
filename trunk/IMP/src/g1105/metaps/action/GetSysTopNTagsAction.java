package g1105.metaps.action;

import g1105.metaps.dao.TagDAO;
import g1105.metaps.entity.Tag;
import g1105.ps.constant.Constant;

import java.util.List;

public class GetSysTopNTagsAction{
	private int topN = Constant.topN;
	private TagDAO tagDAO;
	private List<Tag> topNTags;

	
	public int getTopN() {
		return topN;
	}


	public void setTopN(int topN) {
		this.topN = topN;
	}


	public TagDAO getTagDAO() {
		return tagDAO;
	}


	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}


	public List<Tag> getTopNTags() {
		return topNTags;
	}


	public void setTopNTags(List<Tag> topNTags) {
		this.topNTags = topNTags;
	}


	public String execute(){
		try{
			topNTags = tagDAO.getSysTopNTags(topN);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "result";
	}
}
