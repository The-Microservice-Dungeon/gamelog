/**
 * We store every event that is point/score-relevant for us in the database and need to define
 * schema, projections and repositories for that. No further comments are made, since this is a
 * lot of manual and repetitive code-monkey work...
 *
 * TODO: Well, we could have used a Kafka SQL Connector... But imo we shouldn't really use a
 *       relational database if we can't use relations at all (No relation player <-> event possible
 *       until the round has ended)
 */
package com.github.tmd.gamelog.adapter.jpa.history;