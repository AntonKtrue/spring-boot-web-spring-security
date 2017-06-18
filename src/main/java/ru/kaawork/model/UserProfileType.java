package ru.kaawork.model;

import java.io.Serializable;

/**
 * Created by user on 18.06.17.
 */
public enum UserProfileType implements Serializable {
    USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");

    String userProfileType;

    UserProfileType(String userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType() {
        return userProfileType;
    }
}
