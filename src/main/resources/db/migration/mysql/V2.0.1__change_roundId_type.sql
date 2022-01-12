alter table player_round_scores drop constraint FKg1tm0im2hjnku2a4u9a892uha;

drop table player_round_scores;
drop table round_scores;

create table round_scores
(
    round_id varchar(255) not null,
    primary key (round_id)
) engine = InnoDB;

create table player_round_scores
(
    round_id varchar(255) not null,
    fighting_score           double precision,
    mining_score             double precision,
    movement_score           double precision,
    robot_score              double precision,
    trading_score            double precision,
    player_id                binary(255) not null,
    primary key (round_id, player_id)
) engine = InnoDB;

alter table player_round_scores add constraint FKg1tm0im2hjnku2a4u9a892uha foreign key (round_id) references round_scores (round_id);