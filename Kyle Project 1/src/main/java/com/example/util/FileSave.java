package com.example.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface FileSave {
    String oneFile(MultipartFile file);

    ArrayList<String> multiFile(List<MultipartFile> files);
}
