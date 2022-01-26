ALTER TABLE player_trophy_dto drop constraint FKr9hgoc15msy9vnn5e6v06usxc;

alter table player_dto add column player_id_new varchar(255);
alter table player_trophy_dto add column player_id_new varchar(255);

update player_trophy_dto
set player_trophy_dto.player_id_new = LOWER(CONCAT(
        SUBSTR(HEX(player_id), 1, 8), '-',
        SUBSTR(HEX(player_id), 9, 4), '-',
        SUBSTR(HEX(player_id), 13, 4), '-',
        SUBSTR(HEX(player_id), 17, 4), '-',
        SUBSTR(HEX(player_id), 21, 12)
    ))
WHERE 1 = 1;

update player_dto
set player_dto.player_id_new = LOWER(CONCAT(
        SUBSTR(HEX(player_id), 1, 8), '-',
        SUBSTR(HEX(player_id), 9, 4), '-',
        SUBSTR(HEX(player_id), 13, 4), '-',
        SUBSTR(HEX(player_id), 17, 4), '-',
        SUBSTR(HEX(player_id), 21, 12)
    ))
WHERE 1 = 1;

alter table player_dto drop primary key;
alter table player_trophy_dto drop primary key;

alter table player_dto drop column player_id;
alter table player_trophy_dto drop column player_id;

alter table player_dto change player_id_new player_id varchar(255);
alter table player_trophy_dto change player_id_new player_id varchar(255);

alter table player_dto add primary key (player_id);
alter table player_trophy_dto add primary key (player_id);

ALTER TABLE player_trophy_dto ADD CONSTRAINT FKr9hgoc15msy9vnn5e6v06usxc
    FOREIGN KEY (player_id) REFERENCES player_dto (player_id);

select * from player_dto;