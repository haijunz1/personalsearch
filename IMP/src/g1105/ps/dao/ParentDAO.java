package g1105.ps.dao;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class ParentDAO {
	protected SessionFactory sessionFactory;
	protected HibernateTemplate template;

	public ParentDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ParentDAO(HibernateTemplate template) {
		this.template = template;
	}

}
