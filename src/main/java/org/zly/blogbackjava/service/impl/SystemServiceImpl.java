package org.zly.blogbackjava.service.impl;

import java.time.LocalDateTime;

import org.zly.blogbackjava.repository.SystemRepository;
import org.zly.blogbackjava.service.SystemService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {

    private final SystemRepository systemRepository;

    @Override
    public LocalDateTime currentTime() {
        return systemRepository.currentTime();
    }
}
