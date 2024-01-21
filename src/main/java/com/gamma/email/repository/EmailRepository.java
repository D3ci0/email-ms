package com.gamma.email.repository;

import com.gamma.email.configuration.QueryResolver;
import com.gamma.email.model.entity.AttachmentEntity;
import com.gamma.email.model.entity.EmailEntity;
import com.gamma.email.exception.GammaServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for the database access to Emails and Attachments entities
 */
@Component
@Scope("prototype")
public class EmailRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private ResultSetExtractor<List<EmailEntity>> emailsExtractor;
    private RowMapper<AttachmentEntity> attachmentRowMapper;
    private QueryResolver queryResolver;

    /**
     *
     * @param tenantId the id of the tenant of the user who sent the email
     * @param userId the id of the user who sent the email
     * @param attachmentType the type of the email attachment (filtering criteria)
     * @param sender the sender email (filtering criteria)
     * @param from the start date (filtering criteria)
     * @param to the end date (filtering criteria)
     * @return a list of emails with theirs attachments, if present, matching the request filtering criteria
     */
    public List<EmailEntity> getEmailsWithAttachmentsByFilter(Long tenantId, Long userId, List<String> attachmentType, List<String> sender, LocalDateTime from, LocalDateTime to){
        Map<String, Object> queryParameters = new HashMap<>();
        List<EmailEntity> emailsWithAttachments;

        try {
            queryParameters.put("v_tenant_id", tenantId);
            queryParameters.put("v_user_id", userId);
            queryParameters.put("v_attachment_type", attachmentType);
            queryParameters.put("v_sender", sender);
            queryParameters.put("v_from", from);
            queryParameters.put("v_to", to);
            emailsWithAttachments = jdbcTemplate.query(queryResolver.getQueries().get("get-emails-with-attachment-by-filter"), queryParameters, emailsExtractor);
        } catch (Exception e){
            throw new GammaServiceException(e);
        }

        return emailsWithAttachments;
    }

    /**
     *
     * @param id the id of the attachment
     * @return the attachment matching the input id
     */
    public AttachmentEntity getAttachmentById(Long id){
        Map<String, Object> queryParameters = new HashMap<>();
        AttachmentEntity attachment;
        try {
            queryParameters.put("v_id", id);
            attachment = jdbcTemplate.queryForObject(queryResolver.getQueries().get("get-attachment-by-id"), queryParameters, attachmentRowMapper);
        } catch (Exception e){
            throw new GammaServiceException(e);
        }

        return attachment;
    }

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setEmailsExtractor(ResultSetExtractor<List<EmailEntity>> emailsExtractor) {
        this.emailsExtractor = emailsExtractor;
    }

    @Autowired
    public void setAttachmentRowMapper(RowMapper<AttachmentEntity> attachmentRowMapper) {
        this.attachmentRowMapper = attachmentRowMapper;
    }

    @Autowired
    public void setQueryResolver(QueryResolver queryResolver) {
        this.queryResolver = queryResolver;
    }
}
