package com.gamma.email.service;

import com.gamma.email.helper.MessageConverter;
import com.gamma.email.model.dto.EmailResponseMessage;
import com.gamma.email.model.entity.AttachmentEntity;
import com.gamma.email.model.entity.EmailEntity;
import com.gamma.email.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private EmailRepository emailRepository;
    private MessageConverter messageConverter;

    public EmailResponseMessage getEmailsWithAttachmentsByFilter(Long tenantId, Long userId,
                                                                 LocalDateTime from, LocalDateTime to,
                                                                 List<String> attachmentType, List<String> sender){
        EmailResponseMessage emailResponseMessage;
        logger.debug("Request sent by user {} of tenant {}", userId, tenantId);
        logger.debug("Request filtering criteria: from {}, to {}, attachment type {}, sender {}", from, to, attachmentType, sender);

        try{
            List<EmailEntity> emails = emailRepository.getEmailsWithAttachmentsByFilter(tenantId, userId, attachmentType, sender, from, to);
            emailResponseMessage = messageConverter.successMessage(emails);
        } catch (Exception e){
            logger.error("Exception while fetching emails", e);
            emailResponseMessage = messageConverter.errorMessage();
        }
        logger.debug("Response message: {}", emailResponseMessage);

        return emailResponseMessage;
    }

    public Resource getFileResource(Long tenantId, Long userId, Long attachmentId){
        Resource resource = null;
        try {
            AttachmentEntity attachmentEntity = emailRepository.getAttachmentById(attachmentId);
            logger.debug("Attachment file system path {}", attachmentEntity.getFsPath());
            String separator = FileSystems.getDefault().getSeparator();
            String attachmentPath = separator + tenantId + separator + userId +
                    attachmentEntity.getFsPath() + attachmentEntity.getName() + attachmentEntity.getType();

            logger.debug("Attachment complete file system path {}", attachmentPath);
            resource = new ClassPathResource(attachmentPath);
            if(resource.exists()){
                logger.debug("Resource found");
            }else{
                logger.debug("Resource not found");
            }
        }catch (Exception e){
            logger.error("Exception while getting the attachment with id {}", attachmentId, e);
        }

        return resource;
    }

    @Autowired
    public void setEmailRepository(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Autowired
    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}
