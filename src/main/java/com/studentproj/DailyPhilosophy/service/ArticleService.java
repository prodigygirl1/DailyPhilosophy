package com.studentproj.DailyPhilosophy.service;

import com.studentproj.DailyPhilosophy.dao.ArticleRepository;
import com.studentproj.DailyPhilosophy.dao.ProfileRepository;
import com.studentproj.DailyPhilosophy.dao.RecordRepository;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.RecordArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RecordRepository recordRepository;

    public Article getArticleDay() {
        Optional<RecordArticle> recordArticle = recordRepository.findById((long)(1));
        if (recordArticle.isPresent()) {
            Long articleId = recordArticle.get().getArticleId();
            Optional<Article> currentArticle = articleRepository.findById(articleId);
            if (currentArticle.isPresent())
                return currentArticle.get();
        }
        return null;

    }

    public void updateArticleDay() {
        Optional<RecordArticle> recordArticle = recordRepository.findById((long)(1));
        if (recordArticle.isPresent()) {
            Date dateArticle = recordArticle.get().getDate();
            Date dateNow = new Date();
            if (dateArticle.getDay() != dateNow.getDay() ||
                    dateArticle.getMonth() != dateNow.getMonth() ||
                    dateArticle.getYear() != dateNow.getYear()) {
                // меняем статью дня
                long lastValue = recordArticle.get().getArticleId();
                // проверка, есть ли статья с новым айди
                List<Article> all = (List<Article>) articleRepository.findAll();
                long maxId = all.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId())).get().getId();
                if (lastValue + 1 <= maxId) {
                    recordArticle.get().setArticleId(lastValue+1);
                } else {
                    recordArticle.get().setArticleId(1);
                }

                recordArticle.get().setDate(dateNow);
                recordRepository.save(recordArticle.get());
            }
        } else {
            recordRepository.save(new RecordArticle(1));
        }
    }

}
