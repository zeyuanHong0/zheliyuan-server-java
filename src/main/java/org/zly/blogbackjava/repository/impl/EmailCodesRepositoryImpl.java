package org.zly.blogbackjava.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.zly.blogbackjava.entity.EmailCodes;
import org.zly.blogbackjava.mapper.EmailCodesMapper;
import org.zly.blogbackjava.repository.EmailCodesRepository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class EmailCodesRepositoryImpl implements EmailCodesRepository {

    private final EmailCodesMapper emailCodesMapper;

    @Override
    public EmailCodes selectLatestCodeByEmail(String email) {
        return emailCodesMapper.selectLatestCodeByEmail(email);
    }

    @Override
    public EmailCodes selectLatestValidCodeByEmail(String email) {
        return emailCodesMapper.selectLatestValidCodeByEmail(email);
    }

    @Override
    public int insertEmailCode(String id, String email, String code, LocalDateTime expiresTime) {
        return emailCodesMapper.insertEmailCode(id, email, code, expiresTime);
    }

    @Override
    public int markCodeAsUsed(String id) {
        return emailCodesMapper.markCodeAsUsed(id);
    }
}
