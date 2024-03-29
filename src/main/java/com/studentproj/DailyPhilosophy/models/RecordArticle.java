package com.studentproj.DailyPhilosophy.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "records")
@NoArgsConstructor
@AllArgsConstructor
public class RecordArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rec_seq")
    private long id;

    private Date date;

    private long articleId;
    private long questionId;

    public RecordArticle(long articleId) {
        this.date = new Date();
        this.articleId = articleId;
    }

    public RecordArticle(long articleId, long questionId) {
        this.date = new Date();
        this.articleId = articleId;
        this.questionId = questionId;
    }
}
