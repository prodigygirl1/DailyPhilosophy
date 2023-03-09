package com.studentproj.DailyPhilosophy.controllers;

import com.studentproj.DailyPhilosophy.dao.ArticleRepository;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ArticleController {
    @Autowired
    private ArticleRepository articleDAO;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/article_day")
    public Article getArticleDay() {
        Article articleDay = articleService.getArticleDay();
        if (articleDay != null)
            return articleDay;
        return null; // сообщение о ненайденной статье
    }
}

