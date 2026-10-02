package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.status.response.StatusItem;
import org.zly.blogbackjava.repository.StatusRepository;

public interface StatusService {

    StatusItem getStatus();
}
