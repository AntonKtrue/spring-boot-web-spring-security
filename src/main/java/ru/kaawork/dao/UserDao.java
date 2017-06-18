package ru.kaawork.dao;

import ru.kaawork.model.User;

import java.util.List;

/**
 * Created by user on 18.06.17.
 */
public interface UserDao {
    User findById(int id);
    User findBySSO(String sso);
    void save(User user);
    void deleteBySSO(String sso);
    List<User> findAllUsers();
}
