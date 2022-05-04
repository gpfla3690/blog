package com.yhr.blog.dto.category;

import com.yhr.blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDTO {

    private Long id;
    private String name;

    public CategoryListDTO(Category category){

        this.id = category.getId();
        this.name = category.getName();

    }

}
