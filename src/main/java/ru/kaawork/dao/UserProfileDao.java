package ru.kaawork.dao;

import ru.kaawork.model.UserProfile;

import java.util.List;

/**
 * Created by user on 18.06.17.
 */
public interface UserProfileDao {
    List<UserProfile> findAll();
    UserProfile findByType(String type);
    UserProfile findById(int id);
}
