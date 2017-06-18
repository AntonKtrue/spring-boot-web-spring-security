package ru.kaawork.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 18.06.17.
 */
@Entity
@Table(name = "PERSISTENT_LOGINS")
public class PersistentLogin implements Serializable {
    @Id
    private String series;

    @Column(name="USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name="TOKEN", unique = true, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_userd;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLast_userd() {
        return last_userd;
    }

    public void setLast_userd(Date last_userd) {
        this.last_userd = last_userd;
    }
}

