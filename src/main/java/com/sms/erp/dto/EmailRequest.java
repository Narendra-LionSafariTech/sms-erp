package com.sms.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String to;       // Recipient email address
    private String subject;  // Subject of the email
    private String htmlBody;
}
