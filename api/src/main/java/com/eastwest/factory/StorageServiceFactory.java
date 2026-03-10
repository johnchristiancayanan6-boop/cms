package com.eastwest.factory;

import com.eastwest.model.enums.StorageType;
import com.eastwest.service.GCPService;
import com.eastwest.service.MinioService;
import com.eastwest.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StorageServiceFactory {
    @Value("${storage.type}")
    private StorageType storageType;

    private final GCPService gcpService;
    private final MinioService minioService;

    public StorageService getStorageService() {
        switch (storageType) {
            case GCP:
                return gcpService;
            default:
                return minioService;
        }
    }
}
