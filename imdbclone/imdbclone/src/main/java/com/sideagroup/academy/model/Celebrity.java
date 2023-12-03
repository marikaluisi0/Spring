package com.sideagroup.academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Table(name="celebrity")
@Getter
@Setter
public class Celebrity {
    @Id
    @Column(length = 200)
    String id;
    @Column(name="primary_name", length =1000, nullable = false)
    private String primaryName;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy="celebrity") //usa il campo celebrity
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Set<MovieCelebrity> titles; //insieme di film in cui ha partecipato l'attore


}
