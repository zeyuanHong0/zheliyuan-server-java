package org.zly.blogbackjava.dto.tag.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.zly.blogbackjava.validation.SafeSvg;

@Data
public class CreateTagDto {

    @NotBlank(message = "标签名称不能为空")
    private String name;

    @Size(max = 50_000, message = "SVG 不能超过50000个字符")
    @SafeSvg
    private String icon;

    @JsonProperty("icon_dark")
    @Size(max = 50_000, message = "SVG 不能超过50000个字符")
    @SafeSvg
    private String iconDark;
}
