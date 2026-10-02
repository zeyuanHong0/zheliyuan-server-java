package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.entity.Status;
import org.zly.blogbackjava.mapper.StatusMapper;
import org.zly.blogbackjava.repository.StatusRepository;

@Component
public class StatusRepositoryImpl implements StatusRepository {

    private final StatusMapper statusMapper;

    public StatusRepositoryImpl(StatusMapper statusMapper) {
        this.statusMapper = statusMapper;
    }

    @Override
    public Status getStatus() {
        return statusMapper.getStatus();
    }

    @Override
    public void updateStatus(Integer isOnline, String statusText, String statusDesc) {
        statusMapper.updateStatus(isOnline, statusText, statusDesc);
    }
}
