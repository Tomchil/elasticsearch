package com.lc.ibps.elasticsearch.repository;

import com.lc.ibps.elasticsearch.entity.BlogByRep;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BlogRepository extends ElasticsearchRepository<BlogByRep, String> {

    List<BlogByRep> findByTitleLike(String keyword);

}
