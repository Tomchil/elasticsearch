package com.lc.ibps.elasticsearch.repository;

import com.lc.ibps.elasticsearch.entity.Blog;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BlogRepository extends ElasticsearchRepository<Blog, String> {

    List<Blog> findByTitleLike(String keyword);

    @Query("{\"match_phrase\":{\"title\":\"?0\"}}")
    List<Blog> findByTitleCustom(String keyword);
}
