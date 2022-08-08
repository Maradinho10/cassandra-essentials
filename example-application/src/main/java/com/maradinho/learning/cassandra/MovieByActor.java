package com.maradinho.learning.cassandra;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

import static com.maradinho.learning.cassandra.Constants.MOVIES_BY_ACTOR_TABLE;

@Table(name = MOVIES_BY_ACTOR_TABLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MovieByActor {
    @PartitionKey
    @Column(name = "actor")
    private String actor;

    @ClusteringColumn(0)
    @Column(name = "release_year")
    private Integer releaseYear;

    @ClusteringColumn(1)
    @Column(name = "movie_id")
    private UUID movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "genres")
    private Set<String> genres;
}
