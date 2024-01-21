package com.gamma.email.repository;

import com.gamma.email.model.entity.AttachmentEntity;
import com.gamma.email.model.entity.EmailEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class EmailExtractor implements ResultSetExtractor<List<EmailEntity>> {
    @Override
    public List<EmailEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<EmailEntity> emails = new LinkedList<>();
        Map<Long, EmailEntity> alreadyGot = new HashMap<>();
        while(rs.next()) {
            Long id = rs.getLong("email_id");
            EmailEntity current;
            if(!alreadyGot.containsKey(id)){
                current = new EmailEntity();
                current.setId(id);
                current.setUserId(rs.getLong("user_id"));
                current.setSender(rs.getString("sender"));
                current.setRecipient(rs.getString("recipient"));
                current.setEmailObject(rs.getString("email_object"));
                current.setEmailMessage(rs.getString("email_message"));
                current.setSentOn(rs.getTimestamp("sent_on").toLocalDateTime());
                current.setCreatedOn(rs.getTimestamp("e_created_on").toLocalDateTime());
                current.setUpdatedOn(rs.getTimestamp("e_updated_on").toLocalDateTime());
                alreadyGot.put(id, current);
                emails.add(current);
            }else{
                current = alreadyGot.get(id);
            }
            AttachmentEntity attachment = new AttachmentEntity();
            attachment.setId(rs.getLong("attachment_id"));
            attachment.setType(rs.getString("type"));
            attachment.setName(rs.getString("name"));
            attachment.setEmailId(id);
            attachment.setFsPath(rs.getString("fs_path"));
            attachment.setCreatedOn(rs.getTimestamp("a_created_on").toLocalDateTime());
            attachment.setUpdatedOn(rs.getTimestamp("a_updated_on").toLocalDateTime());
            current.addAttachment(attachment);
        }

        return emails;
    }
}
