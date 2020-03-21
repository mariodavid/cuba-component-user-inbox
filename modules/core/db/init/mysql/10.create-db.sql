-- begin DDCUI_MESSAGE
create table DDCUI_MESSAGE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    SENDER_ID varchar(32),
    RECEIVER_ID varchar(32) not null,
    SUBJECT varchar(255),
    SHAREABLE varchar(255),
    TEXT longtext,
    READ_ boolean not null,
    RECEIVED_AT datetime(3) not null,
    --
    primary key (ID)
)^
-- end DDCUI_MESSAGE
