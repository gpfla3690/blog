package com.yhr.blog.service;

import com.yhr.blog.dao.ArticleRepository;
import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void save(ArticleSaveForm articleSaveForm, Member member) {

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );

        article.setMember(member);

        articleRepository.save(article);

    }
}
