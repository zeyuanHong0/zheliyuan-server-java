package org.zly.blogbackjava.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.zly.blogbackjava.service.AiService;

import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private final RestClient restClient;

    @Value("${app.deepseek.base-url}")
    private String baseUrl;

    @Value("${app.deepseek.api-key}")
    private String apiKey;

    @Value("${app.deepseek.model}")
    private String model;


    public AiServiceImpl(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @Override
    public String summarizeBlog(String content) {
        Map<String, Object> body = Map.of("model", model, "temperature", 0.3, "messages",
                List.of(Map.of("role", "system", "content", "你是一个专业的技术博客编辑。"),
                        Map.of("role", "user", "content", "请对下面这篇博客内容进行总结：" +
                                "要求：1. 使用中文 2. 3~4 行 3. 不添加原文没有的观点\n" +
                                "博客内容：\n" + content)));

        Map response = restClient.post()
                .uri(baseUrl + "/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .body(body)
                .retrieve()
                .body(Map.class);
        return response.toString();
    }
}
