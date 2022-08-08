# Cassandra Essentials

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