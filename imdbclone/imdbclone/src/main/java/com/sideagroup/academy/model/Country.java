package com.sideagroup.academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="country")
public class Country {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length=1000, nullable = false)
    private String title;
    @Column(length=5)
    private String region;
    @Column(length = 5)
    private String language;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;


}
