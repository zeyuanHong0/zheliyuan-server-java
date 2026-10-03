package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.status.request.UpdateStatusDto;
import org.zly.blogbackjava.dto.status.response.StatusItem;

public interface StatusService {

    StatusItem getStatus();

    void updateStatus(UpdateStatusDto updateStatusDto);
}
