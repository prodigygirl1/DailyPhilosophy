package com.studentproj.DailyPhilosophy.dao;

import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.RecordArticle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends CrudRepository<RecordArticle, Long> {
    Optional<RecordArticle> findById(Long id);
}
