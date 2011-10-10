package g1105.metaps.dao;


import g1105.metaps.entity.Tag;
import g1105.ps.dao.ParentDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class TagDAO extends ParentDAO {

	public TagDAO(HibernateTemplate template) {
		super(template);
	}

	public void addOrUpdateTag(Tag tag) {
		Integer tempWeight=1;
		List<Object[]> cursor = template.find("select a.id,a.weight from Tag a where a.tagName=?",tag.getTagName());
		if(cursor.size() > 0){
			tempWeight += (Integer)(cursor.get(0)[1]);
			tag.setId((Integer)(cursor.get(0)[0]));
		}
		tag.setWeight(tempWeight);
		template.saveOrUpdate(tag);
	}

	public Integer getId(Tag tag) {
		List cursor = template.find("select a.id from Tag a where a.tagName=?",tag.getTagName());
		return (Integer)cursor.get(0);
	}

	public String getTag(Tag tag) {
		List cursor = template.find("select a.tagName from Tag a where a.id=?",tag.getId());
		return (String)cursor.get(0);
	}
	public List<Tag> getSysTopNTags(int topN) {
		List<Tag> tags = new ArrayList<Tag>();
		String sql = "select * from tag order by weight DESC limit "+topN;
		try{	
//			List<Object[]> cursor = template.find("select a.id,a.tag,a.weight from Tag a order by a.weight limit "+topN);
			Session session = template.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			List<Object[]> cursor = session.createSQLQuery(sql).list();
			transaction.commit();
			session.close();
			Iterator<Object[]> iterator = cursor.iterator();
			while(iterator.hasNext()){
				Object[] aRecord = iterator.next();
				Tag tag = new Tag();
				tag.setId((Integer)aRecord[0]);
				tag.setTagName(aRecord[1].toString());
				tag.setWeight((Integer)aRecord[2]);
				tags.add(tag);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

}
