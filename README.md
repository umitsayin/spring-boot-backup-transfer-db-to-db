# Database backup and transfer of database to another database with Spring Boot

Apply this
-install docker community version
-docker-compose up -d

Change -> Resources/backup/hibernate.cfg.xml -> Set custom database information
This custom database must run on port 3307 as a separate docker container

Change container_ids in DbtodbApplication Class ( my procject container_id = 2aa )
 
