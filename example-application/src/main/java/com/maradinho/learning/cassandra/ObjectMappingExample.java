package com.maradinho.learning.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.maradinho.learning.cassandra.Constants.CONTACT_POINT;
import static com.maradinho.learning.cassandra.MovieDAO.getAllFromTable;
import static com.maradinho.learning.cassandra.MovieDAO.printMoviesByActorResultSet;
import static com.maradinho.learning.cassandra.Constants.ESSENTIALS_KEYSPACE;

public class ObjectMappingExample {

    private static final Logger log = LoggerFactory.getLogger(ObjectMappingExample.class);


    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint(CONTACT_POINT).build();
        Session session = cluster.connect(ESSENTIALS_KEYSPACE);

        MappingManager mappingManager = new MappingManager(session);
        Mapper<MovieByActor> mapper = mappingManager.mapper(MovieByActor.class);
        MovieByActor mba = mapper.get("Brad Pitt", 2011, UUID.fromString("6c6725db-2a3f-46b7-804e-31c6cd5e4e09"));
        log.info("movie_by_actor: {}", mba.toString());

//        MovieByActor rg = new MovieByActor("Ryan Gosling", 2016, UUID.fromString("0a3b2105-c8aa-4840-9633-e8f359946d9f"),
//                "La La Land", 10F, new HashSet<>(List.of("drama", "musical")));
//        mapper.save(rg);

        printMoviesByActorResultSet(getAllFromTable(session, Constants.MOVIES_BY_ACTOR_TABLE));
    }


}
