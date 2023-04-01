package com.studentproj.DailyPhilosophy.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
@JsonIncludeProperties(value = {"id", "text"})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
    private Long Id;
    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;
    @Column(nullable = false)
    private boolean is_correct;
}
