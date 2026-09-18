package org.zly.blogbackjava.service;

import org.springframework.web.multipart.MultipartFile;
import org.zly.blogbackjava.dto.upload.response.UploadFileResult;

public interface UploadService {

    UploadFileResult uploadFileToCos(MultipartFile file, String userId);
}
