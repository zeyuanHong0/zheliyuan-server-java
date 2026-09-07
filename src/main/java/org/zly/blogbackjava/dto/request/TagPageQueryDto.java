package org.zly.blogbackjava.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TagPageQueryDto {

    private String name;

    @NotNull(message = "页码不能为空")
    @Positive(message = "页码必须大于0")
    private Integer pageNum;

    @NotNull(message = "每页数量不能为空")
    @Positive(message = "每页数量必须大于0")
    private Integer pageSize;
}
