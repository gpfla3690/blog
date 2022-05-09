package com.yhr.blog.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveForm {

    private Long id;
    @NotBlank(message = "카테고리 이름을 입력해 주세요.")
    private String name;

}
