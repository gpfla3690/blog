package com.yhr.blog.controller;

import com.yhr.blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

}
