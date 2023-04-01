package com.studentproj.DailyPhilosophy.controllers;

import com.studentproj.DailyPhilosophy.dao.ArticleRepository;
import com.studentproj.DailyPhilosophy.dto.AnswerCheckResultDto;
import com.studentproj.DailyPhilosophy.dto.ArticleDayDto;
import com.studentproj.DailyPhilosophy.models.Answer;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.Profile;
import com.studentproj.DailyPhilosophy.models.Question;
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

    @GetMapping("/articleDay")
    public ResponseEntity<?> getArticleDay() {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Article articleDay = articleService.getArticleDay();
        if (articleDay != null) {
            Profile current_user = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean isLiked = profileService.checkLike(current_user, articleDay);
          /*  HashMap<String, Object> response = new HashMap<>();
            response.put("article", articleDay);
            response.put("isLiked", profileService.checkLike(current_user, articleDay));*/
            return new ResponseEntity<>(new ArticleDayDto(articleDay, isLiked), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // сообщение о ненайденной статье
    }

    @GetMapping("/question")
    public ResponseEntity<?> getQuestionDay() {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Question question = articleService.getQuestionDay();
        if (question != null && question.getAnswers().size() > 0) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
        return new ResponseEntity<>("Викторины дня пока нет. Приходи завтра!", HttpStatus.NOT_FOUND); // сообщение о ненайденной статье
    }

    @GetMapping("/answer/{id}/check")
    public ResponseEntity<?> checkAnswer(@PathVariable Long id) {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Answer userAnswer = articleService.getAnswer(id);
        if (userAnswer == null) {
            return new ResponseEntity<>("Неожиданная ошибка", HttpStatus.NOT_FOUND);
        }

        Answer correctAnswer = articleService.getCorrectAnswer(id);
        if (correctAnswer == null) {
            return new ResponseEntity<>("Неожиданная ошибка", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new AnswerCheckResultDto(userAnswer, correctAnswer), HttpStatus.OK);
    }
}

