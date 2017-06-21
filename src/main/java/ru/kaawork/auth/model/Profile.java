package ru.kaawork.auth.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by user on 21.06.17.
 */
@Entity
@Table(name = "profile")
public class Profile {

    private Integer userId;
    private User user;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private Integer country_id;
    private Date birthDate;
    private String occupation;
    //@Email
    private String email;
    private String website;

    public Profile() {
    }

    public Profile(User user, String firstName, String lastName, String aboutMe, Integer country_id, Date birthDate, String occupation, String email, String website) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
        this.country_id = country_id;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.email = email;
        this.website = website;
    }

    public Integer getUserId() {
        return userId;
    }

    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "app_user"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "user_id", unique = true, nullable = false)
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
