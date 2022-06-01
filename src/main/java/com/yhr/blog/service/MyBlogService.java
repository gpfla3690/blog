package com.yhr.blog.service;

import com.yhr.blog.dao.MyBlogRepository;
import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Category;
import com.yhr.blog.domain.Member;
import com.yhr.blog.domain.MyBlog;
import com.yhr.blog.dto.article.ArticleDTO;
import com.yhr.blog.dto.category.CategoryDTO;
import com.yhr.blog.dto.myblog.MyBlogMainDTO;
import com.yhr.blog.dto.myblog.MyBlogSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBlogService {

    private final MemberService memberService;
    private final CategoryService categoryService;

    private final ArticleService articleService;
    private final MyBlogRepository myBlogRepository;

    public boolean ownBlog(String loginId){

        Member findMember = memberService.findByLoginId(loginId);

        boolean isExistMember = myBlogRepository.existsByMember(findMember);

        return isExistMember;

    }

    @Transactional
    public void createBlog(MyBlogSaveForm myBlogSaveForm, String loginId) {

        MyBlog myBlog = MyBlog.createMyBLog(myBlogSaveForm);
        Member findMember = memberService.findByLoginId(loginId);
        myBlog.setMember(findMember);

        myBlogRepository.save(myBlog);

    }

    public MyBlogMainDTO getMyBlogMainDto(String loginId) {

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        Member findMember = memberService.findByLoginId(loginId);

        List<Category> categories = findMember.getCategories();

        for(Category category : categories){
            CategoryDTO categoryDTO = categoryService.getCategory(category.getName());
            categoryDTOList.add(categoryDTO);
        }

        MyBlogMainDTO myBlogMainDTO = new MyBlogMainDTO(findMember);
        myBlogMainDTO.setCategoryDTOList(categoryDTOList);
        return myBlogMainDTO;
    }

    public List<ArticleDTO> getArticles() {
        return articleService.getArticleDTOList();
    }

    public List<ArticleDTO> getArticleByCategoryName(String categoryName) {

        List<ArticleDTO> articleList = new ArrayList<>();
        CategoryDTO findCategory = categoryService.getCategory(categoryName);
        System.out.println("findCategory" + findCategory);

        for(Article article : findCategory.getArticles()){
            ArticleDTO articleDTO = articleService.getArticle(article.getId());
            articleList.add(articleDTO);
        }
        return articleList;

    }


    public List<ArticleDTO> getArticleByLoginId(String loginId){

        Member findMember = memberService.findByLoginId(loginId);

        List<ArticleDTO> articles = articleService.getArticleDtoList(findMember.getArticles());

        return articles;
    }

}
