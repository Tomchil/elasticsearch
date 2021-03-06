package com.lc.ibps.elasticsearch.controller;

import com.lc.ibps.elasticsearch.entity.BlogByRep;
import com.lc.ibps.elasticsearch.repository.BlogRepository;
import com.lc.ibps.elasticsearch.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @PostMapping("/add")
    public Result<BlogByRep> add(@RequestBody BlogByRep blogByRep) {
        blogRepository.save(blogByRep);
        return Result.success();
    }

    @GetMapping("/get/{id}")
    public Result<BlogByRep> getById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error();
        }

        Optional<BlogByRep> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {
            BlogByRep blogByRep = blogOptional.get();
            return Result.success(blogByRep);
        }
        return Result.error();
    }

    @RequestMapping("/get")
    public Result<List<BlogByRep>> getAll() {
        Iterable<BlogByRep> iterable = blogRepository.findAll();
        List<BlogByRep> list = new ArrayList<>();
        iterable.forEach(list::add);
        return Result.success(list);
    }

    @PostMapping("update")
    public Result<BlogByRep> updateById(@RequestBody BlogByRep blogByRep) {
        String id = blogByRep.getId();
        if (StringUtils.isEmpty(id)) {
            return Result.error();
        }
        blogRepository.save(blogByRep);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<BlogByRep> deleteById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error();
        }
        blogRepository.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result<BlogByRep> deleteAll() {
        blogRepository.deleteAll();
        return Result.success();
    }

    @GetMapping("/rep/search/title")
    public Result<List<BlogByRep>> repSearchTitle(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return Result.error();
        }
        List<BlogByRep> blogByReps = blogRepository.findByTitleLike(keyword);
        return Result.success(blogByReps);
    }

}
