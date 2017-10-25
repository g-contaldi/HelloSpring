package it.relatech.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.relatech.model.User;

@Repository
@Transactional
public class UserDaoImpl extends AbstractDao implements UserDao {

	@Override
	public User saveUser(User user) {
		persist(user);
		return user;
	}

	@Override
	public User getUserById(int id) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		return (User) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getListUsers() {
		return getSession().createCriteria(User.class).list();
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		return (User) criteria.uniqueResult();
	}

	@Override
	public User updateUser(User user) {
		update(user);
		return null;
	}

	@Override
	public void deleteUserById(int id) {
		delete(getUserById(id));
	}

	@Override
	public boolean userExist(User user) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", user.getUsername()));
		User result = (User) criteria.uniqueResult();
		if (result != null)
			return true;
		else
			return false;
	}

}
