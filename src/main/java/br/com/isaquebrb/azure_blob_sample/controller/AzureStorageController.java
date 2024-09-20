package br.com.isaquebrb.azure_blob_sample.controller;

import br.com.isaquebrb.azure_blob_sample.service.AzureStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/azure-storage")
public class AzureStorageController {

    private final AzureStorageService service;

    @GetMapping("/blob")
    @ResponseStatus(HttpStatus.OK)
    public List<String> listBlobFiles(@RequestParam(required = false) String filePattern) throws IOException {
        return service.listBlobFiles(filePattern);
    }

    @GetMapping("/blob/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] downloadBlobFile(@PathVariable String fileName) throws IOException {
        return service.downloadBlobFile(fileName);
    }

    @PostMapping("/blob")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(@RequestParam MultipartFile file) throws IOException {
        service.uploadFile(file);
    }
}
