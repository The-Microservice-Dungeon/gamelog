CREATE TABLE command_history_jpa (transcation_id varchar(255) NOT NULL,
                                  created_at datetime(6) NOT NULL,
                                  game_id varchar(255) NOT NULL,
                                  player_id varchar(255) NOT NULL,
                                  round_id varchar(255) NOT NULL,
                                  PRIMARY KEY (transcation_id)) ENGINE=InnoDB;


CREATE TABLE fight_history_jpa (id binary(255) NOT NULL,
                                attacker_id varchar(255) NOT NULL,
                                created_at datetime(6) NOT NULL,
                                defender_id varchar(255) NOT NULL,
                                defender_health integer NOT NULL,
                                TIMESTAMP datetime(6) NOT NULL,
                                transaction_id varchar(255) NOT NULL,
                                PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE game_player_status_history_jpa (id binary(255) NOT NULL,
                                             created_at datetime(6) NOT NULL,
                                             game_id varchar(255) NOT NULL,
                                             status varchar(255) NOT NULL,
                                             TIMESTAMP datetime(6) NOT NULL,
                                             user_id varchar(255) NOT NULL,
                                             user_name varchar(255) NOT NULL,
                                             PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE game_round_status_history_jpa (id binary(255) NOT NULL,
                                            created_at datetime(6) NOT NULL,
                                            game_id varchar(255) NOT NULL,
                                            round_id varchar(255) NOT NULL,
                                            round_number integer NOT NULL,
                                            status integer NOT NULL,
                                            TIMESTAMP datetime(6) NOT NULL,
                                            PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE game_status_history_jpa (id binary(255) NOT NULL,
                                      created_at datetime(6) NOT NULL,
                                      game_id varchar(255) NOT NULL,
                                      status varchar(255) NOT NULL,
                                      TIMESTAMP datetime(6) NOT NULL,
                                      PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE hibernate_sequence (next_val bigint) ENGINE=InnoDB;


INSERT INTO hibernate_sequence
VALUES (1);


CREATE TABLE mining_history_jpa (id binary(255) NOT NULL,
                                 created_at datetime(6) NOT NULL,
                                 mined_amount integer NOT NULL,
                                 mined_resource varchar(255) NOT NULL,
                                 TIMESTAMP datetime(6) NOT NULL,
                                 transaction_id varchar(255) NOT NULL,
                                 PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE movement_history_jpa (id binary(255) NOT NULL,
                                   created_at datetime(6) NOT NULL,
                                   movement_difficulty integer NOT NULL,
                                   planet_id varchar(255) NOT NULL,
                                   TIMESTAMP datetime(6) NOT NULL,
                                   transaction_id varchar(255) NOT NULL,
                                   PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE movement_history_jpa_robots (movement_history_jpa_id binary(255) NOT NULL,
                                          robots varchar(255)) ENGINE=InnoDB;


CREATE TABLE planet_block_history_jpa (id binary(255) NOT NULL,
                                       created_at datetime(6) NOT NULL,
                                       planet_id varchar(255) NOT NULL,
                                       TIMESTAMP datetime(6) NOT NULL,
                                       transaction_id varchar(255) NOT NULL,
                                       PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE player_balance_history_jpa (id binary(255) NOT NULL,
                                         balance integer NOT NULL,
                                         created_at datetime(6) NOT NULL,
                                         player_id varchar(255) NOT NULL,
                                         assumed_round_id varchar(255) NOT NULL,
                                         PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE player_dto (player_id binary(255) NOT NULL,
                         PRIMARY KEY (player_id)) ENGINE=InnoDB;


CREATE TABLE player_trophy_dto (player_trophy_id bigint NOT NULL,
                                date_awarded datetime(6) NOT NULL,
                                game_id binary(255) NOT NULL,
                                trophy_id bigint, player_id binary(255),
                                PRIMARY KEY (player_trophy_id)) ENGINE=InnoDB;


CREATE TABLE robot_history_jpa (id binary(255) NOT NULL,
                                alive bit NOT NULL,
                                attack_damage integer NOT NULL,
                                created_at datetime(6) NOT NULL,
                                damage_level integer NOT NULL,
                                energy integer NOT NULL,
                                energy_level integer NOT NULL,
                                energy_regen integer NOT NULL,
                                energy_regen_level integer NOT NULL,
                                health integer NOT NULL,
                                health_level integer NOT NULL,
                                max_energy integer NOT NULL,
                                max_health integer NOT NULL,
                                mining_level integer NOT NULL,
                                mining_speed integer NOT NULL,
                                mining_speed_level integer NOT NULL,
                                planet_id varchar(255) NOT NULL,
                                player_id varchar(255) NOT NULL,
                                robot_id varchar(255) NOT NULL,
                                round_id varchar(255) NOT NULL,
                                storage_level integer NOT NULL,
                                PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE round_score_dto (id bigint NOT NULL,
                              game_id varchar(255),
                              value integer NOT NULL,
                              player_id binary(255),
                              round_id varchar(255),
                              round_number integer NOT NULL,
                              PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE trading_history_jpa (id binary(255) NOT NULL,
                                  created_at datetime(6) NOT NULL,
                                  money_change_amount integer NOT NULL,
                                  TIMESTAMP datetime(6) NOT NULL,
                                  transaction_id varchar(255) NOT NULL,
                                  PRIMARY KEY (id)) ENGINE=InnoDB;


CREATE TABLE trophy_dto (trophy_id bigint NOT NULL,
                         badge_url varchar(255) NOT NULL,
                         name varchar(255) NOT NULL,
                         trophy_type integer NOT NULL,
                         PRIMARY KEY (trophy_id)) ENGINE=InnoDB;


ALTER TABLE fight_history_jpa ADD CONSTRAINT UK_kcd0d7c3euxnaaxctqqc5prkn UNIQUE (transaction_id);


ALTER TABLE mining_history_jpa ADD CONSTRAINT UK_b2h358d6vqi4bggkkfdurl0wf UNIQUE (transaction_id);


ALTER TABLE movement_history_jpa ADD CONSTRAINT UK_i2tlsj052cxi52l75roffrsm9 UNIQUE (transaction_id);


ALTER TABLE planet_block_history_jpa ADD CONSTRAINT UK_jcnkd8lt0b245smbekx3xp7yq UNIQUE (transaction_id);


ALTER TABLE player_balance_history_jpa ADD CONSTRAINT UK6bbr48kk29it2ecs248uegyi9 UNIQUE (player_id,
                                                                                          assumed_round_id);


ALTER TABLE robot_history_jpa ADD CONSTRAINT UKfic8ih6lqfwcf9qxtasf9ugtt UNIQUE (round_id,
                                                                                 robot_id);


ALTER TABLE trading_history_jpa ADD CONSTRAINT UK_mhby2l03xpuqtq8mhuhybfvq UNIQUE (transaction_id);


ALTER TABLE trophy_dto ADD CONSTRAINT UK_p7i9j05eye85kxsihpdduwg2b UNIQUE (name);


ALTER TABLE trophy_dto ADD CONSTRAINT UK_2duj867upyp7m4fx4ils82fi UNIQUE (trophy_type);


ALTER TABLE movement_history_jpa_robots ADD CONSTRAINT FKaa6f850rdyc3v1y3vlymovbm1
    FOREIGN KEY (movement_history_jpa_id) REFERENCES movement_history_jpa (id);


ALTER TABLE player_trophy_dto ADD CONSTRAINT FKssmsemjwhcw3fg81mfrir549i
    FOREIGN KEY (trophy_id) REFERENCES trophy_dto (trophy_id);


ALTER TABLE player_trophy_dto ADD CONSTRAINT FKr9hgoc15msy9vnn5e6v06usxc
    FOREIGN KEY (player_id) REFERENCES player_dto (player_id);