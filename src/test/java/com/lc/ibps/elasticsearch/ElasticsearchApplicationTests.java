package com.lc.ibps.elasticsearch;

import com.lc.ibps.elasticsearch.entity.BlogByJest;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {

    @Autowired
    JestClient jestClient;

    // 使用JestClient添加索引和数据
    @Test
    public void contextLoads() throws ParseException {

        BlogByJest blogByJest = new BlogByJest();
        blogByJest.setId("1");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-08-08");
        blogByJest.setTime(date);
        blogByJest.setTitle("JestClient 测试");
        Index index = new Index.Builder(blogByJest).index("blogs").type("java").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 使用RestHighLevelClient创建索引posts，并在该索引中创建1000条数据
    @Test
    public void initCreate() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        String[] possibleUsers = new String[] {"Martin", "Jim", "John"};
        String[] possibleDates = new String[] {"2019-12-15", "2019-12-16", "2019-12-17"};
        String[] possibleMessages = new String[] {"Hello, Javaadvent !",
                "Cool set of blog posts. We want more !",
                "Elasticsearch SQL is great."};

        for(int i = 1; i <= 1000; i++) {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("user", possibleUsers[ThreadLocalRandom.current().nextInt(0, 3)]);
            jsonMap.put("date", possibleDates[ThreadLocalRandom.current().nextInt(0, 3)]);
            jsonMap.put("message", possibleMessages[ThreadLocalRandom.current().nextInt(0, 3)]);
            IndexRequest request = new IndexRequest("posts", "doc")
                    .id(String.valueOf(i)).source(jsonMap);
            client.index(request, RequestOptions.DEFAULT);
        }

        client.close();
    }

}
