package com.studentproj.DailyPhilosophy.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@JsonIncludeProperties(value = {"id", "name", "content"})
@Getter
@Setter
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String content;
    @ManyToMany(mappedBy = "articles")
    public Set<Profile> profiles;
    public Article(long id, String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Article() {
    }

}