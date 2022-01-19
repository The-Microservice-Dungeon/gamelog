SET @player1_id = '7228d445-0f83-4ac8-888a-e3d70314059d';
SET @player2_id = '417d25b8-1a78-49f4-855c-2091403d389b';
SET @player3_id = 'c5e8838c-dffd-4282-b9f4-58ce2996bf9c';

SET @game_id = '22838d5f-3e11-46fd-9074-d76d0e2ad27c';

SET @round1_id = 'a03cea0f-1a6b-4127-973a-658839913225';
SET @round2_id = 'fb4d537b-61e1-4165-ad01-0745504a1955';
SET @round3_id = 'c0305195-0005-4ee9-8350-023ee9e588d0';


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