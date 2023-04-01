package com.studentproj.DailyPhilosophy.service;

import com.studentproj.DailyPhilosophy.dao.AnswerRepository;
import com.studentproj.DailyPhilosophy.dao.ArticleRepository;
import com.studentproj.DailyPhilosophy.dao.QuestionRepository;
import com.studentproj.DailyPhilosophy.dao.RecordRepository;
import com.studentproj.DailyPhilosophy.models.Answer;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.Question;
import com.studentproj.DailyPhilosophy.models.RecordArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

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

                long lastValue = recordArticle.get().getArticleId();
                //обновляем вопрос дня
                Question question = updateQuestionDay(lastValue);
                if (question != null)
                    recordArticle.get().setQuestionId(question.getId());
                else
                    recordArticle.get().setQuestionId(0);
                // проверка, есть ли статья с новым айди
                List<Article> all = (List<Article>) articleRepository.findAll();
                long maxId = all.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId())).get().getId();
                // меняем статью дня
                if (lastValue + 1 <= maxId) {
                    recordArticle.get().setArticleId(lastValue+1);
                } else {
                    recordArticle.get().setArticleId(1);
                }

                recordArticle.get().setDate(dateNow);
                recordRepository.save(recordArticle.get());
            }
        } else {
            recordRepository.save(new RecordArticle(1, 0));
        }
    }

    private Question updateQuestionDay(long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isPresent()) {
            Random random = new Random();
            List<Question> questions = article.get().getQuestions();
            if (questions.size() > 0) {
                int randomIndexQuestion = random.nextInt(questions.size());
                return questions.get(randomIndexQuestion);
            }
        }
        return null;
    }

    public Question getQuestionDay() {
        Optional<RecordArticle> recordArticle = recordRepository.findById((long)(1));
        if (recordArticle.isPresent()) {
            Long questionId = recordArticle.get().getQuestionId();
            Optional<Question> currentQuestion = questionRepository.findById(questionId);
            if (currentQuestion.isPresent())
                return currentQuestion.get();
        }
        return null;
    }

    public Answer getCorrectAnswer(Long answerId) {
        Question question = getQuestionDay();
        if (question == null) {
            return null;
        }

        if (question.getAnswers().stream().noneMatch((a) -> a.getId().equals(answerId))) {
            return null;
        }
        return question.getCorrectAnswer();
    }

    public Answer getAnswer(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (answer.isEmpty()) {
            return null;
        }
        return answer.get();
    }

}
