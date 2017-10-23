package it.relatech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.relatech.dao.UserDao;
import it.relatech.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	@Override
	public List<User> getListUsers() {
		return userDao.getListUsers();
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		return userDao.getUserByUsernameAndPassword(username, password);
	}

	@Override
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public void deleteUserById(int id) {
		userDao.deleteUserById(id);
	}

}
