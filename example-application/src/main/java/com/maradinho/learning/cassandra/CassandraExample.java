package com.maradinho.learning.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.maradinho.learning.cassandra.Constants.CONTACT_POINT;
import static com.maradinho.learning.cassandra.Constants.ESSENTIALS_KEYSPACE;
import static com.maradinho.learning.cassandra.Constants.MOVIES_BY_ACTOR_TABLE;
import static com.maradinho.learning.cassandra.Constants.MOVIES_TABLE;
import static com.maradinho.learning.cassandra.MovieDAO.getAllFromTable;
import static com.maradinho.learning.cassandra.MovieDAO.getMoviesWithQuery;
import static com.maradinho.learning.cassandra.MovieDAO.printMoviesByActorResultSet;
import static com.maradinho.learning.cassandra.MovieDAO.printMoviesResultSet;

public class CassandraExample {
    private static final Logger log = LoggerFactory.getLogger(CassandraExample.class);

    public static void main(String[] args) {
        Cluster cluster;
        Session session;

        cluster = Cluster.builder().addContactPoint(CONTACT_POINT).build();
        session = cluster.connect(ESSENTIALS_KEYSPACE);

        log.info("New session created: {}", session);
//        session.execute("INSERT INTO movies(movie_id, title, release_year) VALUES(uuid(), 'Blade Runner 2049', 2017)");

//      addMovie(session, "Big Trouble In Little China", 1986);
        ResultSet resultSet = getMoviesWithQuery(session);
        log.info("Retrieving movies with string query");
        printMoviesResultSet(resultSet);

        log.info("Retrieving movies with query builder");
        printMoviesResultSet(getAllFromTable(session, MOVIES_TABLE));

//       addMovieByActor(session, "Kurt Russell", UUID.fromString("238cc2e6-bf99-4486-b6fb-c5320338d320"),
//                "Big Trouble In Little China", 1986, null, null);

        printMoviesByActorResultSet(getAllFromTable(session, MOVIES_BY_ACTOR_TABLE));
    }
}
