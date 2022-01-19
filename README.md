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


## Todo
This README file should be adapted to include the following information:

(1) How can the service be started, regardless of the platform? A short, precise instruction is sufficient. Existence/familiarity with tools like maven can be assumed.

(2) What other prerequisites must be in place for the service to run? For example, linking to further documentation of required infrastructure such as Kafka from the DevOps team.

(3) Are there decisions from the decision log that should be further elaborated (like domain model)? Depending on the level of detail, describe in more detail here or in the wiki.

(4) Are there further links that promote navigation or understanding? For example, link to API documentation or important decisions in the decision log.

_Note_: The design of the task planning and review is up to the team. One possible option is to use a Kanban board directly via GitHub.
