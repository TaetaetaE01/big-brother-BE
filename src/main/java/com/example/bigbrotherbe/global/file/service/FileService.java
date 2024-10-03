package com.example.bigbrotherbe.global.file.service;

import com.example.bigbrotherbe.global.file.dto.FileDeleteDTO;
import com.example.bigbrotherbe.global.file.dto.FileSaveDTO;
import com.example.bigbrotherbe.global.file.dto.FileUpdateDTO;
import com.example.bigbrotherbe.global.file.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    boolean checkExistRequestFile(List<MultipartFile> multipartFiles);

    List<File> saveFiles(FileSaveDTO fileSaveDTO);

    List<File> updateFile(FileUpdateDTO fileUpdateDTO);

    void deleteFile(FileDeleteDTO deleteDTO);

    File saveFile(FileSaveDTO fileSaveDTO);
}
