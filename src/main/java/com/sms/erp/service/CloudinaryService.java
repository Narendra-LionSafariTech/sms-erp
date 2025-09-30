package com.sms.erp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudinaryService {
    public String uploadFile(MultipartFile file) throws IOException {
        // integrate Cloudinary SDK here
        return "https://cloudinary.com/fake-uploaded-url.jpg";
    }
}
