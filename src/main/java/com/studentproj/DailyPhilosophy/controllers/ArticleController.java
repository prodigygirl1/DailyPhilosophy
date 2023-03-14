package com.studentproj.DailyPhilosophy.controllers;

import com.studentproj.DailyPhilosophy.dao.ArticleRepository;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.Profile;
import com.studentproj.DailyPhilosophy.service.ArticleService;
import com.studentproj.DailyPhilosophy.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ArticleController {
    @Autowired
    private ArticleRepository articleDAO;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/article_day")
    public ResponseEntity<?> getArticleDay() {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Article articleDay = articleService.getArticleDay();
        if (articleDay != null) {
            Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            HashMap<String, Object> response = new HashMap<>();
            response.put("article", articleDay);
            response.put("isLiked", profileService.checkLike(current_user, articleDay));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // сообщение о ненайденной статье
    }
}

