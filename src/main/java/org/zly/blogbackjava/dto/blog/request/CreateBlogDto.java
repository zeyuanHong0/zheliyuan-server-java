package org.zly.blogbackjava.dto.blog.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateBlogDto {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "描述不能为空")
    private String description;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "发布状态不能为空")
    private Integer published;

    @NotBlank(message = "分类不能为空")
    private String category;

    @Size(min = 1, message = "至少要有一个标签")
    private List<String> tags;
}
