package com.studentproj.DailyPhilosophy.service;

import com.studentproj.DailyPhilosophy.dao.ArticleRepository;
import com.studentproj.DailyPhilosophy.dao.ProfileRepository;
import com.studentproj.DailyPhilosophy.dto.RegisterDto;
import com.studentproj.DailyPhilosophy.models.Article;
import com.studentproj.DailyPhilosophy.models.Profile;
import com.studentproj.DailyPhilosophy.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class ProfileService implements UserDetailsService {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProfileRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public void register(Profile profile) {
        profile.setPassword(encoder.encode(profile.getPassword()));
        userRepository.save(profile);
    }

    public String auth(String login, String password) {
        Profile profile = userRepository.findByLogin(login);
        if (profile == null)
            return null; // возврат сообщения
        if (encoder.matches(password, profile.getPassword())) {
            // запрос обновления статьи дня
            articleService.updateArticleDay();
            return jwtProvider.generateToken(login);
        }

        return null; // возврат сообщения
    }

    public void update(Profile curr_user, RegisterDto profile) {
        curr_user.setPassword(encoder.encode(profile.getPassword()));
        curr_user.setLogin(profile.getLogin());
        userRepository.save(curr_user);
    }

    public void like(Profile curr_user, Long article_id) {
        Optional<Article> articleLike = articleRepository.findById(article_id);
        if (articleLike.isPresent()) {
            curr_user.addArticleToFav(articleLike.get());
            userRepository.save(curr_user);
            //return
        }
        //return
    }

    public void remove_like(Profile curr_user, Long article_id) {
        Article article = curr_user.removeArticleFromFav(article_id);
        if (article != null) {
            articleRepository.save(article);}
        else {
            //return;
        }
    }

    public Set<Article> get_likes(Profile current_user) {
        return current_user.getArticles();
    }

}
