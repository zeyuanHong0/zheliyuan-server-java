package org.zly.blogbackjava.service;

public interface AiService {
    String summarizeBlog(String content);

    void summarizeBlogAsync(String blogId, String content);
}
