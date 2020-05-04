# farewell Payment Module
Payment Module

## Technologies used:
* Docker
* Spring Boot
* Spring REST MVC
* Hibernate
* PostgreSQL

## Commands
* Building and running with Maven <!-- or Gradle --> - execute inside <code>farewell/payment_module</code> directory <!-- (choose one of the below)-->:
    * <code>.\mvnw package</code>
    * <code>java -jar target\payment-module-0.1.0.jar</code>
    <!-- * <code>.\gradlew build; java -jar build\libs\payment-module-0.1.0.jar</code> -->
* Create Docker image and tag it - execute inside <code>farewell/payment_module</code> directory:
    * <code>docker build -t farewell/payment_module .</code>
    * <code>docker run -d -p 8080:8080(<machine_port>:<container_port>) farewell/payment_module .</code>
    * container can be accessed at <code>(docker-machine ip):8080</code>