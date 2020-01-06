package com.lc.ibps.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class ElasticsearchApplicationTests {

    @Test
    public void contextLoads() {
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
