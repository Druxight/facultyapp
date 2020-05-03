package ua.nure.malyshevskyi.SummaryTask4.dao;

import java.util.List;

import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;

public interface UserDao {

	public List<User> getAllUsers() throws DBException;

	public User getUserById(long id) throws DBException;
	
	public User getUserByEmail(String email) throws DBException;
	
	public User getUserByLogin(String login) throws DBException;
	
	public List<User> getUsersByRole(int roleId) throws DBException;
	
	public long insertUser(User user) throws DBException;

	public void updateUser(User user) throws DBException;

	public int deleteUser(long userId) throws DBException;
	
}
