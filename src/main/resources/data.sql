INSERT INTO users (id, tenant_id, name, surname, vat_code, created_on, updated_on) VALUES (1, 1, 'Mario', 'Rossi', 'MRORSI55I16M321U', {ts '2024-01-01 18:00:00.00'}, {ts '2024-01-01 18:00:00.00'});
INSERT INTO users (id, tenant_id, name, surname, vat_code, created_on, updated_on) VALUES (2, 2, 'John', 'Doe', 'JHNDOE45I16Y121U', {ts '2024-01-01 18:00:00.00'}, {ts '2024-01-01 18:00:00.00'});


INSERT INTO emails (id, user_id, sender, recipient, email_object, email_message, sent_on, created_on, updated_on) VALUES (1, 1, 'mrossi@casella.pec', 'jhdoe@casella.pec', 'prova', 'prova', {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'});
INSERT INTO emails (id, user_id, sender, recipient, email_object, email_message, sent_on, created_on, updated_on) VALUES (3, 1, 'mrossisecond@casella.pec', 'jhdoe@casella.pec', 'altra prova', 'seconda prova', {ts '2024-01-01 19:10:00.00'}, {ts '2024-01-01 19:10:00.00'}, {ts '2024-01-01 19:10:00.00'});
INSERT INTO emails (id, user_id, sender, recipient, email_object, email_message, sent_on, created_on, updated_on) VALUES (2, 2, 'jhdoe@casella.pec', 'mrossi@casella.pec', 'prova', 'prova', {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'});


INSERT INTO attachments (id, email_id, type, name, fs_path, created_on, updated_on) VALUES (1, 1, '.pdf', 'prova', '/2024/1/1/', {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'});
INSERT INTO attachments (id, email_id, type, name, fs_path, created_on, updated_on) VALUES (2, 1, '.txt', 'prova', '/2024/1/1/', {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'});
INSERT INTO attachments (id, email_id, type, name, fs_path, created_on, updated_on) VALUES (5, 3, '.docx', 'prova word', '/2024/1/3/', {ts '2024-01-01 19:10:00.00'}, {ts '2024-01-01 19:10:00.00'});
INSERT INTO attachments (id, email_id, type, name, fs_path, created_on, updated_on) VALUES (3, 2, '.pdf', 'prova', '/2024/1/2/', {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'});
INSERT INTO attachments (id, email_id, type, name, fs_path, created_on, updated_on) VALUES (4, 2, '.txt', 'prova', '/2024/1/2/', {ts '2024-01-01 19:00:00.00'}, {ts '2024-01-01 19:00:00.00'});