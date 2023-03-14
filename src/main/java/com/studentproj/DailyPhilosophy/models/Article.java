package com.studentproj.DailyPhilosophy.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Set<Profile> profiles =new HashSet<>();

    public Article(long id, String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Article() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}