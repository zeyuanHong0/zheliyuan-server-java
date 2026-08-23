package org.zly.blogbackjava.repository.impl;

import java.time.LocalDateTime;

import org.zly.blogbackjava.mapper.SystemMapper;
import org.zly.blogbackjava.repository.SystemRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SystemRepositoryImpl implements SystemRepository {

    private final SystemMapper systemMapper;

    @Override
    public LocalDateTime currentTime() {
        return systemMapper.selectCurrentTime();
    }
}
