package com.studentproj.DailyPhilosophy.dto;

import com.studentproj.DailyPhilosophy.models.Article;
import lombok.Data;

@Data
public class ArticleDayDto {
    Article article;
    boolean isLiked;

    public ArticleDayDto(Article articleDay, boolean isLiked) {
        this.article = articleDay;
        this.isLiked = isLiked;
    }
}
