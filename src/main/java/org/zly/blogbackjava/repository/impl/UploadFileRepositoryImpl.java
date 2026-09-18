package org.zly.blogbackjava.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.zly.blogbackjava.entity.UploadFile;
import org.zly.blogbackjava.mapper.UploadFileMapper;
import org.zly.blogbackjava.repository.UploadFileRepository;

@Repository
@RequiredArgsConstructor
public class UploadFileRepositoryImpl implements UploadFileRepository {

    private final UploadFileMapper uploadFileMapper;

    @Override
    public void saveFileRecord(UploadFile uploadFile) {
        uploadFileMapper.saveFileRecord(uploadFile);
    }
}
