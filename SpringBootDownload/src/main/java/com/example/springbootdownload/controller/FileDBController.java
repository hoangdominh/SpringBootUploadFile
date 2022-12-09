package com.example.springbootdownload.controller;

import com.example.springbootdownload.dto.response.FileDBResponse;
import com.example.springbootdownload.dto.response.MessageResponse;
import com.example.springbootdownload.model.FileDB;
import com.example.springbootdownload.repository.FileDBRepository;
import com.example.springbootdownload.service.FileDBService;
import com.example.springbootdownload.service.impl.FileDBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",maxAge = 3600)

/**
 @CrossOrigin : để cho phép config origins
 @RestController : là một composed annotation được kết từ annotation @Controller và @ResponseBody
 @RequestMapping : được sử dụng để map request với class hoặc method xử lý request đó
 */
public class FileDBController {

    private final FileDBRepository fileDBRepository;

    private final FileDBService fileDBService;

    @Autowired
    public FileDBController(FileDBRepository fileDBRepository, FileDBServiceImpl fileDBService) {
        this.fileDBRepository = fileDBRepository;
        this.fileDBService = fileDBService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file")MultipartFile file) {
        String message = "";
        try{
            fileDBService.store(file);
            message = "Upload the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        }catch (Exception e){
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileDBResponse>> getListFiles() {
        List<FileDBResponse> files = fileDBService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/auth/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new FileDBResponse(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional< FileDB> optionalFileDB = fileDBRepository.findById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+optionalFileDB.get().getName()+"\"")
                .body(optionalFileDB.get().getData());
    }

}
