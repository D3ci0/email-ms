package com.gamma.email.helper;

import com.gamma.email.model.dto.Attachment;
import com.gamma.email.model.dto.Email;
import com.gamma.email.model.dto.EmailResponseMessage;
import com.gamma.email.model.dto.Result;
import com.gamma.email.model.entity.AttachmentEntity;
import com.gamma.email.model.entity.EmailEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageConverter {
    //Creates a success response message converting entity objects to DTOs
    public EmailResponseMessage successMessage(List<EmailEntity> emails){
        EmailResponseMessage emailResponseMessage = new EmailResponseMessage();


        for(EmailEntity e : emails){
            Email email = new Email();
            email.setEmailMessage(e.getEmailMessage());
            email.setEmailObject(e.getEmailObject());
            email.setId(e.getId());
            email.setUserId(e.getUserId());
            email.setSender(e.getSender());
            email.setRecipient(e.getRecipient());
            email.setSentOn(e.getSentOn());
            email.setAttachments(getEmailAttachment(e));
            emailResponseMessage.addEmail(email);
        }
        Result result = new Result("0", "SUCCESS");
        emailResponseMessage.setResult(result);

        return emailResponseMessage;
    }

    private List<Attachment> getEmailAttachment(EmailEntity e){
        List<Attachment> emailAttachments = new ArrayList<>(e.getAttachments().size());

        for(AttachmentEntity a : e.getAttachments()){
            Attachment attachment = new Attachment();
            attachment.setId(a.getId());
            attachment.setName(a.getName());
            attachment.setType(a.getType());
            emailAttachments.add(attachment);
        }

        return emailAttachments;
    }

    //Creates a default error response message
    public EmailResponseMessage errorMessage(){
        EmailResponseMessage emailResponseMessage = new EmailResponseMessage();

        Result result = new Result("-1", "GENERIC_ERROR");
        emailResponseMessage.setResult(result);

        return emailResponseMessage;
    }
}
