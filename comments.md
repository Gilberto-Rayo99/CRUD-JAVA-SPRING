# Useful commands
### delete from table flyway schema all the migrations that was on error statement.
delete from flyway_schema_history where success = 0;
