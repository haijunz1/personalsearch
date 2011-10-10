package g1105.ps.service;


import g1105.ps.dao.UserDAO;
import g1105.ps.entity.User;
import g1105.ps.utils.Encrypter;

public class UserService
{
	private UserDAO userDAO;

	public UserService(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	public void addUser(User user)
	{
		user.setPassword(Encrypter.md5(user.getPassword()));
		userDAO.addUser(user);
	}


	public boolean verifyUser(User user)
	{
		String password = userDAO.getPassword(user);
		if (password == null)
			return false;
		if (password.equals(Encrypter.md5(user.getPassword())))
			return true;
		return false;
	}

	public Integer getUserId(String email) {
		return userDAO.getUserId(email);
	}
	public User getUser(User user){
		return userDAO.getUser(user);
	}
}
