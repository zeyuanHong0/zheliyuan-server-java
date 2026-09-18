package org.zly.blogbackjava.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.service.CosService;

import java.io.IOException;
import java.io.InputStream;

@Service
public class CosServiceImpl implements CosService {

    private final String secretId;
    private final String secretKey;
    private final String bucket;
    private final String region;
    private final COSClient cosClient;

    public CosServiceImpl(
            @Value("${app.cos.secret-id}") String secretId,
            @Value("${app.cos.secret-key}") String secretKey,
            @Value("${app.cos.bucket}") String bucket,
            @Value("${app.cos.region}") String region
    ) {
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.bucket = bucket;
        this.region = region;
        validateConfiguration();
        this.cosClient = new COSClient(
                new BasicCOSCredentials(secretId, secretKey),
                new ClientConfig(new Region(region))
        );
    }

    @Override
    public void uploadFileToCos(String key, MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(400, "上传文件不能为空");
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            cosClient.putObject(new PutObjectRequest(bucket, key, inputStream, metadata));
        } catch (IOException | CosClientException e) {
            throw new BusinessException(500, "文件上传到 COS 失败");
        }
    }

    @Override
    public String getPublicUrl(String key) {
        return "https://" + bucket + ".cos." + region + ".myqcloud.com/" + key;
    }

    private void validateConfiguration() {
        if (isBlank(secretId) || isBlank(secretKey) || isBlank(bucket) || isBlank(region)) {
            throw new BusinessException(500, "COS 配置不完整");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
