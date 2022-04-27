package com.yhr.blog.dto.article;
import com.yhr.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDTO {

    private Long id;

    private String title;

    private String nickname;

    private LocalDateTime regDate;

    public ArticleListDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.nickname = article.getMember().getNickname();
        this.regDate = article.getRegDate();

    }

}
