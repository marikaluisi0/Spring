package com.sideagroup.academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="movie_celebrity")
@Getter
@Setter
public class MovieCelebrity {

    @EmbeddedId
    private MovieCelebrityKey id;
    @ManyToOne
    @MapsId("celebrityId") //per recuperare la chiave di questa "Celebrity", devi usare l'Id celebrity STESSO NOME
    private Celebrity celebrity;

    @ManyToOne
    @MapsId("movieId")
    private Movie movie;

    @Column(length = 1000)
    private String category;
    @Column(length = 1000)
    private String characters;

    public MovieCelebrity(MovieCelebrityKey id, Celebrity celebrity, Movie movie, String category, String characters) {
        this.id = id;
        this.celebrity = celebrity;
        this.movie = movie;
        this.category = category;
        this.characters = characters;
    }
    public MovieCelebrity() {
        this(null, null, null, null, null);
    }

    public MovieCelebrity(MovieCelebrityKey key) {
    }


}
