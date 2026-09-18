package org.zly.blogbackjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zly.blogbackjava.dto.upload.response.UploadFileResult;
import org.zly.blogbackjava.entity.UploadFile;
import org.zly.blogbackjava.repository.UploadFileRepository;
import org.zly.blogbackjava.service.CosService;
import org.zly.blogbackjava.service.UploadService;
import org.zly.blogbackjava.util.FileUtil;
import org.zly.blogbackjava.util.IdGenerator;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final UploadFileRepository uploadFileRepository;
    private final CosService cosService;

    @Override
    public UploadFileResult uploadFileToCos(MultipartFile file, String userId) {
        String filename = FileUtil.getFileName(file);
        String key = "uploads/" + filename;

        cosService.uploadFileToCos(key, file);
        String fileUrl = cosService.getPublicUrl(key);

        UploadFile uploadFile = new UploadFile();
        uploadFile.setId(IdGenerator.generateId());
        uploadFile.setOriginalName(file.getOriginalFilename());
        uploadFile.setFilename(filename);
        uploadFile.setMimeType(file.getContentType());
        uploadFile.setSize(file.getSize());
        uploadFile.setStorageType("cos");
        uploadFile.setFileUrl(fileUrl);
        uploadFile.setUploadedBy(userId);
        uploadFileRepository.saveFileRecord(uploadFile);

        return new UploadFileResult(filename, fileUrl);
    }
}
