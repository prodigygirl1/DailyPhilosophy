package com.studentproj.DailyPhilosophy.dto;

import com.studentproj.DailyPhilosophy.models.Answer;
import lombok.Data;

@Data
public class AnswerCheckResultDto {
    Answer userAnswer;
    Answer correctAnswer;

    public AnswerCheckResultDto(Answer userAnswer, Answer correctAnswer) {
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
    }
}
