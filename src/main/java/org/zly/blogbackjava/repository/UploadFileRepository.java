package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.UploadFile;

public interface UploadFileRepository {

    void saveFileRecord(UploadFile uploadFile);
}
