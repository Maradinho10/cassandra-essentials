# Cassandra Essentials

### Getting Started

On the repository root folder, start the Cassandra Docker container.

```
docker compose up -d
```

### Use CQL Shell
1. In a terminal, use the following to get an interactive shell session in the Cassandra Docker container
```
docker exec -it essentials-cassandra /bin/bash
```
2. Once in the container shell, use the following command to execute CQL Shell
```
cqlsh    
```
### Basic Commands

For basic commands (`create keyspace`, `insert`, `select`) check `basic-commands.cql` file.

### Java Examples

The Java examples make usage of the `essentials` Cassandra keyspace and tables specified in the `basic-commands.cql` file, so please make sure to create these using the Docker container specified in the `docker-compose.yml` file before executing them. 

The Java classes `CassandraExample` and `ObjectMappingExample` can be executed through Gradle, use the following commands to execute them

```
./gradlew cassandraExample
```

```
./gradlew objectMappingExample
```

The `CassandraExample` class creates a session to communicate with the Cassandra server, has some CRUD examples using simple queries of prepared statements.

The `ObjectMappingExample` class shows the usage of the object mapping functionalities provided by Cassandra and accesses data in a more ORM-like way.
