SET @player1_id = '7228d445-0f83-4ac8-888a-e3d70314059d';
SET @player2_id = '417d25b8-1a78-49f4-855c-2091403d389b';
SET @player3_id = 'c5e8838c-dffd-4282-b9f4-58ce2996bf9c';

SET @game_id = '22838d5f-3e11-46fd-9074-d76d0e2ad27c';

SET @round1_id = 'a03cea0f-1a6b-4127-973a-658839913225';
SET @round2_id = 'fb4d537b-61e1-4165-ad01-0745504a1955';
SET @round3_id = 'c0305195-0005-4ee9-8350-023ee9e588d0';

SET @player1_transaction1 = '1e479e4c-ce06-4119-962c-25a561087622';
SET @player1_transaction2 = '17698f28-75a3-4016-9202-ec5c8d91efc3';
SET @player1_transaction3 = 'ec78782d-5814-462d-af0e-143df6c53df0';
SET @player1_transaction4 = '282d4f9c-e277-449d-bdaa-14ef8c07b1f5';
SET @player1_transaction5 = '2902b418-d91e-42d2-b83c-27f42ac807d9';

SET @player1_robot1 = 'fbe303cd-679b-4e48-bec8-7484dc0b60a7';
SET @player1_robot2 = '26d7e840-cc0b-4e4f-91ce-e1d9562ff316';

SET @player2_transaction1 = 'b35dec65-4352-43ff-9d53-d0fe64af8429';
SET @player2_transaction2 = '3e72fc6c-6db1-4361-8b00-88be18edc39b';
SET @player2_transaction3 = '83952983-b23a-4ce7-8110-a390f5f97384';
SET @player2_transaction4 = 'a29a0648-594d-4ddf-abee-9fd38dd8a129';
SET @player2_transaction5 = 'cd70d8b1-55d8-4b7d-b7a9-8e740a39a6d8';

SET @player2_robot1 = '72c96fd6-a4d5-409b-aab0-6f6d38553228';
SET @player2_robot2 = 'b75c01ad-0833-40b2-8345-640221e11e4c';

SET @player3_transaction1 = 'f64acba8-2958-4809-ad59-721005d536cf';
SET @player3_transaction2 = '0f10786e-6f83-4416-bb57-9e28694e484e';
SET @player3_transaction3 = '28051134-ab96-4348-93b7-3753cf840298';
SET @player3_transaction4 = '2926d821-23d6-4180-b48f-78c28faf29c0';
SET @player3_transaction5 = 'a1a53f5e-ae29-430a-aa06-a7530af484ea';

SET @player3_robot1 = '06ca3a1c-5232-4525-8509-f882a782091d';
SET @player3_robot2 = 'feeba2e1-b9dc-40cc-850d-29d42dc570e6';

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