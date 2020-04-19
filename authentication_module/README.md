
# Farewell Authentication Module

 Authentication Module


## Technologies used:

* Docker
* Spring Boot
* Hibernate
* PostgreSQL

## Commands

* Building and running with Maven - execute inside farewell/authentication_module directory :
    * mvn package
    * java -jar target\authentication_module-0.1.0.jar

* Create Docker image and tag it - execute inside farewell/authentication_module directory:
    * docker build -t farewell/authentication_module .
    * docker run -d -p 8080:7000(<machine_port>:<container_port>) farewell/authentication_module .
    * container can be accessed at (docker-machine ip):7000