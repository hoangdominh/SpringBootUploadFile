package com.example.springbootdownload.service;

import com.example.springbootdownload.model.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileDBService {

    /**
     store(file): nhận đối tượng MultipartFile, chuyển đổi thành đối tượng FileDB và lưu nó vào database
     */
    FileDB store(MultipartFile file) throws IOException;


    /**
     getAllFiles() : trả về tất cả các tệp được lưu trữ dưới dạng danh sách FileDB
     */
    Stream<FileDB> getAllFiles();
}
