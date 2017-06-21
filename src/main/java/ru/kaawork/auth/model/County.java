package ru.kaawork.auth.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by user on 21.06.17.
 */
@Entity
@Table(name = "countries")
public class County {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name="name", unique=true, nullable=false)
    private String name;

}
