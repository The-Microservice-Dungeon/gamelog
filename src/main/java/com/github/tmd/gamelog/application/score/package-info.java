/**
 * Here the magic score calculation happens...
 * TODO: It is way to complex which comes from the fact, that we have to store every event in the
 *       database, retrieve, aggregate, accumulate them manual to a score. Therefore we also have
 *       NO TEST AT ALL... We should really introduce at least some tests for the crucial parts -
 *       maybe populate a database via SQL Dump/Script and then do some assertions...
 */
package com.github.tmd.gamelog.application.score;