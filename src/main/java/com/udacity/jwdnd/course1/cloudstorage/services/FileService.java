package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public List<File> getFileByUserId(Integer userId) { return fileMapper.getFiles(userId); }

    public File getFileById(Integer fileId) { return fileMapper.getFileById(fileId); }

    public boolean checkFileName(Integer userId, String fileName) {
        return (fileMapper.isFileNameDuplicate(userId, fileName).compareTo("Y") == 0);
    }

    public void insertFile(MultipartFile file, Integer userId) throws IOException {
        File newFile = new File();

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        newFile.setFileName(fileName);
        newFile.setContentType(file.getContentType());
        newFile.setFileSize(String.valueOf(file.getSize()));
        newFile.setUserId(userId);
        newFile.setFileData(file.getInputStream().readAllBytes());

        fileMapper.insertFile(newFile);
    }

    public void deleteFileById(Integer fileId) { fileMapper.deleteFileById(fileId); }
}
