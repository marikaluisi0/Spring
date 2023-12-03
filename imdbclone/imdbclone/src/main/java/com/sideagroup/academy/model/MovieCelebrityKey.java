package com.sideagroup.academy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable //chiave composta
public class MovieCelebrityKey {
    @Column(length=200)
    private String celebrityId;
    @Column (length=200)
    private String movieId;

    public MovieCelebrityKey(String celebrityId, String movieId) {
        this.celebrityId = celebrityId;
        this.movieId = movieId;
    }

    public MovieCelebrityKey() {
        this(null, null);
    }
}
