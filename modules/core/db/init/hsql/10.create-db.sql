-- begin DDCUI_MESSAGE
create table DDCUI_MESSAGE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SENDER_ID varchar(36),
    RECEIVER_ID varchar(36) not null,
    SUBJECT varchar(255),
    ENTITY_REFERENCE_ID varchar(255),
    ENTITY_REFERENCE_CLASS varchar(255),
    TEXT longvarchar,
    READ_ boolean not null,
    --
    primary key (ID)
)^
-- end DDCUI_MESSAGE
