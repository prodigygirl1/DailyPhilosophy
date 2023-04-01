package com.studentproj.DailyPhilosophy.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "questions")
@NoArgsConstructor
@AllArgsConstructor
@JsonIncludeProperties(value = {"id", "text", "answers"})
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    private Long Id;
    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    private Article article;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Answer> answers = new HashSet<>();

    public Answer getCorrectAnswer() {
        return answers.stream().filter(Answer::is_correct).findFirst().get();
    }
}
