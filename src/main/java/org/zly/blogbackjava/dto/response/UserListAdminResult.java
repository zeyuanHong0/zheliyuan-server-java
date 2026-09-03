package org.zly.blogbackjava.dto.response;


import java.util.List;

public record UserListAdminResult(
        List<UserListItem> list,
        Long total,
        Integer pageNum,
        Integer pageSize
) {
}
