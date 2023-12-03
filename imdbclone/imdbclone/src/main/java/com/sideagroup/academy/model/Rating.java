package com.sideagroup.academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double averageRating;
    @Column(nullable = false)
    private Integer numVotes;

    @OneToOne
    @JoinColumn(name="movie_id", referencedColumnName = "id", unique = true)
    private Movie movie;

}
