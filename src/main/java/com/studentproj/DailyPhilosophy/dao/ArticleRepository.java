package com.studentproj.DailyPhilosophy.dao;

import com.studentproj.DailyPhilosophy.models.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Optional<Article> findById(Long id);
}
