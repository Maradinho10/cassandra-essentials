ARG VERSION

FROM cassandra:$VERSION

RUN sed -i -r 's/enable_materialized_views: false/enable_materialized_views: true/' /etc/cassandra/cassandra.yaml
RUN sed -i -r 's/enable_sasi_indexes: false/enable_sasi_indexes: true/' /etc/cassandra/cassandra.yaml
