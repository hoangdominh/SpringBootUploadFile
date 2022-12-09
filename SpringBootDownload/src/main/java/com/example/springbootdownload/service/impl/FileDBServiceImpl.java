package com.example.springbootdownload.service.impl;

import com.example.springbootdownload.model.FileDB;
import com.example.springbootdownload.repository.FileDBRepository;
import com.example.springbootdownload.service.FileDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;


@Service
public class FileDBServiceImpl implements FileDBService {
    private final FileDBRepository fileDBRepository;

    @Autowired
    public FileDBServiceImpl(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }


    @Override
    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB image = new FileDB(fileName, file.getContentType(),file.getBytes());
        return fileDBRepository.save(image);
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
