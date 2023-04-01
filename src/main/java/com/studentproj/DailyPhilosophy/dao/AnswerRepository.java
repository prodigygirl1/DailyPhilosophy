package com.studentproj.DailyPhilosophy.dao;

import com.studentproj.DailyPhilosophy.models.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
