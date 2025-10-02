package com.sms.erp.controller;

import com.sms.erp.dto.EmailRequest;
import com.sms.erp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send/simple/text-email")
    public ResponseEntity<String> sendTextEmail(@RequestBody EmailRequest emailRequest){

        try{
            emailService.sendSimpleTextEmail(
                    emailRequest.getTo(),
                    emailRequest.getSubject(),
                    emailRequest.getHtmlBody()
            );

            return  ResponseEntity.ok("Text Email sent");
        } catch(Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send Email" + e.getMessage());
        }
    }

    @PostMapping("/send/attachment-email")
    public ResponseEntity<String> sendEmailWithAttachment(@RequestBody EmailRequest emailRequest){

        try{
            Map<String, InputStreamSource> attachments =new HashMap<>();
            attachments.put("sample.pdf",new FileSystemResource("src/main/resources/sample.pdf"));
            emailService.sendEmailWithAttachment(
                    emailRequest.getTo(),
                    emailRequest.getSubject(),
                    emailRequest.getHtmlBody(),
                    attachments
            );

            return  ResponseEntity.ok(" Email sent");
        } catch(Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send Email" + e.getMessage());
        }
    }
}
