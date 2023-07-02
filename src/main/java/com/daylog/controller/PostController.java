package com.daylog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    // SSR -> JSP, Thymeleaf, mustache, freemarker
    /* SPA
     -> vue + SSR = nuxt.js
     -> react + SSR = next.js
    */

    @GetMapping("/posts")
    public String get() {
        return "Hello World";
    }
}
