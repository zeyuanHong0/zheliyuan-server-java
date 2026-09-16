package org.zly.blogbackjava.dto.blog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateBlogDto extends CreateBlogDto{

    @NotBlank(message = "id不能为空")
    private String id;
    
    private String aiSummary;
}
