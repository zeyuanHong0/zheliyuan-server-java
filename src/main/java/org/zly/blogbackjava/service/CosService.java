package org.zly.blogbackjava.service;

import org.springframework.web.multipart.MultipartFile;

public interface CosService {

    void uploadFileToCos(String key, MultipartFile file);

    String getPublicUrl(String key);
}
