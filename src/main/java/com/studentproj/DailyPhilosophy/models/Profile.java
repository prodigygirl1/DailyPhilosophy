package com.studentproj.DailyPhilosophy.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.*;

@Slf4j
@JsonIncludeProperties(value = {"id", "login", "isAnsweredToday"})
@Entity
@Getter
@Setter
@Table(name = "profiles")
public class Profile implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String login;
    private String password;
    private Date dateLastAnswer;
    @ManyToMany(mappedBy = "profiles",fetch = FetchType.EAGER)
    public Set<Article> articles =new HashSet<>();
    public Profile() {}

    public Profile(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void addArticleToFav(Article article){
        this.articles.add(article);
        article.getProfiles().add(this);
    }

    public Article removeArticleFromFav(Long article_id){
        Article article1 = this.articles.stream().filter(t -> t.getId() == article_id).findFirst().orElse(null);
        if (article1 != null) {
            this.articles.remove(article1);
            article1.getProfiles().remove(this);
        }
        return article1;
    }
    @JsonProperty("isAnsweredToday")
    public boolean getIsAnsweredToday (){
        Date dateUser = this.getDateLastAnswer();
        Date dateNow = new Date();
        if (dateUser != null && dateUser.getDay() == dateNow.getDay()
                && dateUser.getMonth() == dateNow.getMonth()
                && dateUser.getYear() == dateNow.getYear()) {
            return true;
        }

        return false;
    }

}
