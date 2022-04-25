package com.yhr.blog.service;

import com.yhr.blog.dao.ArticleRepository;
import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.article.ArticleModifyForm;
import com.yhr.blog.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void save(ArticleSaveForm articleSaveForm, Member member) {

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );

        article.setMember(member);

        articleRepository.save(article);
    }

    public Article findById(Long id) {

        Optional<Article> articleOptional = articleRepository.findById(id);

        articleOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시글 입니다.")
        );

        return articleOptional.get();
    }

    @Transactional
    public void modifyArticle(ArticleModifyForm articleModifyForm, Long id) {

        Article findArticle = findById(id);

        findArticle.modifyArticle(
                articleModifyForm.getTitle(),
                articleModifyForm.getBody()
        );

    }
}
