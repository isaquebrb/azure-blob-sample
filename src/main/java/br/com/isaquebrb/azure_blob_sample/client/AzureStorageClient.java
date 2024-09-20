package br.com.isaquebrb.azure_blob_sample.client;

import com.azure.spring.cloud.core.resource.AzureStorageBlobProtocolResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AzureStorageClient {

    private static final String AZURE_BLOB_LOCATION = "azure-blob://%s/%s";

    private final ResourceLoader resourceLoader;
    private final AzureStorageBlobProtocolResolver azureBlobResolver;

    public Resource[] getBlobFiles(String filePattern, String containerName) throws IOException {
        filePattern = Optional.ofNullable(filePattern).orElse("**");
        String fileLocation = String.format(AZURE_BLOB_LOCATION, containerName, filePattern);
        return azureBlobResolver.getResources(fileLocation);
    }

    public byte[] getBlobFile(String fileName, String containerName) throws IOException {
        String fileLocation = String.format(AZURE_BLOB_LOCATION, containerName, fileName);
        return resourceLoader.getResource(fileLocation).getContentAsByteArray();
    }

    public void sendBlobFile(String fileName, byte[] fileBytes, String containerName) throws IOException {
        String fileLocation = String.format(AZURE_BLOB_LOCATION, containerName, fileName);
        Resource resource = resourceLoader.getResource(fileLocation);
        try (OutputStream blobOutputStream = ((WritableResource) resource).getOutputStream()) {
            blobOutputStream.write(fileBytes);
        }
    }
}