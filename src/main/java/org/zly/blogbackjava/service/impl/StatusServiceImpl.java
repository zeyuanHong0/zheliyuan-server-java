package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.zly.blogbackjava.config.MyWebSocketHandler;
import org.zly.blogbackjava.dto.status.request.UpdateStatusDto;
import org.zly.blogbackjava.dto.status.response.StatusItem;
import org.zly.blogbackjava.entity.Status;
import org.zly.blogbackjava.repository.StatusRepository;
import org.zly.blogbackjava.service.StatusService;
import tools.jackson.core.JacksonException;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final MyWebSocketHandler myWebSocketHandler;

    public StatusServiceImpl(StatusRepository statusRepository, MyWebSocketHandler myWebSocketHandler) {
        this.statusRepository = statusRepository;
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @Override
    public StatusItem getStatus() {
        Status status = statusRepository.getStatus();
        return new StatusItem(status.getIsOnline(), status.getStatusText(), status.getStatusDesc());
    }

    @Override
    public void updateStatus(UpdateStatusDto updateStatusDto) {
        statusRepository.updateStatus(
                updateStatusDto.getIsOnline(),
                updateStatusDto.getStatusText(),
                updateStatusDto.getStatusDesc()
        );
        try {
            myWebSocketHandler.broadcastStatus("status_update", updateStatusDto);
        } catch (JacksonException exception) {
            throw new IllegalStateException("Failed to serialize status update event", exception);
        }
    }
}
