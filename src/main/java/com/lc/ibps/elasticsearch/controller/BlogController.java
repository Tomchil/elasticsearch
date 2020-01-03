package com.lc.ibps.elasticsearch.controller;

import com.lc.ibps.elasticsearch.entity.Blog;
import com.lc.ibps.elasticsearch.repository.BlogRepository;
import com.lc.ibps.elasticsearch.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/add")
    public Result<Blog> add(@RequestBody Blog blog) {
        blogRepository.save(blog);
        return Result.success();
    }

    @GetMapping("/get/{id}")
    public Result<Blog> getById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error();
        }

        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {
            Blog blog = blogOptional.get();
            return Result.success(blog);
        }
        return Result.error();
    }

    @RequestMapping("/get")
    public Result<List<Blog>> getAll() {
        Iterable<Blog> iterable = blogRepository.findAll();
        List<Blog> list = new ArrayList<>();
        iterable.forEach(list::add);
        return Result.success(list);
    }

    @PostMapping("update")
    public Result<Blog> updateById(@RequestBody Blog blog) {
        String id = blog.getId();
        if (StringUtils.isEmpty(id)) {
            return Result.error();
        }
        blogRepository.save(blog);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Blog> deleteById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error();
        }
        blogRepository.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result<Blog> deleteAll() {
        blogRepository.deleteAll();
        return Result.success();
    }

    @GetMapping("/rep/search/title")
    public Result<List<Blog>> repSearchTitle(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return Result.error();
        }
        List<Blog> blogs = blogRepository.findByTitleLike(keyword);
        return Result.success(blogs);
    }

    @GetMapping("/rep/search/title/custom")
    public Result<List<Blog>> repSearchTitleCustom(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return Result.error();
        return Result.success(blogRepository.findByTitleCustom(keyword));
    }

    @GetMapping("/search/title")
    public Result<List<Blog>> searchTitle(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return Result.error();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery(keyword))
                .build();
        List<Blog> list = elasticsearchTemplate.queryForList(searchQuery, Blog.class);
        return Result.success(list);
    }
}
