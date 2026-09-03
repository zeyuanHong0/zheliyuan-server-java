package org.zly.blogbackjava.dto.request;

import lombok.Data;

@Data
public class UserListAdminDto {
    private String name;
    private Integer pageNum;
    private Integer pageSize;
}
