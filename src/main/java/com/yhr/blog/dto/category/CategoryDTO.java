package com.yhr.blog.dto.category;

import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String title;

    public void setCategoryDTO(Category category, Article article){

        this.id = category.getId();
        this.name = category.getName();
        this.title = article.getTitle();

    }


}
