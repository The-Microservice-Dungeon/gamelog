# gamelog

## Running this service

### Prerequisites

To run this service and its dependencies, the following things need to be installed:
- Docker and Docker Compose (for the Kafka and MariaDB containers required for development)
- Java in version 17 or newer
- Maven (manages the dependencies, building and execution of the Java project)

### Starting the service
First, start the Kafka and MariaDB Docker containers via Terminal (run this in the base directory of the project):
```shell
docker-compose -f src/main/docker/docker-compose.kafka.yml up -d
docker-compose -f src/main/docker/docker-compose.mariadb.yml up -d
```
This will download the required images (if they aren't found locally) and start the containers in detached mode.

As soon as both containers are running, you can start the Java application via Terminal (run this in the base directory of the project):
```shell
mvn spring-boot:run
```
This will compile the project and start the application.

### Grafana / Prometheus

We configured a Grafana Dashboard for displaying game-related metrics and some score values. The 
necessary configuration files are found in this repo as well ([Prometheus](./src/main/prometheus), [Grafana](./src/main/grafana)).

## Documentation

### Decision Log
An overview of our decisions can be found [here](https://the-microservice-dungeon.github.io/decisionlog/services/gameLog.html) on the Microservice Dungeon Decision Log.

### API Documentation
Our API Documentation can be found [here(REST API)](https://raw.githubusercontent.com/The-Microservice-Dungeon/gamelog/main/docs/api-spec.yaml) and [here(Event Specification)](https://raw.githubusercontent.com/The-Microservice-Dungeon/gamelog/main/docs/event-spec.yaml).

### Wiki
Further documentation can be found on the [wiki](https://github.com/The-Microservice-Dungeon/gamelog/wiki), including [Problems and Conclusions for further Development](https://github.com/The-Microservice-Dungeon/gamelog/wiki/Problems---Conclusions---Future-Development),
the [Scoreboard Mechanics](https://github.com/The-Microservice-Dungeon/gamelog/wiki/Scoreboard-mechanics), the [Concept of the Service](https://github.com/The-Microservice-Dungeon/gamelog/wiki/Service) and the list of [Trophies and Achievements](https://github.com/The-Microservice-Dungeon/gamelog/wiki/Trophies).

## Features
This microservice provides scoreboards and trophies for the players of the Microservice Dungeon.

### Scoreboard
The service provides a scoreboard for the total scores of the players. Whoever ranks no. 1 on this board can be considered the winner of the game.

The service also provides categorized scoreboards in 4 categories to enable competing in different aspects of the game. Those categories are:
- Fighting
- Mining
- Robot Stats
- Trading
- Traveling

The scoreboard is reachable under the `/scorebard` endpoint as defined in the [API Spec](docs/api-spec.yaml). 
However, if you call it from the browser it will render a static view of the scoreboard. 

![Scoreboard View](docs/img/scoreboard.png)

### Grafana Dashboard

We developed a Grafana Dashboard to provide various metrics including scores to the participants of 
the codefight/hackathon. 

![Grafana Dashboard](docs/img/dashboard.png)

### Achievements and Trophies

The service provides achievements and trophies.  
Achievements are awarded for the individual progress of the player in activities of the game.  
Trophies are awarded to the players that rank 1st, 2nd and 3rd on any of the scoreboards.

#### Achievements

| Category / Level | Bronze                                                                                                                                                                          | Silver                                                                                                                                                                           | Gold                                                                                                                                                                                 |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|  
| Fighting         | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png" width="250"> | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Silver%20-%20Blood%20Thirst.png" width="250"> | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Gold%20-%20Psychopath.png" width="250">           |  
| Mining           | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Mining%20Bronze%20-%20Miner.png" width="250">           | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Mining%20Silver%20-%20Gold%20Digger.png" width="250">    | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Mining%20Gold%20-%20Mining%20Industrialist.png" width="250"> |  
Trading          | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Trading%20Bronze%20-%20Businessman.png" width="250">    | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Trading%20Silver%20-%20Manager.png" width="250">         | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Trading%20Gold%20-%20Grand%20Nagus.png" width="250">         |  
| Traveling        | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Traveling%20Bronze%20-%20Sputnik.png" width="250">      | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Traveling%20Silver%20-%20Apollo%2011.png" width="250">   | <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Traveling%20Gold%20-%20Enterprise.png" width="250">          |    

#### Trophies

|Scoreboard / Rank|Third Place|Second Place|First Place|  
|---------------|-|-|-|  
|Game| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Game%20Score%20-%20Third%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Game%20Score%20-%20Second%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Game%20Score%20-%20First%20Place.png" width="250">       |  
|Fighting| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Fighting%20Score%20-%20Third%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Fighting%20Score%20-%20Second%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Fighting%20Score%20-%20First%20Place.png" width="250">   |  
|Mining| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Mining%20Score%20-%20Third%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Mining%20Score%20-%20Second%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Mining%20Score%20-%20First%20Place.png" width="250">     |  
|Trading| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Trading%20Score%20-%20Third%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Trading%20Score%20-%20Second%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Trading%20Score%20-%20First%20Place.png" width="250">    |  
|Traveling| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Traveling%20Score%20-%20Third%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Traveling%20Score%20-%20Second%20Place.png" width="250">| <img src="https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Traveling%20Score%20-%20First%20Place.png" width="250">  |

#### Copyright of the graphics
Copyright (c) André Hahn, 2022. Licensed under the Terms and Conditions of the Creative Commons CC BY-NC license.