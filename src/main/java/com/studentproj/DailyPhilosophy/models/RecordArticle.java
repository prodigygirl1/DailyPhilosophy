package com.studentproj.DailyPhilosophy.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "records")
public class RecordArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rec_seq")
    private long id;

    private Date date;

    private long articleId;

    public RecordArticle(long articleId) {
        this.date = new Date();
        this.articleId = articleId;
    }

    public RecordArticle() {
    }
}
