SET @player1_id = uuid();
SET @player2_id = uuid();
SET @player3_id = uuid();

SET @game_id = uuid();

SET @round1_id = uuid();
SET @round2_id = uuid();
SET @round3_id = uuid();

SET @player1_transaction1 = uuid();
SET @player1_transaction2 = uuid();
SET @player1_transaction3 = uuid();
SET @player1_transaction4 = uuid();
SET @player1_transaction5 = uuid();

SET @player1_robot1 = uuid();
SET @player1_robot2 = uuid();

SET @player2_transaction1 = uuid();
SET @player2_transaction2 = uuid();
SET @player2_transaction3 = uuid();
SET @player2_transaction4 = uuid();
SET @player2_transaction5 = uuid();

SET @player2_robot1 = uuid();
SET @player2_robot2 = uuid();

SET @player3_transaction1 = uuid();
SET @player3_transaction2 = uuid();
SET @player3_transaction3 = uuid();
SET @player3_transaction4 = uuid();
SET @player3_transaction5 = uuid();

SET @player3_robot1 = uuid();
SET @player3_robot2 = uuid();

/* We assume to have only one running game at a time */
insert into game_status_history_jpa(id, created_at, game_id, status, TIMESTAMP)
VALUES (uuid(), now(), @game_id, 'CREATED', now()),
       (uuid(), now(), @game_id, 'STARTED', now());

/* All players are participating within the game */
insert into game_player_status_history_jpa(id, created_at, game_id, TIMESTAMP, user_id, user_name)
VALUES (uuid(), now(), @game_id, now(), @player1_id, 'Karl-Peter'),
       (uuid(), now(), @game_id, now(), @player2_id, 'Leonie 123'),
       (uuid(), now(), @game_id, now(), @player3_id, 'Gustav Gans');

/* We played 3 Rounds in the game */
insert into game_round_status_history_jpa(id, created_at, game_id, round_id, round_number, status, TIMESTAMP)
VALUES (uuid(), now(), @game_id, @round1_id, 1, 0, now()),
       (uuid(), now(), @game_id, @round1_id, 1, 1, now()),
       (uuid(), now(), @game_id, @round1_id, 1, 2, now()),
       (uuid(), now(), @game_id, @round2_id, 2, 0, now()),
       (uuid(), now(), @game_id, @round2_id, 2, 1, now()),
       (uuid(), now(), @game_id, @round2_id, 2, 2, now()),
       (uuid(), now(), @game_id, @round3_id, 3, 0, now()),
       (uuid(), now(), @game_id, @round3_id, 3, 1, now()),
       (uuid(), now(), @game_id, @round3_id, 3, 2, now());

/* Aaaand each player performed exactly 5 commands
    The number of performed commands are distributed
    in the following manner:
   +-------+-----------+----------+----------+
   | Round |  Player 1 | Player 2 | Player 3 |
   +-------------------+----------+----------+
   | 1     |  1        | 2        | 1        |
   | 2     |  2        | 1        | 1        |
   | 3     |  2        | 2        | 3        |
   +-------+-----------+----------+----------+
*/

insert into command_history_jpa(transcation_id, created_at, game_id, player_id, round_id)
VALUES (@player1_transaction1, now(), @game_id, @player1_id, @round1_id),
       (@player1_transaction2, now(), @game_id, @player1_id, @round2_id),
       (@player1_transaction3, now(), @game_id, @player1_id, @round2_id),
       (@player1_transaction4, now(), @game_id, @player1_id, @round3_id),
       (@player1_transaction5, now(), @game_id, @player1_id, @round3_id),

       (@player2_transaction1, now(), @game_id, @player2_id, @round1_id),
       (@player2_transaction2, now(), @game_id, @player2_id, @round1_id),
       (@player2_transaction3, now(), @game_id, @player2_id, @round2_id),
       (@player2_transaction4, now(), @game_id, @player2_id, @round3_id),
       (@player2_transaction5, now(), @game_id, @player2_id, @round3_id),

       (@player3_transaction1, now(), @game_id, @player3_id, @round1_id),
       (@player3_transaction2, now(), @game_id, @player3_id, @round2_id),
       (@player3_transaction3, now(), @game_id, @player3_id, @round3_id),
       (@player3_transaction4, now(), @game_id, @player3_id, @round3_id),
       (@player3_transaction5, now(), @game_id, @player3_id, @round3_id);

/* The first transactions are all mining transactions */
insert into mining_history_jpa(id, created_at, mined_amount, mined_resource, TIMESTAMP, transaction_id)
VALUES (uuid(), now(), 10, 'COAL', now(), @player1_transaction1),
       (uuid(), now(), 10, 'IRON', now(), @player2_transaction1),
       (uuid(), now(), 10, 'GEM', now(), @player3_transaction1);


