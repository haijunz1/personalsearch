package g1105.ps.dao;

import g1105.ps.entity.User;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class UserDAO extends ParentDAO {

	public UserDAO(HibernateTemplate template) {
		super(template);
	}

	public String getPassword(User user) {
		List<String> password = template.find(
				"select password from User where email=?", user.getEmail());
		if (password.size() > 0) {
			return password.get(0);
		}
		return null;
	}

	public void addUser(User user) {
		template.saveOrUpdate(user);
	}

	public Integer getUserId(String email) {
		Session session = template.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		String sql = "select UserId from user where UserEmail='" + email + "'";
		List<Object> lt = session.createSQLQuery(sql).list();
		transaction.commit();
		session.close();
		for (Object arr : lt) {
			String strId = arr.toString();
			return Integer.parseInt(strId);
		}
		return -1;
	}
	public User getUser(User user){
		return (User) template.findByExample(user).get(0);
	}

}
