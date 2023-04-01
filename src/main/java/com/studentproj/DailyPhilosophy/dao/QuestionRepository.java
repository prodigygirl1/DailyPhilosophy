package com.studentproj.DailyPhilosophy.dao;

import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Optional<Question> findById(Long id);
}
