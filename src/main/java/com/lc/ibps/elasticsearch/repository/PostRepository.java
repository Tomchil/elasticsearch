package com.lc.ibps.elasticsearch.repository;

import com.lc.ibps.elasticsearch.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
}
