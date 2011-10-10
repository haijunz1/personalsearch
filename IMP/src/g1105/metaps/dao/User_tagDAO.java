package g1105.metaps.dao;


import g1105.metaps.entity.Tag;
import g1105.metaps.entity.User_tag;
import g1105.ps.dao.ParentDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class User_tagDAO extends ParentDAO {

	public User_tagDAO(HibernateTemplate template) {
		super(template);
	}

	public void addOrUpdate(User_tag userTag) {
		Integer weigthTemp = 1;
		User_tag userTagTemp = (User_tag) template.get(User_tag.class, userTag
				.getUserTagPK());
		if (userTagTemp != null) {
			weigthTemp += userTagTemp.getWeight();
		}
		userTag.setWeight(weigthTemp);
		template.saveOrUpdate(userTag);
	}

	public List<Tag> getUserTopNTagIds(Integer userId, int topN) {
		List<Tag> tags = new ArrayList<Tag>();
		String sql = "select tagId,weight from user_tag where userId="
				+ userId + " order by weight DESC limit " + topN;
		try {
			Session session = template.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			List<Object[]> cursor = session.createSQLQuery(sql).list();
			transaction.commit();
			session.close();
			Iterator<Object[]> iterator = cursor.iterator();
			while (iterator.hasNext()) {
				Object[] aRecord = iterator.next();
				Tag tag = new Tag();
				tag.setId((Integer) aRecord[0]);
				tag.setWeight((Integer) aRecord[1]);
				tags.add(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

}
