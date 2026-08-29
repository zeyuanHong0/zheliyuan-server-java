package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.entity.EmailCodes;

import java.time.LocalDateTime;

public interface EmailCodesMapper {
    EmailCodes selectLatestCodeByEmail(@Param("email") String email);

    EmailCodes selectLatestValidCodeByEmail(@Param("email") String email);

    int insertEmailCode(@Param("id") String id, @Param("email") String email, @Param("code") String code, @Param("expiresTime") LocalDateTime expiresTime);

    int markCodeAsUsed(@Param("id") String id);
}
