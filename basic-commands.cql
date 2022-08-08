CREATE KEYSPACE essentials WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};
USE essentials;

CREATE TABLE movies(
    movie_id UUID,
    title TEXT,
    release_year INT,
    PRIMARY KEY ((movie_id))
 );

// Below could use uuid() function to create a new UUID for the movies ID

INSERT INTO movies(movie_id, release_year, title) VALUES(6c6725db-2a3f-46b7-804e-31c6cd5e4e09, 2011, 'Tree of Life');
INSERT INTO movies(movie_id, release_year, title) VALUES(0a3b2105-c8aa-4840-9633-e8f359946d9f, 2016, 'La La Land');
INSERT INTO movies(movie_id, release_year, title) VALUES(8403868b-018a-4d1f-b647-e978671efa3f, 2014, 'Birdman');

CREATE TABLE movies_by_actor(
    actor TEXT,
    release_year INT,
    movie_id UUID,
    title TEXT,
    genres SET<TEXT>,
    rating FLOAT,
    PRIMARY KEY ((actor), release_year, movie_id)
 ) WITH CLUSTERING ORDER BY (release_year DESC, movie_id ASC);

INSERT INTO movies_by_actor(actor, release_year, movie_id, title, genres, rating)
    VALUES('Emma Stone', 2016, 0a3b2105-c8aa-4840-9633-e8f359946d9f, 'La La Land', {'musical', 'drama'}, 10);

INSERT INTO movies_by_actor(actor, release_year, movie_id, title, genres, rating)
    VALUES('Brad Pitt', 2011, 6c6725db-2a3f-46b7-804e-31c6cd5e4e09, 'Tree of Life', {'drama'}, 10);

INSERT INTO movies_by_actor(actor, release_year, movie_id, title, genres, rating)
    VALUES('Emma Stone', 2014, 8403868b-018a-4d1f-b647-e978671efa3f, 'Birdman', {'drama'}, 10);

SELECT * FROM movies_by_actor WHERE actor = 'Emma Stone' and release_year < 2015;

// Custom Indexes

CREATE CUSTOM INDEX title ON movies_by_actor (title)
    USING 'org.apache.cassandra.index.sasi.SASIIndex'
    WITH OPTIONS = {'mode': 'CONTAINS'};

SELECT * FROM movies_by_actor WHERE title LIKE '%Land';


// Materialized Views

CREATE MATERIALIZED VIEW movies_mv AS
SELECT title, release_year, movie_id, genres, actor FROM movies_by_actor
WHERE title IS NOT NULL AND release_year IS NOT NULL AND movie_id IS NOT NULL AND actor IS NOT NULL
PRIMARY KEY ((title, release_year), actor, movie_id);


SELECT * FROM movies_mv;

SELECT * FROM movies_mv WHERE title = 'La La Land' AND release_year = 2016;