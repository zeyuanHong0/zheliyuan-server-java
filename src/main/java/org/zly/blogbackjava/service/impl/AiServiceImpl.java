package org.zly.blogbackjava.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.zly.blogbackjava.repository.BlogRepository;
import org.zly.blogbackjava.service.AiService;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private final RestClient restClient;
    private final BlogRepository blogRepository;

    @Value("${app.deepseek.base-url}")
    private String baseUrl;

    @Value("${app.deepseek.api-key}")
    private String apiKey;

    @Value("${app.deepseek.model}")
    private String model;


    public AiServiceImpl(RestClient.Builder restClientBuilder, BlogRepository blogRepository) {
        this.restClient = restClientBuilder.build();
        this.blogRepository = blogRepository;
    }

    @Override
    public String summarizeBlog(String content) {
        Map<String, Object> body = Map.of("model", model, "temperature", 0.3, "messages",
                List.of(Map.of("role", "system", "content", "你是一个专业的技术博客编辑。"),
                        Map.of("role", "user", "content", "请用中文总结下面这篇博客：" +
                                "摘要总长度控制在150～200个字符（包括标点和空格），只输出一段摘要，" +
                                "不要标题、列表或额外说明，不添加原文没有的观点。\n" +
                                "博客内容：\n" + content)));

        JsonNode response = restClient.post()
                .uri(baseUrl + "/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .body(body)
                .retrieve()
                .body(JsonNode.class);

        return response.at("/choices/0/message/content").asText("");
    }

    @Async
    @Override
    public void summarizeBlogAsync(String blogId, String content) {
        String summary = summarizeBlog(content);
        blogRepository.updateAiSummary(blogId, summary);
    }
}
