package com.laze.aopboard.service;

import com.laze.aopboard.dto.ArticleDto;
import com.laze.aopboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleDto> getArticles() {
        return articleRepository.findAll();
    }

    public ArticleDto getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
        articleDto.setId(null);
        return articleRepository.save(articleDto);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

}
