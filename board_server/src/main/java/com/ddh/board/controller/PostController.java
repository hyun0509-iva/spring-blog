package com.ddh.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
  public String requestMethodName(@RequestParam String param) {
    return new String();
  }

  @GetMapping("/post")
  protected String findAll(Model model) {
    return "post";
  }
}
