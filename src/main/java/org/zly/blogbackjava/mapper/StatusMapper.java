package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.entity.Status;

public interface StatusMapper {

    Status getStatus();

    void updateStatus(
            @Param("isOnline") Integer isOnline,
            @Param("statusText") String statusText,
            @Param("statusDesc") String statusDesc
    );
}
