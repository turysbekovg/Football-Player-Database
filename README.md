# project_fifa
JAVA project made with Spring, Hibernate, PostgreSQL, Security, Lombok
This is project is aimed to create database with football players, clubs, and connect players to clubs.
To create a player in your database you need:

1) register a user
2) authorize
3) then you can either create, update, delete player or club
4) also you can connect a certain player with certain club

Moreover, you can make a certain user as a certain admin. However, in order to make a change you need to make a user as a certain admin. There are 3 types of admins:
 
1) ROLE_FOOTBALL_CLUB_ADMIN
2) ROLE_FOOTBALL_PLAYER_ADMIN
3) ROLE_PLAYER_CLUB_ADMIN

However, you can add your own admin roles.

I made my own database using PostgreSQL, however you can connect your own database. 
