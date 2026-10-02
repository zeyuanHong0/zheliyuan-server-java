package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.Status;

public interface StatusRepository {

    Status getStatus();

    void updateStatus(Integer isOnline, String statusText, String statusDesc);
}
