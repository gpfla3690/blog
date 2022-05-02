package com.yhr.blog.service;

import com.yhr.blog.dao.CategoryRepository;
import com.yhr.blog.domain.Category;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.category.CategorySaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Transactional
    public void save(CategorySaveForm categorySaveForm, Member findMember) {

        Category category = Category.createCategory(
                categorySaveForm.getId(),
                categorySaveForm.getName()
        );

        category.setMember(findMember);

        categoryRepository.save(category);

    }
}
