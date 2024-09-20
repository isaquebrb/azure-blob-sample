package br.com.isaquebrb.azure_blob_sample.service;

import br.com.isaquebrb.azure_blob_sample.client.AzureStorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AzureStorageService {

    private final AzureStorageClient storageClient;

    @Value("${azure.storage.blob.container}")
    private String containerName;

    public List<String> listBlobFiles(String filePattern) throws IOException {
        return Stream.of(storageClient.getBlobFiles(filePattern, containerName))
                .map(Resource::getFilename).toList();
    }

    public byte[] downloadBlobFile(String fileName) throws IOException {
        return storageClient.getBlobFile(fileName, containerName);
    }

    public void uploadFile(MultipartFile file) throws IOException {
        storageClient.sendBlobFile(file.getOriginalFilename(), file.getBytes(), containerName);
    }

    public void uploadFile(String fileName, byte[] fileBytes) throws IOException {
        storageClient.sendBlobFile(fileName, fileBytes, containerName);
    }
}
