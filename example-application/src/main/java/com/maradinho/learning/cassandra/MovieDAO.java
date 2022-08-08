package com.maradinho.learning.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;

public class MovieDAO {
    private static final Logger log = LoggerFactory.getLogger(MovieDAO.class);

    public static ResultSet getAllFromTable(Session session, String table) {
        Statement allRecordsStmnt = QueryBuilder.select().all().from("essentials", table);
        return session.execute(allRecordsStmnt);
    }

    public static ResultSet getMoviesWithQuery(Session session) {
        return session.execute("SELECT * FROM movies");
    }

    public static UUID addMovie(Session session, String title, int releaseYear) {
        PreparedStatement statement = session.prepare("INSERT INTO movies(movie_id, title, release_year) VALUES (?, ?, ?)");
        final UUID insertedUUID = UUID.randomUUID();
        BoundStatement bs = statement.bind(insertedUUID, title, releaseYear);
        log.info("Inserting movie(title: {}, release_year: {}})", title, releaseYear);
        session.execute(bs);

        return insertedUUID;
    }

    public static void addMovieByActor(Session session, String actor, UUID movieId, String title, int releaseYear, Set<String> genres, Double rating) {
        Statement statement = QueryBuilder.insertInto("movies_by_actor")
                .value("actor", actor)
                .value("movie_id", movieId)
                .value("title", title)
                .value("release_year", releaseYear);

        log.info("Inserting movie_by_actor(actor: {}, movie_id: {}, title: {}, release_year: {})",
                actor, movieId, title, releaseYear);
        session.execute(statement);
    }

    public static void printMoviesResultSet(ResultSet resultSet) {
        log.info("Total movie records retrieved: {}", resultSet.getAvailableWithoutFetching());

        resultSet.forEach(result -> {
            log.info("movie_id: {}, title: {}, release_year: {}",
                    result.getUUID("movie_id"),
                    result.getString("title"),
                    result.getInt("release_year")
            );
        });
    }

    public static void printMoviesByActorResultSet(ResultSet resultSet) {
        log.info("Total movies_by_actor records retrieved: {}", resultSet.getAvailableWithoutFetching());

        resultSet.forEach(result -> {
            log.info("actor: {}, movie_id: {}, title: {}, release_year: {}",
                    result.getString("actor"),
                    result.getUUID("movie_id"),
                    result.getString("title"),
                    result.getInt("release_year")
            );
        });
    }
}
