package com.yhr.blog.service;

import com.yhr.blog.dao.ArticleRepository;
import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Category;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.article.ArticleDTO;
import com.yhr.blog.dto.article.ArticleListDTO;
import com.yhr.blog.dto.article.ArticleModifyForm;
import com.yhr.blog.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void save(ArticleSaveForm articleSaveForm, Category category, Member member) {

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );

        article.setMember(member);
        article.setCategory(category);

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
    public void modifyArticle(ArticleModifyForm articleModifyForm, Long id, Category category) {

        Article findArticle = findById(id);

        findArticle.modifyArticle(
                articleModifyForm.getTitle(),
                articleModifyForm.getBody()
        );

        findArticle.setCategory(category);

    }

    public List<ArticleListDTO> getArticleList() {

        List<Article> articleList = articleRepository.findAll();

        List<ArticleListDTO> articleDTOList = new ArrayList<>();

        for (Article article : articleList){

            ArticleListDTO articleDTO = new ArticleListDTO(article);
            articleDTOList.add(articleDTO);

        }
        return articleDTOList;

    }


    public List<ArticleDTO> getArticleDTOList(){

        List<Article> articleList = articleRepository.findAll();

        List<ArticleDTO> articleListDto = new ArrayList<>();

        for(Article article : articleList){
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleListDto.add(articleDTO);
        }

        return articleListDto;

    }

    public List<ArticleDTO> getArticleDtoList(List<Article> articleList){

        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for (Article article : articleList) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDTOList.add(articleDTO);
        }
        return  articleDTOList;
    }


    public ArticleDTO getArticle(Long id) {

        Article findArticle = findById(id);

        ArticleDTO articleDTO = new ArticleDTO(findArticle);

        return articleDTO;

    }

    @Transactional
    public void delete(Long id) {

        Article findArticle = findById(id);
        articleRepository.delete(findArticle);

    }

    public Long articleCount(){
        return articleRepository.count();
    }

    public Map<String, Object> searchArticle(
            Long categoryId, int currentPage, String keyword){

        Map<String, Object> result = new HashMap<>();

        keyword.trim();
        keyword = "%" + keyword + "%";

        if(currentPage == 1){
            currentPage = 0;
        }else {
            currentPage = ((currentPage - 1) * 10) +1;
        }

        List<Article> articleList;

        if(categoryId == 0){
            articleList = articleRepository.findAllByKeywordAndPage(keyword, currentPage);
            Integer articleListCount = articleRepository.countByKeywordAndPage(keyword);
            result.put("articleListCount", articleListCount);
        }else {
            articleList = articleRepository.findByKeywordAndPageWithCategory(categoryId, keyword, currentPage);
            Integer articleListCount = articleRepository.countKeywordAndPageWithCategory(categoryId, keyword);
            result.put("articleListCount", articleListCount);
        }

        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for(Article article : articleList){
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDTOList.add(articleDTO);
        }

        result.put("articleDTOList", articleDTOList);

        return result;
    }
}
