package com.gamma.email.controller;

import com.gamma.email.model.dto.EmailResponseMessage;
import com.gamma.email.service.EmailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class EmailController {
    private EmailService emailService;

    @GetMapping("/emails/{tenantId}/{userId}")
    public EmailResponseMessage getEmailsWithAttachmentsByFilter(@PathVariable Long tenantId, @PathVariable Long userId,
                                                                 @RequestParam(required = false) List<String> attachmentType,
                                                                 @RequestParam(required = false) List<String> sender,
                                                                 @RequestParam LocalDateTime from,
                                                                 @RequestParam LocalDateTime to){


        return emailService.getEmailsWithAttachmentsByFilter(tenantId, userId, from, to, attachmentType, sender);

    }

    @GetMapping("/emails/{tenantId}/{userId}/{attachmentId}")
    public StreamingResponseBody getEmailAttachment(HttpServletResponse response,
                                                    @PathVariable Long tenantId, @PathVariable Long userId,
                                                    @PathVariable Long attachmentId){

        try {
            Resource resource = emailService.getFileResource(tenantId, userId, attachmentId);
            if(resource.exists()) {
                response.setContentType("application/octet-stream");
                response.setHeader(
                        HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"");

                return outputStream -> {
                    StreamUtils.copy(resource.getInputStream(), outputStream);
                };
            }else{
                return outputStream -> {
                    StreamUtils.copy(InputStream.nullInputStream(), outputStream);
                };
            }
        } catch (Exception e){
            return outputStream -> {
                StreamUtils.copy(InputStream.nullInputStream(), outputStream);
            };
        }
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
