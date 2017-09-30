alter table DDCUI_MESSAGE add column ENTITY_CAPTION varchar(255) ;
alter table DDCUI_MESSAGE add column RECEIVED_AT timestamp ^
update DDCUI_MESSAGE set RECEIVED_AT = current_timestamp where RECEIVED_AT is null ;
alter table DDCUI_MESSAGE alter column RECEIVED_AT set not null ;
