-- begin DDCUI_MESSAGE
create table DDCUI_MESSAGE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SENDER_ID uuid,
    RECEIVER_ID uuid not null,
    SUBJECT varchar(255),
    SHAREABLE varchar(255),
    TEXT text,
    READ_ boolean not null,
    RECEIVED_AT timestamp not null,
    --
    primary key (ID)
)^
-- end DDCUI_MESSAGE
