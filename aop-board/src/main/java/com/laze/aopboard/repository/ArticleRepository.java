package com.laze.aopboard.repository;

import com.laze.aopboard.dto.ArticleDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleRepository {

    private final Map<Long, ArticleDto> articleStore = new HashMap<>();
    private long sequence = 0L;

    public List<ArticleDto> findAll() {
        return new ArrayList<>(articleStore.values());
    }

    public Optional<ArticleDto> findById(Long id) {
        return Optional.ofNullable(articleStore.get(id));
    }

    // 게시글 저장 (생성 및 수정)
    public ArticleDto save(ArticleDto articleDto) {
        if (articleDto.getId() == null || articleDto.getId() == 0L) {
            // 새 게시글 생성
            articleDto.setId(++sequence);
            articleStore.put(articleDto.getId(), articleDto);
            return articleDto;
        } else {
            // 기존 게시글 수정
            articleStore.put(articleDto.getId(), articleDto);
            return articleDto;
        }
    }

    // 게시글 삭제
    public void deleteById(Long id) {
        articleStore.remove(id);
    }
}
