package com.yhr.blog.dao;

import com.yhr.blog.domain.Member;
import com.yhr.blog.domain.MyBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBlogRepository  extends JpaRepository<MyBlog, Long> {

    boolean existsByMember(Member member);

}
