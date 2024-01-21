package com.gamma.email.repository;

import com.gamma.email.model.entity.AttachmentEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AttachmentRowMapper implements RowMapper<AttachmentEntity> {

    @Override
    public AttachmentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AttachmentEntity attachment = new AttachmentEntity();

        attachment.setId(rs.getLong("id"));
        attachment.setType(rs.getString("type"));
        attachment.setName(rs.getString("name"));
        attachment.setEmailId(rs.getLong("email_id"));
        attachment.setFsPath(rs.getString("fs_path"));
        attachment.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
        attachment.setUpdatedOn(rs.getTimestamp("updated_on").toLocalDateTime());

        return attachment;
    }
}
