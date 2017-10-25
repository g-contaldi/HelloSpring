package it.relatech.dao;

import java.util.List;

import it.relatech.model.User;

public interface UserDao {

	User saveUser(User user);

	User getUserById(int id);

	List<User> getListUsers();

	User getUserByUsernameAndPassword(String username, String password);

	User updateUser(User user);

	void deleteUserById(int id);

	boolean userExist(User user);

}
