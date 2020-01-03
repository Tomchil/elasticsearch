package com.lc.ibps.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "blog", type = "java")
public class Blog implements Serializable {

    private static final long serialVersionUID = -6459278471002007777L;

    @Id // id字段是必须的，可以不写注解@Id
    private String id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;

    public Blog() {
    }

    public Blog(String id, String title, Date time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }

}
