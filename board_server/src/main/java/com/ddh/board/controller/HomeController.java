package com.ddh.board.controller;

import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// @Controller("indexController") //파라미터로 어떤 컨트롤러인지를 명시 가능(보통 생략)
@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class HomeController {
  // @GetMapping("/")
  protected String index() {
    return "index";
  }
}