/* Balances of each player
   +-------+-----------+----------+----------+
   | Round |  Player 1 | Player 2 | Player 3 |
   +-------------------+----------+----------+
   | 1     |  100      | 100      | 100      |
   | 2     |  80       | 120      | 80       |
   | 3     |  110      | 130      | 50       |
   +-------+-----------+----------+----------+
*/
insert into player_balance_history_jpa(id, balance, created_at, player_id, round_id)
VALUES (uuid(), 100, now(), @player1_id, @round1_id),
       (uuid(), 80, now(), @player1_id, @round2_id),
       (uuid(), 110, now(), @player1_id, @round3_id),

       (uuid(), 100, now(), @player2_id, @round1_id),
       (uuid(), 120, now(), @player2_id, @round2_id),
       (uuid(), 130, now(), @player2_id, @round3_id),

       (uuid(), 100, now(), @player3_id, @round1_id),
       (uuid(), 80, now(), @player3_id, @round2_id),
       (uuid(), 50, now(), @player3_id, @round3_id);

/* We just use the second transactions for trading events - although
   there should probably be more... Too much work atm
 */
insert into trading_history_jpa(id, created_at, money_change_amount, TIMESTAMP, transaction_id)
VALUES (uuid(), now(), -20, now(), @player1_transaction2),
       (uuid(), now(), 20, now(), @player2_transaction2),
       (uuid(), now(), -20, now(), @player3_transaction2);

/* We use the third transaction for fighting events, where
   Player 1 attacks player 2 and player 2 attacks player 3

   player 3 did nothing
 */
insert into fight_history_jpa(id, attacker_id, created_at, defender_id, defender_health, TIMESTAMP, transaction_id)
VALUES (uuid(), @player1_id, now(), @player2_id, 20, now(), @player1_transaction3),
       (uuid(), @player2_id, now(), @player3_id, 100, now(), @player2_transaction3);

/* Player 3 used his third transaction to block a planet */
insert into planet_block_history_jpa(id, created_at, planet_id, TIMESTAMP, transaction_id)
VALUES (uuid(), now(), uuid(), now(), @player3_transaction3);

/* The Fourth transaction was movement */
SET @player1_movement1 = uuid();
SET @player2_movement1 = uuid();
SET @player3_movement1 = uuid();

insert into movement_history_jpa(id, created_at, movement_difficulty, planet_id, TIMESTAMP, transaction_id)
VALUES (@player1_movement1, now(), 2, uuid(), now(), @player1_transaction4),
       (@player2_movement1, now(), 1, uuid(), now(), @player2_transaction4),
       (@player3_movement1, now(), 3, uuid(), now(), @player3_transaction4);

insert into movement_history_jpa_robots(movement_history_jpa_id, robots)
VALUES (@player1_movement1, @player1_robot1),
       (@player1_movement1, @player1_robot2),
       (@player2_movement1, @player2_robot1),
       (@player3_movement1, @player3_robot1),
       (@player3_movement1, @player3_robot2);

/*
  The fith transaction remains unused (as of now, but maybe it's a good testing strategy to cover even that case...
 */


/* Robot History
   We simply insert static robot values because I'm way to lazy to create a real-world-model
*/
insert into robot_history_jpa(id, alive, attack_damage, created_at, damage_level, energy, energy_level, energy_regen, energy_regen_level, health, health_level, max_energy, max_health, mining_level, mining_speed, mining_speed_level, planet_id, player_id, robot_id, round_id, storage_level)
VALUES (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player1_id, @player1_robot1, @round1_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player1_id, @player1_robot1, @round2_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player1_id, @player1_robot1, @round3_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player1_id, @player1_robot2, @round1_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player1_id, @player1_robot2, @round2_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player1_id, @player1_robot2, @round3_id, 10),

       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player2_id, @player2_robot1, @round1_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player2_id, @player2_robot1, @round2_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player2_id, @player2_robot1, @round3_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player2_id, @player2_robot2, @round1_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player2_id, @player2_robot2, @round2_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player2_id, @player2_robot2, @round3_id, 10),

       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player3_id, @player3_robot1, @round1_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player3_id, @player3_robot1, @round2_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player3_id, @player3_robot1, @round3_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player3_id, @player3_robot2, @round1_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player3_id, @player3_robot2, @round2_id, 10),
       (uuid(), 1, 10, now(), 10, 10, 10, 10, 10, 10, 100, 100, 100, 10, 10, 10, uuid(), @player3_id, @player3_robot2, @round3_id, 10);




/*
insert into round_scores(round_id)
    VALUES (@round1_id),
           (@round2_id),
           (@round3_id);

insert into player_round_scores(round_id, fighting_score, mining_score, movement_score, robot_score, trading_score, player_id)
    VALUES (@round1_id, 10, 10, 10, 10, 10, @player1_id),
           (@round2_id, 11, 11, 11, 11, 11, @player1_id),
           (@round3_id, 12, 12, 12, 12, 12, @player1_id),

           (@round1_id, 10, 10, 10, 10, 10, @player2_id),
           (@round2_id, 9, 9, 9, 9, 9, @player2_id),
           (@round3_id, 8, 8, 8, 8, 8, @player2_id),

           (@round1_id, 1, 1, 1, 1, 1, @player3_id),
           (@round2_id, 2, 2, 2, 2, 2, @player3_id),
           (@round3_id, 3, 3, 3, 3, 3, @player3_id);
*/