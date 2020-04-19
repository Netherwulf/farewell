#!/bin/bash
set -e

echo "Creating database: reservations_db"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- drop database if exists
    DROP DATABASE IF EXISTS reservations_db;
    CREATE DATABASE reservations_db WITH ENCODING = 'UTF8';
    GRANT ALL PRIVILEGES ON DATABASE reservations_db TO $POSTGRES_USER;
EOSQL

echo "Creating database: analytical_db"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- drop database if exists
    DROP DATABASE IF EXISTS analytical_db;
    CREATE DATABASE analytical_db WITH ENCODING = 'UTF8';
    GRANT ALL PRIVILEGES ON DATABASE analytical_db TO $POSTGRES_USER;
EOSQL

#	      \connect $database
#	      -- drop tables if they exist
#	      ALTER TABLE IF EXISTS Grave DROP CONSTRAINT Concerns;
#        ALTER TABLE IF EXISTS Deceased DROP CONSTRAINT Contains;
#        DROP TABLE IF EXISTS Grave CASCADE;
#        DROP TABLE IF EXISTS Deceased CASCADE;
#        DROP TABLE IF EXISTS Funeral CASCADE;
#        -- create tables
#        CREATE TABLE Grave (Id SERIAL PRIMARY KEY, ReservationDate TIMESTAMP NOT NULL, GraveNumber VARCHAR(20) NOT NULL, Coordinates VARCHAR(30) NOT NULL, Capacity INT NOT NULL, FuneralId INT);
#        CREATE TABLE Deceased (Id SERIAL PRIMARY KEY, Surname VARCHAR(50) NOT NULL, Name VARCHAR(30) NOT NULL, DateOfBirth DATE NOT NULL, PlaceOfBirth VARCHAR(50) NOT NULL, DateOfDeath DATE NOT NULL, PlaceOfDeath VARCHAR(50) NOT NULL, GraveId INT NOT NULL);
#        CREATE TABLE Funeral (Id SERIAL PRIMARY KEY, ReservationDate TIMESTAMP NOT NULL, Date TIMESTAMP NOT NULL, FuneralDirectorId INT NOT NULL, UserId INT NOT NULL);
#        ALTER TABLE Grave ADD CONSTRAINT Concerns FOREIGN KEY (FuneralId) REFERENCES Funeral (Id);
#        ALTER TABLE Deceased ADD CONSTRAINT Contains FOREIGN KEY (GraveId) REFERENCES Grave (Id);
#        -- create dummy data in tables
#        INSERT INTO Funeral (ReservationDate, Date, FuneralDirectorId, UserId)
#        VALUES
#          ('2012-05-03 22:15:10-00', '2012-06-12 10:15:00-00', 2, 1);
#        INSERT INTO Grave (ReservationDate, GraveNumber, Coordinates, Capacity , FuneralId)
#        VALUES
#          ('2016-06-22 19:10:25-00', 0, '41-24-12.2-N 2-10-26.5-E', 4, 0),
#          ('2017-07-24 19:11:00-00', 1, '30-22-16.0-S 3-25-27.7-W', 1, 0);
#        INSERT INTO Deceased (Surname, Name, DateOfBirth, PlaceOfBirth, DateOfDeath, PlaceOfDeath, GraveId);
#        VALUES
#          ('Kowalski', 'Jan', '1970-05-03', 'Warszawa', '2012-01-30', 'Gdynia', 1);