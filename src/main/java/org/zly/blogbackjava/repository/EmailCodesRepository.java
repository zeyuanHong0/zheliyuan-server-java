package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.EmailCodes;

import java.time.LocalDateTime;

public interface EmailCodesRepository {
    EmailCodes selectLatestCodeByEmail(String email);

    EmailCodes selectLatestValidCodeByEmail(String email);

    int insertEmailCode(String id, String email, String code, LocalDateTime expiredTime);

    int markCodeAsUsed(String id);
}
