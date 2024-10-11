package com.example.bigbrotherbe.global.file.util;

import com.example.bigbrotherbe.global.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.bigbrotherbe.global.common.exception.enums.ErrorCode.INVALID_IMAGE_TYPE;
import static com.example.bigbrotherbe.global.common.exception.enums.ErrorCode.INVALID_PDF_TYPE;

@Component
@RequiredArgsConstructor
public class FileUtil {
    public void CheckImageFiles(List<MultipartFile> multipartFiles) {
        multipartFiles.stream()
                .filter(file -> {
                    String contentType = file.getContentType();
                    return contentType == null || !(contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                            contentType.equals("image/jpg") ||
                            contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                            contentType.equals(MediaType.IMAGE_GIF_VALUE));
                })
                .findFirst()
                .ifPresent(file -> {
                    throw new BusinessException(INVALID_IMAGE_TYPE);
                });
    }

    public void checkPdfFiles(List<MultipartFile> multipartFiles) {
        multipartFiles.stream()
                .filter(file -> {
                    String contentType = file.getContentType();
                    return contentType == null || !contentType.equals(MediaType.APPLICATION_PDF_VALUE);
                })
                .findFirst()
                .ifPresent(file -> {
                    throw new BusinessException(INVALID_PDF_TYPE);
                });
    }

    public void checkPdfFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals(MediaType.APPLICATION_PDF_VALUE)) {
            throw new BusinessException(INVALID_PDF_TYPE);
        }
    }
}
