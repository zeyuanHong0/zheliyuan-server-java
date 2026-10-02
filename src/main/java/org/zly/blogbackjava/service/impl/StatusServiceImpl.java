package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.zly.blogbackjava.dto.status.response.StatusItem;
import org.zly.blogbackjava.entity.Status;
import org.zly.blogbackjava.repository.StatusRepository;
import org.zly.blogbackjava.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public StatusItem getStatus() {
        Status status = statusRepository.getStatus();
        return new StatusItem(status.getIsOnline(), status.getStatusText(), status.getStatusDesc());
    }
}
