drop table if exists command_history_jpa cascade;

drop table if exists fight_history_jpa cascade;

drop table if exists  game_player_status_history_jpa cascade;

drop table if exists  game_round_status_history_jpa cascade;

drop table if exists  game_status_history_jpa cascade;

drop table if exists  hibernate_sequence cascade;

drop table if exists  mining_history_jpa cascade;

drop table if exists  movement_history_jpa_robots cascade;

drop table if exists  movement_history_jpa cascade;

drop table if exists  planet_block_history_jpa cascade;

drop table if exists  player_balance_history_jpa cascade;

drop table if exists  player_round_scores cascade;

drop table if exists  player_trophy_dto cascade;

drop table if exists  player_dto cascade;

drop table if exists  robot_history_jpa cascade;

drop table if exists  round_scores cascade;

drop table if exists  trading_history_jpa cascade;

drop table if exists  trophy_dto cascade;

create table command_history_jpa
(
    transcation_id varchar(255) not null,
    created_at     datetime(6)  not null,
    game_id        varchar(255) not null,
    player_id      varchar(255) not null,
    round_id       varchar(255) not null,
    primary key (transcation_id)
) engine = InnoDB;
create table fight_history_jpa
(
    id              varchar(255) not null,
    attacker_id     varchar(255) not null,
    created_at      datetime(6)  not null,
    defender_id     varchar(255) not null,
    defender_health integer      not null,
    timestamp       datetime(6)  not null,
    transaction_id  varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table game_player_status_history_jpa
(
    id         varchar(255) not null,
    created_at datetime(6)  not null,
    game_id    varchar(255) not null,
    timestamp  datetime(6)  not null,
    user_id    varchar(255) not null,
    user_name  varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table game_round_status_history_jpa
(
    id           varchar(255) not null,
    created_at   datetime(6)  not null,
    game_id      varchar(255) not null,
    round_id     varchar(255) not null,
    round_number integer      not null,
    status       integer      not null,
    timestamp    datetime(6)  not null,
    primary key (id)
) engine = InnoDB;
create table game_status_history_jpa
(
    id         varchar(255) not null,
    created_at datetime(6)  not null,
    game_id    varchar(255) not null,
    status     varchar(255) not null,
    timestamp  datetime(6)  not null,
    primary key (id)
) engine = InnoDB;
create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;
insert into hibernate_sequence
values (1);
create table mining_history_jpa
(
    id             varchar(255) not null,
    created_at     datetime(6)  not null,
    mined_amount   integer      not null,
    mined_resource varchar(255) not null,
    timestamp      datetime(6)  not null,
    transaction_id varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table movement_history_jpa
(
    id                  varchar(255) not null,
    created_at          datetime(6)  not null,
    movement_difficulty integer      not null,
    planet_id           varchar(255) not null,
    timestamp           datetime(6)  not null,
    transaction_id      varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table movement_history_jpa_robots
(
    movement_history_jpa_id varchar(255) not null,
    robots                  varchar(255)
) engine = InnoDB;
create table planet_block_history_jpa
(
    id             varchar(255) not null,
    created_at     datetime(6)  not null,
    planet_id      varchar(255) not null,
    timestamp      datetime(6)  not null,
    transaction_id varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table player_round_scores
(
    round_id       varchar(255)  not null,
    fighting_score double precision,
    mining_score   double precision,
    movement_score double precision,
    robot_score    double precision,
    trading_score  double precision,
    player_id      varchar(255) not null,
    primary key (round_id, player_id)
) engine = InnoDB;
create table player_balance_history_jpa
(
    id         varchar(255) not null,
    balance    integer      not null,
    created_at datetime(6)  not null,
    player_id  varchar(255) not null,
    round_id   varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table player_dto
(
    player_id   varchar(255) not null,
    player_name varchar(255) not null,
    primary key (player_id)
) engine = InnoDB;
create table player_trophy_dto
(
    player_trophy_id bigint       not null,
    date_awarded     datetime(6)  not null,
    game_id          varchar(255) not null,
    trophy_id        bigint,
    player_id        varchar(255),
    primary key (player_trophy_id)
) engine = InnoDB;
create table robot_history_jpa
(
    id                 varchar(255) not null,
    alive              bit          not null,
    attack_damage      integer      not null,
    created_at         datetime(6)  not null,
    damage_level       integer      not null,
    energy             integer      not null,
    energy_level       integer      not null,
    energy_regen       integer      not null,
    energy_regen_level integer      not null,
    health             integer      not null,
    health_level       integer      not null,
    max_energy         integer      not null,
    max_health         integer      not null,
    mining_level       integer      not null,
    mining_speed       integer      not null,
    mining_speed_level integer      not null,
    planet_id          varchar(255) not null,
    player_id          varchar(255) not null,
    robot_id           varchar(255) not null,
    round_id           varchar(255) not null,
    storage_level      integer      not null,
    primary key (id)
) engine = InnoDB;
create table round_scores
(
    round_id varchar(255) not null,
    primary key (round_id)
) engine = InnoDB;
create table trading_history_jpa
(
    id                  varchar(255) not null,
    created_at          datetime(6)  not null,
    money_change_amount integer      not null,
    timestamp           datetime(6)  not null,
    transaction_id      varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table trophy_dto
(
    trophy_id   bigint       not null,
    badge_url   varchar(255) not null,
    name        varchar(255) not null,
    trophy_type integer      not null,
    primary key (trophy_id)
) engine = InnoDB;
alter table fight_history_jpa
    add constraint UK_kcd0d7c3euxnaaxctqqc5prkn unique (transaction_id);
alter table mining_history_jpa
    add constraint UK_b2h358d6vqi4bggkkfdurl0wf unique (transaction_id);
alter table movement_history_jpa
    add constraint UK_i2tlsj052cxi52l75roffrsm9 unique (transaction_id);
alter table planet_block_history_jpa
    add constraint UK_jcnkd8lt0b245smbekx3xp7yq unique (transaction_id);
alter table player_balance_history_jpa
    add constraint UK6bbr48kk29it2ecs248uegyi9 unique (player_id, round_id);
alter table robot_history_jpa
    add constraint UKfic8ih6lqfwcf9qxtasf9ugtt unique (round_id, robot_id);
alter table trading_history_jpa
    add constraint UK_mhby2l03xpuqtq8mhuhybfvq unique (transaction_id);
alter table trophy_dto
    add constraint UK_p7i9j05eye85kxsihpdduwg2b unique (name);
alter table trophy_dto
    add constraint UK_2duj867upyp7m4fx4ils82fi unique (trophy_type);
alter table movement_history_jpa_robots
    add constraint FKaa6f850rdyc3v1y3vlymovbm1 foreign key (movement_history_jpa_id) references movement_history_jpa (id);
alter table player_round_scores
    add constraint FKi6ocuasmtnb9rdu3ekev7ymgy foreign key (round_id) references round_scores (round_id);
alter table player_trophy_dto
    add constraint FKssmsemjwhcw3fg81mfrir549i foreign key (trophy_id) references trophy_dto (trophy_id);
alter table player_trophy_dto
    add constraint FKr9hgoc15msy9vnn5e6v06usxc foreign key (player_id) references player_dto (player_id);
