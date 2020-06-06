package dao;

import java.util.List;

import bean.User;

public interface UserDao {

	List<User> searchUser(int id);
}
