package com.laze.aopboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PostController {
    @GetMapping("/posts/{id}")
    public Map<String, String> getPosts(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(3000);
        return Map.of("id", id.toString(), "content", "Posts content is %d".formatted(id));
    }
}
