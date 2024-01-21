create table USERS(
  ID bigint not null,
  TENANT_ID bigint not null,
  NAME varchar(50) not null,
  SURNAME varchar(50) not null,
  VAT_CODE varchar(50) not null,
  CREATED_ON timestamp,
  UPDATED_ON timestamp,
  PRIMARY KEY ( ID )
);
create table EMAILS(
  ID bigint not null,
  USER_ID bigint not null,
  foreign key (USER_ID) references users(ID),
  SENDER varchar(50) not null,
  RECIPIENT varchar(50) not null,
  EMAIL_OBJECT varchar(100),
  EMAIL_MESSAGE varchar(1000),
  SENT_ON timestamp,
  CREATED_ON timestamp,
  UPDATED_ON timestamp,
  PRIMARY KEY ( ID )
);
create table ATTACHMENTS(
   ID bigint not null,
   EMAIL_ID bigint not null,
   foreign key (EMAIL_ID) references emails(ID),
   TYPE varchar(20) not null,
   NAME varchar(100) not null,
   FS_PATH varchar(1000),
   CREATED_ON timestamp,
   UPDATED_ON timestamp,
   PRIMARY KEY ( ID )
 );