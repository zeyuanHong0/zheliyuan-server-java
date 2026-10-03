package org.zly.blogbackjava.dto.status.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusDto {

    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态只能为0或1")
    @Max(value = 1, message = "状态只能为0或1")
    @JsonProperty("is_online")
    private Integer isOnline;

    @NotBlank(message = "状态文本不能为空")
    @JsonProperty("status_text")
    private String statusText;

    @NotBlank(message = "状态描述不能为空")
    @JsonProperty("status_desc")
    private String statusDesc;
}
