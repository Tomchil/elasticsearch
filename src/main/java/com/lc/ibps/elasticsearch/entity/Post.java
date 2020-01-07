package com.lc.ibps.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName="projectname",type="post")
public class Post {

    @Id
    private String id;

    private String title;

    private String content;

    private int userId;

    private int weight;
}
