package org.zly.blogbackjava.dto.request;

import lombok.Data;

@Data
public class ChangeUserStatusDto {
    private String id;
    private Integer status;
}
