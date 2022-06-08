package com.yhr.blog.dto.myblog;

import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBlogMainDTO {

    private String nickname;
    private String blogName;

    private List<CategoryDTO> categoryDTOList;

    private int noCateArticleSize;

    public MyBlogMainDTO(Member member){

        this.nickname = member.getNickname();
        this.blogName = member.getMyBlog().getBlogName();

    }

}
