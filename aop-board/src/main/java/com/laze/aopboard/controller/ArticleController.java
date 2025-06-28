package com.laze.aopboard.controller;

import com.laze.aopboard.dto.ArticleDto;
import com.laze.aopboard.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/articles") // 이 컨트롤러의 모든 메소드는 /api/articles 로 시작하는 URL을 가짐
public class ArticleController {

    private final ArticleService articleService;

    // 1. 모든 게시글 조회 API
    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.getArticles();
        return ResponseEntity.ok(articles); // 200 OK와 함께 게시글 목록 반환
    }

    // 2. ID로 특정 게시글 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        ArticleDto article = articleService.getArticleById(id);
        if (article != null) {
            return ResponseEntity.ok(article); // 200 OK와 함께 게시글 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // 3. 새 게시글 생성 API
    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        // @Valid: articleDto에 대한 유효성 검사 수행
        // @RequestBody: 요청 본문의 JSON을 ArticleDto 객체로 변환
        ArticleDto createdArticle = articleService.createArticle(articleDto);
        return ResponseEntity.ok(createdArticle); // 200 OK와 함께 생성된 게시글 정보 반환
    }

    // 4. 특정 게시글 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build(); // 204 No Content (성공적으로 처리했으나 반환할 내용 없음)
    }
}