package g1105.metaps.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import g1105.metaps.entity.User_likedPicture;
import g1105.ps.dao.ParentDAO;

public class User_likedPictureDAO extends ParentDAO{

	public User_likedPictureDAO(HibernateTemplate template) {
		super(template);
	}
	public void addARecord(User_likedPicture userLikedPicture){
		template.save(userLikedPicture);	
	}
}
